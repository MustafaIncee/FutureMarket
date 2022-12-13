package com.example.futuremarket4
import android.content.Context
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import android.widget.TextView
import java.text.DecimalFormat

class StockRVAdapter(
    private var stockRVModelArrayList: ArrayList<com.example.futuremarket4.Model.StockRVModel>,
    private val context: Context
) : RecyclerView.Adapter<StockRVAdapter.ViewHolder>() {
    fun filterList(filteredlist: ArrayList<com.example.futuremarket4.Model.StockRVModel>) {
        stockRVModelArrayList = filteredlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stock_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stockRVModel = stockRVModelArrayList[position]
        holder.stockTextTV.text = stockRVModel.text
        holder.stockMinTV.text = stockRVModel.minstr
        holder.stockMaxTV.text = stockRVModel.maxstr
        holder.stockLastPriceTV.text = "₺" + stockRVModel.lastpricestr
        holder.stockRateTV.text = "%" + stockRVModel.rate
    }

    override fun getItemCount(): Int {
        return stockRVModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stockTextTV: TextView
        val stockMinTV: TextView
        val stockMaxTV: TextView
        val stockLastPriceTV: TextView
        val stockRateTV: TextView

        init {
            stockTextTV = itemView.findViewById(R.id.idTVText)
            stockMinTV = itemView.findViewById(R.id.idTvMinPrice)
            stockMaxTV = itemView.findViewById(R.id.idTvMaxPrice)
            stockLastPriceTV = itemView.findViewById(R.id.idTVCurrencyLastPrice)
            stockRateTV = itemView.findViewById(R.id.idTVStockRate)

        }
    }

    companion object {
        private val df2 = DecimalFormat("#.##")
    }
}