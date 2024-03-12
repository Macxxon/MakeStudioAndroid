package com.make.develop.studio

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.make.develop.studio.databinding.ActivityAuthBinding
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.FirebaseAuth

class AuthActivity:AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityAuthBinding
    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.generateBtn.setOnClickListener {
            val phoneNumber = binding.phoneNumberEdt.text.toString()
            startPhoneNumberVerification(phoneNumber)
        }

        binding.otpVerifyBtn.setOnClickListener {
            val code = binding.otpNumberEdt.text.toString()
            if(verificationId.isNullOrEmpty()){
                Toast.makeText(this, "No se ha enviado el código de verificación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            verifyPhoneNumberWithCode(verificationId!!, code)
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+57$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.layoutVerify.visibility = View.GONE
        binding.generateBtn.visibility = View.GONE
        binding.layoutOtp.visibility = View.VISIBLE
        binding.otpVerifyBtn.visibility = View.VISIBLE

    }

    private fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@AuthActivity, "Logeado mijo", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AuthActivity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@AuthActivity, "Error al enviar el código de verificación", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            this@AuthActivity.verificationId = verificationId
            Toast.makeText(this@AuthActivity, "Código de verificación enviado correctamente", Toast.LENGTH_SHORT).show()
        }
    }

}