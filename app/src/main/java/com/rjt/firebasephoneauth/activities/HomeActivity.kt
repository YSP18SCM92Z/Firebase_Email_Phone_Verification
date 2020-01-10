package com.rjt.firebasephoneauth.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.rjt.firebasephoneauth.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            text_view_username.text = auth.currentUser?.email
        } else {
            text_view_username.text = "Guest"
        }

        button_sign_out.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
