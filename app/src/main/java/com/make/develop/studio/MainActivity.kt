package com.make.develop.studio

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.make.develop.studio.databinding.ActivityMainBinding
import com.make.develop.studio.databinding.PopupRegisterBinding
import com.make.develop.studio.models.UserModel
import com.make.develop.studio.ui.HomeActivity
import com.make.develop.studio.utils.Constants


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        userRef = FirebaseDatabase.getInstance().getReference(Constants.USER_REFERENCE)


        firebaseAuth.addAuthStateListener {
            if (firebaseAuth.currentUser != null) {
                checkUserFromFirebase(it.currentUser!!)
            }else{
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun checkUserFromFirebase(user: FirebaseUser) {
        binding.progressBar.visibility = View.VISIBLE
        userRef.child(user.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@MainActivity, "" + p0.message, Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {

                        FirebaseAuth.getInstance().currentUser!!
                            .getIdToken(true)
                            .addOnFailureListener { t ->
                                Toast.makeText(
                                    this@MainActivity,
                                    "" + t.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnCompleteListener {
                                Constants.authorizeToken = it.result!!.token


                                binding.progressBar.visibility = View.GONE
                                val userModel = p0.getValue(UserModel::class.java)
                                Constants.currentUser = userModel

                                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                    } else {
                        binding.progressBar.visibility = View.GONE
                        showRegisterDialog(user)
                    }

                }
            })

    }

    private fun showRegisterDialog(user: FirebaseUser) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro")
        builder.setMessage("Por favor, llene todos los campos")

        val bindingPopUp = PopupRegisterBinding.inflate(layoutInflater)

        builder.setView(bindingPopUp.root)

        bindingPopUp.edtPhone.setText(user.phoneNumber)

        bindingPopUp.btnRegister.setOnClickListener {
            if (TextUtils.isDigitsOnly(bindingPopUp.edtName.text.toString())) {
                Toast.makeText(applicationContext, "Por favor, escribe tu nombre", Toast.LENGTH_LONG).show()
            }else{

                val userModel = UserModel()
                userModel.uid = user.uid
                userModel.name = bindingPopUp.edtName.text.toString()
                userModel.phone = bindingPopUp.edtPhone.text.toString()
                userModel.balance = 0

                userRef.child(user.uid)
                    .setValue(userModel)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            FirebaseAuth.getInstance().currentUser!!
                                .getIdToken(true)
                                .addOnFailureListener { t ->
                                    Toast.makeText(
                                        this@MainActivity,
                                        "" + t.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnCompleteListener {
                                    Constants.authorizeToken = it.result.token

                                    Toast.makeText(
                                        this@MainActivity,
                                        "Felicidades! Registro Exitoso!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                }
                        }
                    }
            }


        }

        val dialog = builder.create()

        dialog.show()

    }

}