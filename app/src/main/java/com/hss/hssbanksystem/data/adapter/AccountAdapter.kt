package com.hss.hssbanksystem.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.data.model.MovementModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountAdapter(private val movements : ArrayList<MovementModel>) : RecyclerView.Adapter<AccountAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movement_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = movements[position]
        holder.typeTextView.text = "Transaccion: " + currentItem.type
        holder.amountTextView.text = "Cantidad: Q" + currentItem.amount
        holder.dateTextView.text = "Fecha: " + currentItem.date
    }

    override fun getItemCount(): Int {
        return movements.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val typeTextView : TextView = itemView.findViewById(R.id.typeTextViewServiceMovement)
        val amountTextView : TextView = itemView.findViewById(R.id.amountTextViewMovement)
        val dateTextView : TextView = itemView.findViewById(R.id.dateTextViewMovement)
    }

}