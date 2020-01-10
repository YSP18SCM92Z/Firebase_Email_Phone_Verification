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
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.rjt.firebasephoneauth.R
import com.rjt.firebasephoneauth.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class RegisterFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)

        view.button_email_register.setOnClickListener {
            var email = edit_text_email_register.text.toString()
            var password = edit_text_password_register.text.toString()

            mAuth = FirebaseAuth.getInstance()
            activity?.let {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(it, OnCompleteListener<AuthResult>{
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(context, HomeActivity::class.java))
                    } else {
                        Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        view.text_view_register_click_here.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.email_fragment_container, LoginFragment()).commit()
        }

        return view
    }

}
