package com.hss.hssbanksystem.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.data.model.PaymentLoanModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LoanAdapter(private val payments : ArrayList<PaymentLoanModel>) : RecyclerView.Adapter<LoanAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.payment_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = payments[position]
        holder.amountTextView.text = "Cantidad: Q" + currentItem.amount
        holder.dateTextView.text = "Fecha: " + currentItem.date
        holder.balanceTextView.text = "Balance: " + currentItem.balance
        holder.totalPaymentTextView.text = "Pago total: " + currentItem.totalPayment
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val amountTextView : TextView = itemView.findViewById(R.id.amountTextViewPayment)
        val dateTextView : TextView = itemView.findViewById(R.id.dateTextViewPayment)
        val balanceTextView : TextView =  itemView.findViewById(R.id.balanceTextViewPayment)
        val totalPaymentTextView : TextView =  itemView.findViewById(R.id.totalPaymentTextViewPayment)
    }

}