package com.example.golnation.teams

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.golnation.MainActivity
import com.example.golnation.R
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
                    val losses = fixtures.getJSONObject("loses").getInt("total")

                    val goals = jsonResponse.getJSONObject("response").getJSONObject("goals").getJSONObject("for").getJSONObject("total").getInt("total")

                    val timeRanges = listOf(
                        "0-15", "16-30", "31-45", "46-60",
                        "61-75", "76-90", "91-105", "106-120"
                    )

                    // Extracts card counts for a given type (e.g., "yellow" or "red")
                    fun getCardTotal(jsonResponse: JSONObject, cardType: String): Int {
                        return timeRanges.sumOf { range ->
                            jsonResponse.getJSONObject("response")
                                .getJSONObject("cards")
                                .getJSONObject(cardType)
                                .optJSONObject(range)
                                ?.optInt("total", 0) ?: 0
                        }
                    }

                    val yellowCardsTotal = getCardTotal(jsonResponse, "yellow")
                    val redCardsTotal = getCardTotal(jsonResponse, "red")
                    val totalCards = yellowCardsTotal + redCardsTotal

                    runOnUiThread {
                        val santosMatchesToXML: TextView = findViewById(R.id.santosMatches)
                        santosMatchesToXML.text = played.toString()

                        val santosWinsToXML: TextView = findViewById(R.id.santosWins)
                        santosWinsToXML.text = wins.toString()

                        val santosDrawsToXML: TextView = findViewById(R.id.santosDraws)
                        santosDrawsToXML.text = draws.toString()

                        val santosLosesToXML: TextView = findViewById(R.id.santosLosses)
                        santosLosesToXML.text = losses.toString()

                        val santosGoalsToXML: TextView = findViewById(R.id.santosGoals)
                        santosGoalsToXML.text = goals.toString()

                        val santosCardsToXML: TextView = findViewById(R.id.santosCards)
                        santosCardsToXML.text = totalCards.toString()
                    }
                }.start()
            }
            fetchFromAPIFootballSantos()
    }
}