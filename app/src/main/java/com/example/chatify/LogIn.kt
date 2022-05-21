package com.example.chatify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
class LogIn : AppCompatActivity() {
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var buttonLogin : Button
    private lateinit var buttonSignup : Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_pwd)
        buttonLogin = findViewById(R.id.btnlogin)
        buttonSignup = findViewById(R.id.btnsignup)
        buttonSignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        buttonLogin.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            login(email, password)
        }
    }
    private fun login(email : String, password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}