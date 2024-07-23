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
import kotlin.math.log

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val registerText: TextView = findViewById(R.id.registerNow)

        registerText.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            startSignUp()
        }
    }

    private fun startSignUp() {
        val email: EditText = findViewById(R.id.email_edittext)
        val password: EditText = findViewById(R.id.password_edittext)

        //Checking empty values on email and password
        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }
        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)


                    Toast.makeText(
                        baseContext,
                        "Authentication Successful!",
                        Toast.LENGTH_LONG,
                    ).show()


                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Authentication failed ${it.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }
    }
}