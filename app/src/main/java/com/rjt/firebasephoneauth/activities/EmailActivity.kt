package com.rjt.firebasephoneauth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rjt.firebasephoneauth.Fragments.LoginFragment
import com.rjt.firebasephoneauth.R

class EmailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        supportFragmentManager.beginTransaction().add(
            R.id.email_fragment_container,
            LoginFragment()
        ).commit()

    }
}
