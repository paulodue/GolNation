package com.example.assignment3_groupc.teams

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment3_groupc.Login
import com.example.assignment3_groupc.MainActivity
import com.example.assignment3_groupc.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SantosInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_santos_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.santosInfo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainMenuBtn: TextView = findViewById(R.id.mainMenuBtn)
        mainMenuBtn.setOnClickListener{
            val goToMainLoginPage = Intent(this, MainActivity::class.java)
            startActivity(goToMainLoginPage)
        }

        fun fetchFromAPIFootballSantos() {
            Thread {
                    val url = URL("https://api-football-v1.p.rapidapi.com/v3/teams/statistics?league=72&season=2024&team=128")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("x-rapidapi-key", "5439f206e0msh0d83a4f819df7acp14255bjsn27fddeb0af36")
                    connection.setRequestProperty("x-rapidapi-host", "api-football-v1.p.rapidapi.com")

                    val inputStream = connection.inputStream
                    val response = inputStream.bufferedReader().use { it.readText() }

                    val jsonResponse = JSONObject(response)
                    val fixtures = jsonResponse.getJSONObject("response").getJSONObject("fixtures")
                    val played = fixtures.getJSONObject("played").getInt("total")
                    val wins = fixtures.getJSONObject("wins").getInt("total")
                    val draws = fixtures.getJSONObject("draws").getInt("total")
                    val loses = fixtures.getJSONObject("loses").getInt("total")

                    runOnUiThread {
                        val santosMatchesToXML: TextView = findViewById(R.id.santosMatches)
                        santosMatchesToXML.text = played.toString()

                        val santosWinsToXML: TextView = findViewById(R.id.santosWins)
                        santosWinsToXML.text = wins.toString()

                        val santosDrawsToXML: TextView = findViewById(R.id.santosDraws)
                        santosDrawsToXML.text = draws.toString()

                        val santosLosesToXML: TextView = findViewById(R.id.santosLoses)
                        santosLosesToXML.text = loses.toString()
                    }
                }.start()
            }
            fetchFromAPIFootballSantos()
    }
}