package com.rjt.firebasephoneauth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rjt.firebasephoneauth.Fragments.PhoneFragment
import com.rjt.firebasephoneauth.Fragments.VerifyFragment
import com.rjt.firebasephoneauth.R

class PhoneActivity : AppCompatActivity(), PhoneFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        supportFragmentManager.beginTransaction().add(R.id.phone_fragment_container, PhoneFragment()).commit()

    }


    // pass the phone number from PhoneFragment to PhoneActivity
    override fun onFragmentInteraction(phoneNumber: String) {

        // pass the phone number from PhoneActivity to VerifyFragment
        var verifyFragment = VerifyFragment.newInstance(phoneNumber)
        supportFragmentManager.beginTransaction().replace(R.id.phone_fragment_container, verifyFragment).commit()
    }
}
