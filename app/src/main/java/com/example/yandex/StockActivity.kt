package com.example.yandex

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class StockActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listView = findViewById<ListView>(R.id.listView)
        var arrStock: ArrayList<Stock> = ArrayList()
        val key = "c1lha7q37fko1nlhd0ng"
        val url = "https://finnhub.io/api/v1/stock/symbol?exchange=US&token=$key"
        var displaySymbol: String? = null
        var urlGraph =
            "https://finnhub.io/api/v1/stock/candle?symbol=$displaySymbol&resolution=1&from=1615298999&to=1615302599&token=$key"
        val search = findViewById<SearchView>(R.id.searchView)
        val list = findViewById<ListView>(R.id.listView)


        doAsync {
            val apiResponse = URL(url).readText()
            //Log.d("INFO", apiResponse)
            val packet = JSONArray(apiResponse)
            var names = ArrayList<String>(16)
            var descriptions = ArrayList<String>(16)
            //Log.d("INFO1", packet.length().toString())
            for (i in 0..15) {
                names.add(packet.getJSONObject(i).getString("displaySymbol"))
                descriptions.add(packet.getJSONObject(i).getString("description"))
            }
            //Log.d("INFO1", names[0])
            //Log.d("INFO1", descriptions[0])
            var prices = ArrayList<Double>(16)

            for (i in 0..15) {
                displaySymbol = names[i]
                var urlPrice = "https://finnhub.io/api/v1/quote?symbol=$displaySymbol&token=$key"
                var apiResponsePrice = URL(urlPrice).readText()
                //Log.d("INFO1", apiResponsePrice)
                var packetPrice = JSONObject(apiResponsePrice)
                prices.add(packetPrice.getDouble("c"))
            }
            //for (i in 0..15) {
            //    Log.d("INFO1", prices[i].toString())
            //}
            for (i in 0..15) {
                arrStock.add(Stock(names[i], prices[i], descriptions[i]))
            }
            listView.adapter = CustomAdapter(applicationContext, arrStock)
        }



    }
}