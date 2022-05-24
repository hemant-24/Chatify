package com.example.chatify

import android.content.Intent
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var editName : EditText
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var buttonSignup : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDBref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        editName = findViewById(R.id.edit_name)
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_pwd)
        confirmPassword = findViewById(R.id.confirm_pwd)
        buttonSignup = findViewById(R.id.btnsignup)
        buttonSignup.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val confirm = confirmPassword.text.toString()
            signup(name, email,password,confirm)
        }
    }
    private fun signup(name : String, email : String, password : String, cPassword : String){
        if(password != cPassword){
            Toast.makeText(this@SignUp, "Passwords doesn't match", Toast.LENGTH_SHORT).show()
        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                        val intent = Intent(this@SignUp, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT)
                            .show()

                    }
                }
        }
    }
    private fun addUserToDatabase(name : String, email : String, uid : String){
        mDBref = FirebaseDatabase.getInstance().getReference()
        mDBref.child("user").child(uid).setValue(user(name, email, uid))
    }
}