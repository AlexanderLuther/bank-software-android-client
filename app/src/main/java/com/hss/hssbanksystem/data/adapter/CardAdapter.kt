package com.hss.hssbanksystem.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.data.model.CardPaymentModel
import com.hss.hssbanksystem.data.model.PaymentLoanModel
import kotlin.collections.ArrayList

class CardAdapter(private val payments : ArrayList<CardPaymentModel>) : RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_payment_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = payments[position]
        holder.amountTextView.text = "Cantidad: Q" + currentItem.amount
        holder.dateTextView.text = "Fecha: " + currentItem.dateTime
        holder.descriptionTextView.text = "Descripcion: " + currentItem.description
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val amountTextView : TextView = itemView.findViewById(R.id.amountTextViewCardPayment)
        val dateTextView : TextView = itemView.findViewById(R.id.dateTextViewCardPayment)
        val descriptionTextView : TextView =  itemView.findViewById(R.id.descriptionTextViewCadPayment)
    }

}