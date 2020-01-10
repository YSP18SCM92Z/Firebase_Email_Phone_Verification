package com.rjt.firebasephoneauth.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rjt.firebasephoneauth.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_email.setOnClickListener {
            startActivity(Intent(this, EmailActivity::class.java))
        }

        button_phone.setOnClickListener {
            startActivity(Intent(this, PhoneActivity::class.java))
        }
    }
}
