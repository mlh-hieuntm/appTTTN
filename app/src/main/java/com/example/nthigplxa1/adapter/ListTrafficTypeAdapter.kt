package com.example.nthigplxa1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.nthigplxa1.R
import com.example.nthigplxa1.model.Exam

class ListTrafficTypeAdapter (var mContext: Context, var mListener: ItemListener) :
    RecyclerView.Adapter<ListTrafficTypeAdapter.ViewHolder>() {

    private var mArrayListTrafficType = ArrayList<String>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvType: TextView = itemView.findViewById(R.id.tv_typeTraffic)
    }

    fun setList(list: ArrayList<String>) {
        this.mArrayListTrafficType = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_traffic_type, parent, false)
        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return mArrayListTrafficType.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvType.text = mArrayListTrafficType[position]
        holder.itemView.setOnClickListener {
           this.mListener.onItemClick(position)
        }
    }
    interface ItemListener {
        fun onItemClick(position: Int)
    }

}

