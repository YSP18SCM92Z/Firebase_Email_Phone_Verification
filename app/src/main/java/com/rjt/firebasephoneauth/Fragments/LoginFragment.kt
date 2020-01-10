package com.rjt.firebasephoneauth.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.rjt.firebasephoneauth.R
import com.rjt.firebasephoneauth.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        view.button_email_login.setOnClickListener {

            var email = edit_text_email_login.text.toString()
            var password = edit_text_password_login.text.toString()

            mAuth = FirebaseAuth.getInstance()
            activity?.let { it1 ->
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    it1,
                    OnCompleteListener<AuthResult> {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(context, HomeActivity::class.java))
                        } else {
                            Toast.makeText(context, "Please check your email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }

        view.text_view_login_click_here.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.email_fragment_container, RegisterFragment()).commit()
        }

        return view
    }

}
