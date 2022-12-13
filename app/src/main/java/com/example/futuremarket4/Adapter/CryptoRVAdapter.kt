package com.example.futuremarket4.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.futuremarket4.Model.CryptoRVModel
import com.example.futuremarket4.Model.StockRVModel
import com.example.futuremarket4.R
import java.text.DecimalFormat
import java.util.ArrayList

    class CryptoRVAdapter(
        private var cryptoRVModelArrayList: ArrayList<CryptoRVModel>,
        private val context: Context
    ) : RecyclerView.Adapter<CryptoRVAdapter.ViewHolder>() {
        fun filterList(filteredlist: ArrayList<CryptoRVModel>) {
            cryptoRVModelArrayList = filteredlist
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.crypto_rv_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val CryptoRVModel = cryptoRVModelArrayList[position]
            holder.cryptoNameTV.text = CryptoRVModel.name
            holder.cryptSymbolTV.text = CryptoRVModel.symbol
            holder.cryptoPriceTV.text = "$" + df5.format(CryptoRVModel.price)
            holder.cryptochangerateTV.text = "%" + df2.format(CryptoRVModel.percent_change_24h)
}

        override fun getItemCount(): Int {
            return cryptoRVModelArrayList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cryptoNameTV: TextView
            val cryptSymbolTV: TextView
            val cryptoPriceTV: TextView
            val cryptochangerateTV: TextView


            init {
                cryptoNameTV = itemView.findViewById(R.id.idTvCryptoName)
                cryptSymbolTV = itemView.findViewById(R.id.idTVSymbol)
                cryptoPriceTV = itemView.findViewById(R.id.idTVLastPriceCrypto)
                cryptochangerateTV = itemView.findViewById(R.id.idTVCryptoRate)
            }
        }

        companion object {
            private val df5 = DecimalFormat("#.#####")
            private val df2 = DecimalFormat("#.##")

        }
    }
