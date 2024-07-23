package com.example.assignment3_groupc.teams

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.assignment3_groupc.MainActivity
import com.example.assignment3_groupc.R
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class FlaInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fla_info)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.flaInfo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainMenuBtn: TextView = findViewById(R.id.mainMenuBtn)
        mainMenuBtn.setOnClickListener {
            val goToMainLoginPage = Intent(this, MainActivity::class.java)
            startActivity(goToMainLoginPage)
        }

        fun fetchFromAPIFootballFla() {
            Thread {
                    val url =
                        URL("https://api-football-v1.p.rapidapi.com/v3/teams/statistics?league=71&season=2024&team=127")
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
                        val flaMatchesToXML: TextView = findViewById(R.id.flaMatches)
                        flaMatchesToXML.text = played.toString()

                        val flaWinsToXML: TextView = findViewById(R.id.flaWins)
                        flaWinsToXML.text = wins.toString()

                        val flaDrawsToXML: TextView = findViewById(R.id.flaDraws)
                        flaDrawsToXML.text = draws.toString()

                        val flaLosesToXML: TextView = findViewById(R.id.flaLoses)
                        flaLosesToXML.text = loses.toString()
                    }
            }.start()
        }
        fetchFromAPIFootballFla()
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main, fragment)
        fragmentTransaction.commit()
    }
}
