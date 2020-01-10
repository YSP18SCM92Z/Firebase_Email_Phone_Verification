package com.rjt.firebasephoneauth.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.rjt.firebasephoneauth.R
import com.rjt.firebasephoneauth.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_verify.*
import kotlinx.android.synthetic.main.fragment_verify.view.*
import java.util.concurrent.TimeUnit

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

class VerifyFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var phoneAuthProvider: PhoneAuthProvider
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var phoneNum: String? = null
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phoneNum = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_verify, container, false)

        Toast.makeText(context, phoneNum, Toast.LENGTH_LONG).show()

        auth = FirebaseAuth.getInstance()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(context, "onVerificationCompleted: $credential", Toast.LENGTH_SHORT).show()

//                startActivity(Intent(context, HomeActivity::class.java))

                // This callback will be invoked in two situations:
//                // 1 - Instant verification. In some cases the phone number can be instantly
//                //     verified without needing to send or enter a verification code.
//                // 2 - Auto-retrieval. On some devices Google Play services can automatically
//                //     detect the incoming verification SMS and perform verification without
//                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Toast.makeText(context, "Verification Failed with message $e", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }


        startPhoneNumberVerification(phoneNum.toString())


        view.button_phone_verify.setOnClickListener {

            var fieldVerificationCode = edit_text_OTP.text.toString()

            verifyPhoneNumberWithCode(storedVerificationId, fieldVerificationCode)

        }

        return view
    }

    private fun startPhoneNumberVerification(phoneNum: String) {
        phoneAuthProvider = PhoneAuthProvider.getInstance()
        activity?.let { it1 ->
            phoneAuthProvider.verifyPhoneNumber(
                "+1$phoneNum",
//                phoneNum,
                60,
                TimeUnit.SECONDS,
                it1,
                callbacks
            )
        }
    }

    private fun verifyPhoneNumberWithCode(storedVerificationId: String?, fieldVerificationCode: String) {

        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, fieldVerificationCode)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        activity?.let {
            auth.signInWithCredential(credential).addOnCompleteListener(it, OnCompleteListener {

                if(it.isSuccessful) {

                    Toast.makeText(context, "signInWithCredential: success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, HomeActivity::class.java))

                } else {
                    Toast.makeText(context, "signInWithCredential: failure", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            VerifyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
