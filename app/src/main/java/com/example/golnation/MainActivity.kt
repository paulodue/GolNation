package com.example.golnation

import android.content.Intent
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.golnation.teams.FlaInfo
import com.example.golnation.teams.RealInfo
import com.example.golnation.teams.SantosInfo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val logoutText: TextView = findViewById(R.id.logoutBtn)
        logoutText.setOnClickListener{
            Firebase.auth.signOut()
            val goToMainLoginPage = Intent(this, Login::class.java)
            startActivity(goToMainLoginPage)

            Toast.makeText(this, "Logout Successful", Toast.LENGTH_LONG).show()
        }

        val realBtn: ImageButton = findViewById(R.id.real)
        realBtn.setOnClickListener{
            val intent = Intent(this, RealInfo::class.java)
            startActivity(intent)
        }

        val santosBtn: ImageButton = findViewById(R.id.santos)
        santosBtn.setOnClickListener{
            val intent = Intent(this, SantosInfo::class.java)
            startActivity(intent)
        }

        val flaBtn: ImageButton = findViewById(R.id.fla)
        flaBtn.setOnClickListener{
            val intent = Intent(this, FlaInfo::class.java)
            startActivity(intent)
        }

        val welcomeText = findViewById<TextView>(R.id.textView)
        welcomeText.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(3600)
            .setInterpolator(BounceInterpolator())
            .start()
    }
}