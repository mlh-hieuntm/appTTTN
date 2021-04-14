package com.example.nthigplxa1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nthigplxa1.R
import com.example.nthigplxa1.model.Home

class ListHomeAdapter(var mContext: Context, var mListener: ItemHomeListener) :
    RecyclerView.Adapter<ListHomeAdapter.ViewHolder>() {

    private var mArrayListHome = ArrayList<Home>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv_title)
        val img: ImageView = itemView.findViewById(R.id.img_logoItem)
    }

    fun setList(list: ArrayList<Home>) {
        this.mArrayListHome = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_list_home, parent, false)
        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return mArrayListHome.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = mArrayListHome[position].title
        holder.img.setImageResource(mArrayListHome[position].imgRs)
        holder.itemView.setOnClickListener {
            this.mListener.onItemClick(position, mArrayListHome[position])
        }

    }
}

interface ItemHomeListener {
    fun onItemClick(position: Int, mItem: Home)
}
