package com.make.develop.studio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.make.develop.studio.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = Firebase.auth


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
        /*dialog.show()
        userRef!!.child(user!!.uid)
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
                                Common.authorizeToken = it.result!!.token


                                dialog!!.dismiss()
                                val userModel = p0.getValue(UserModel::class.java)
                                Common.currentUser = userModel
                                if (prefManager.isFullRegister()) {
                                    goToHomeActivity(userModel)
                                }else {
                                    val intent = Intent(this@MainActivity,MapsActivity::class.java)
                                    intent.putExtra("register", true)
                                    startActivity(intent)
                                }


                            }

                    } else {
                        dialog!!.dismiss()
                        showRegisterDialog(user!!)
                    }

                }
            })*/

    }


}