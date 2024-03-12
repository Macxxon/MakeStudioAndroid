package com.make.develop.studio

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Número de teléfono a verificar
            .setTimeout(60L, TimeUnit.SECONDS)  // Duración del tiempo de espera para ingresar el código
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // Método para verificar el código ingresado por el usuario
    private fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    // Método para autenticar al usuario con las credenciales del número de teléfono
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Autenticación exitosa, puedes redirigir al usuario o realizar otras acciones
                    // Por ejemplo, puedes iniciar una nueva actividad.
                } else {
                    // La autenticación falló, maneja la situación según tus necesidades
                }
            }
    }

    // Callbacks para gestionar eventos de autenticación con el número de teléfono
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Se llama cuando la verificación se completa automáticamente
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // Se llama cuando la verificación falla, maneja la situación según tus necesidades
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            // Se llama cuando el código de verificación es enviado exitosamente al número de teléfono
            // Puedes almacenar el "verificationId" para usarlo más tarde
        }
    }

}