package com.example.futuremarket4

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.futuremarket4.Adapter.CryptoRVAdapter
import com.example.futuremarket4.Model.CryptoRVModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class CryptoCurrencyActivity : AppCompatActivity() {
    private var searchEdt: EditText? = null
    private var CryptoRV: RecyclerView? = null
    private var loadingPB: ProgressBar? = null
    private var cryptoRVModelArrayList: ArrayList<CryptoRVModel>? = null
    private var cryptoRVAdapter: CryptoRVAdapter? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_currency)
        searchEdt = findViewById(R.id.idEdtSearchCrypto)
        CryptoRV= findViewById(R.id.RVCrypto)
        loadingPB = findViewById(R.id.PbLoadingCrypto)
        cryptoRVModelArrayList = ArrayList()
        cryptoRVAdapter = CryptoRVAdapter(cryptoRVModelArrayList!!, this)
        CryptoRV?.setLayoutManager(LinearLayoutManager(this))
        CryptoRV?.setAdapter(cryptoRVAdapter)
        cryptoData
        searchEdt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filterCrypto(editable.toString())
            }
        })
    }

    private fun filterCrypto(crypto: String) {
        val filteredlist = ArrayList<CryptoRVModel>()
        for (item in cryptoRVModelArrayList!!) {
            if (item.name.lowercase(Locale.getDefault())
                    .contains(crypto.lowercase(Locale.getDefault()))
            ) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "Böyle bir kripto para bulunamadı", Toast.LENGTH_SHORT).show()
        } else {
            cryptoRVAdapter!!.filterList(filteredlist)
        }
    }

    private val cryptoData: Unit
        private get() {
            loadingPB!!.visibility = View.VISIBLE
            val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                    loadingPB!!.visibility = View.GONE
                    try {
                        val dataArray = response.getJSONArray("data")
                        for (i in 0 until dataArray.length()) {
                            val dataObj = dataArray.getJSONObject(i)
                            val name = dataObj.getString("name")
                            val symbol = dataObj.getString("symbol")
                            val  quote:JSONObject = dataObj.getJSONObject("quote")
                            val  USD:JSONObject = quote.getJSONObject("USD")
                            var price: Double = USD.getDouble("price")
                            var percent_change_24h = USD.getDouble("percent_change_24h")
                            cryptoRVModelArrayList!!.add(CryptoRVModel(name,symbol,price,percent_change_24h))
                        }
                        cryptoRVAdapter!!.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this@CryptoCurrencyActivity,
                            "Json dosyası çekilemedi..",
                            Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    loadingPB!!.visibility = View.GONE
                    Toast.makeText(this@CryptoCurrencyActivity,
                        "Veri çekme işlemi başarısız oldu...",
                        Toast.LENGTH_SHORT).show()
                }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["X-CMC_PRO_API_KEY"] =
                            "5a647f4c-96a9-40cf-b145-5429a50694cf"
                        return headers
                    }
                }
            requestQueue.add(jsonObjectRequest)
        }
}
