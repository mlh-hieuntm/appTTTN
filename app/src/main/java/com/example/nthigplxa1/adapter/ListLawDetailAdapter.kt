package com.example.nthigplxa1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nthigplxa1.R
import com.example.nthigplxa1.model.TrafficBoard

class ListLawDetailAdapter(var mContext: Context) :
    RecyclerView.Adapter<ListLawDetailAdapter.ViewHolder>() {

    private var mArrayListTraffic = ArrayList<TrafficBoard>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.nameDL)
        val content: TextView = itemView.findViewById(R.id.tv_contentDL)
        val imgTB: ImageView = itemView.findViewById(R.id.img_trafficBoard)
    }

    fun setList(list: ArrayList<TrafficBoard>) {
        mArrayListTraffic = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_detail_law, parent, false)
        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return mArrayListTraffic.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = mArrayListTraffic[position].name.capitalize()
        holder.content.text = mArrayListTraffic[position].content.capitalize()
        holder.imgTB.setImageResource(mArrayListTraffic[position].img)
    }
}