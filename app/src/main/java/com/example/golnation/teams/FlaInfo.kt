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
                    val url = URL("https://api-football-v1.p.rapidapi.com/v3/teams/statistics?league=71&season=2024&team=127")
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

//                    val yellowCards015 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("0-15").getInt("total")
//                    val yellowCards1630 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("16-30").getInt("total")
//                    val yellowCards3145 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("31-45").getInt("total")
//                    val yellowCards4660 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("46-60").getInt("total")
//                    val yellowCards6175 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("61-75").getInt("total")
//                    val yellowCards7690 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("76-90").getInt("total")
//                    val yellowCards91105 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("91-105").getInt("total")
//                    val yellowCards106120 = jsonResponse.getJSONObject("response").getJSONObject("cards").getJSONObject("yellow").getJSONObject("106-120").getInt("total")
//                    val yellowCardsTotal = yellowCards015 + yellowCards1630 + yellowCards3145 + yellowCards4660 + yellowCards6175 + yellowCards7690 + yellowCards91105 + yellowCards106120

                    runOnUiThread {
                        val flaMatchesToXML: TextView = findViewById(R.id.flaMatches)
                        flaMatchesToXML.text = played.toString()

                        val flaWinsToXML: TextView = findViewById(R.id.flaWins)
                        flaWinsToXML.text = wins.toString()

                        val flaDrawsToXML: TextView = findViewById(R.id.flaDraws)
                        flaDrawsToXML.text = draws.toString()

                        val flaLosesToXML: TextView = findViewById(R.id.flaLoses)
                        flaLosesToXML.text = losses.toString()

                        val flaGoalsToXML: TextView = findViewById(R.id.flaGoals)
                        flaGoalsToXML.text = goals.toString()

                        val flaCardsToXML: TextView = findViewById(R.id.flaCards)
                        flaCardsToXML.text = totalCards.toString()

                    }
            }.start()
        }
        fetchFromAPIFootballFla()
        
    }
}
