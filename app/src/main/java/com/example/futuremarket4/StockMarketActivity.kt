package com.example.futuremarket4

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import android.widget.ProgressBar
import java.util.ArrayList
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextWatcher
import android.text.Editable
import java.util.Locale
import android.widget.Toast
import android.view.View
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import kotlin.Throws
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.example.futuremarket4.Model.StockRVModel
import java.util.HashMap

class StockMarketActivity : AppCompatActivity() {
    private var searchEdt: EditText? = null
    private var StocksRV: RecyclerView? = null
    private var loadingPB: ProgressBar? = null
    private var stockRVModelArrayList: ArrayList<StockRVModel>? = null
    private var stockRVAdapter: StockRVAdapter? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_market)
        searchEdt = findViewById(R.id.idEdtSearch)
        StocksRV = findViewById(R.id.RVStocks)
        loadingPB = findViewById(R.id.PbLoading)
        stockRVModelArrayList = ArrayList()
        stockRVAdapter = StockRVAdapter(stockRVModelArrayList!!, this)
        StocksRV?.setLayoutManager(LinearLayoutManager(this))
        StocksRV?.setAdapter(stockRVAdapter)
        stockData
        searchEdt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filterStocks(editable.toString())
            }
        })
    }

    private fun filterStocks(stock: String) {
        val filteredlist = ArrayList<StockRVModel>()
        for (item in stockRVModelArrayList!!) {
            if (item.text.lowercase(Locale.getDefault())
                    .contains(stock.lowercase(Locale.getDefault()))
            ) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "Böyle bir hisse bulunamadı", Toast.LENGTH_SHORT).show()
        } else {
            stockRVAdapter!!.filterList(filteredlist)
        }
    }

    private val stockData: Unit
        private get() {
            loadingPB!!.visibility = View.VISIBLE
            val url = "https://api.collectapi.com/economy/hisseSenedi"
            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                    loadingPB!!.visibility = View.GONE
                    try {
                        val dataArray = response.getJSONArray("result")
                        for (i in 0 until dataArray.length()) {
                            val dataObj = dataArray.getJSONObject(i)
                            val text = dataObj.getString("text")
                            val lastpricestr = dataObj.getString("lastpricestr")
                            val rate = dataObj.getDouble("rate")
                            val minstr = dataObj.getString("minstr")
                            val maxstr = dataObj.getString("maxstr")
                            stockRVModelArrayList!!.add(StockRVModel(text,lastpricestr, rate, minstr, maxstr))
                        }
                        stockRVAdapter!!.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this@StockMarketActivity,
                            "Json dosyası çekilemedi..",
                            Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    loadingPB!!.visibility = View.GONE
                    Toast.makeText(this@StockMarketActivity,
                        "Veri çekme işlemi başarısız oldu...",
                        Toast.LENGTH_SHORT).show()
                }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["authorization"] =
                            "apikey 7zXu8PZSMumrxh4Q47MsiO:6SGGUoF09JTyiGe7UKYvjA"
                        return headers
                    }
                }
            requestQueue.add(jsonObjectRequest)
        }
}