package com.example.assignment3_groupc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth

        val loginText: TextView = findViewById(R.id.goToLogin)
        loginText.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            startSignUp()
        }

    }

    private fun startSignUp() {
        val email = findViewById<EditText>(R.id.email_edittext)
        val password = findViewById<EditText>(R.id.password_edittext)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show()
            return
        }

        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                //If the login is successfull, move to the next activity MainActivity

                val intent = Intent(this, Login::class.java)
                startActivity(intent)

                Toast.makeText(
                    baseContext,
                    "Registration Successful!",
                    Toast.LENGTH_LONG,
                ).show()

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }
    }
}