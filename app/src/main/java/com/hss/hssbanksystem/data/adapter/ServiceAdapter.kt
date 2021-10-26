package com.hss.hssbanksystem.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.data.model.ServiceModelItem

class ServiceAdapter(
    private val services : ArrayList<ServiceModelItem>,
    private var listener: onButtonClickListener
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.service_item, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = services[position]
        holder.titleImage.setImageResource(currentItem.image)
        holder.typeTextView.text = currentItem.type
        holder.balanceTextView.text = "Balance: Q" + currentItem.balance.toString()
        holder.idTextView.text = "Numero: " +currentItem.id
        if(currentItem.type.equals("Cuenta de ahorro") || currentItem.type.equals("Cuenta monetaria")){
            holder.makeTransferButton.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return services.size
    }

    class ViewHolder(itemView : View, listener: onButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val titleImage : ImageView = itemView.findViewById(R.id.titleImage)
        val typeTextView : TextView = itemView.findViewById(R.id.typeTextViewServiceMovement)
        val balanceTextView : TextView = itemView.findViewById(R.id.balanceTextViewService)
        val idTextView : TextView = itemView.findViewById(R.id.idTextViewService)
        val viewServiceButton : MaterialButton = itemView.findViewById(R.id.viewServiceButton)
        val makeTransferButton : MaterialButton = itemView.findViewById(R.id.transferButton)

        init {
            viewServiceButton.setOnClickListener{ listener.onViewButtonClick(adapterPosition) }
            makeTransferButton.setOnClickListener{ listener.onTransferButtonClick(adapterPosition) }
        }
    }

    interface onButtonClickListener{
        fun onTransferButtonClick(position: Int)
        fun onViewButtonClick(position: Int)
    }
}