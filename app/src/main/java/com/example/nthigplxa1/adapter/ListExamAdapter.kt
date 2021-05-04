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

class ListExamAdapter(var mContext: Context, var mListener: ItemListener) :
    RecyclerView.Adapter<ListExamAdapter.ViewHolder>() {

    private var mArrayListExam = ArrayList<Exam>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameOfExam: TextView = itemView.findViewById(R.id.tv_nameOfExam)
        val tvDo: TextView = itemView.findViewById(R.id.tv_doExam)
        val clBg: ConstraintLayout = itemView.findViewById(R.id.cl_bg)
        val foregroundView: ConstraintLayout = itemView.findViewById(R.id.foregroundView)
        val textDeleteRight: TextView = itemView.findViewById(R.id.textDeleteRight)
        var mItem: Exam? = null
    }

    fun setList(list: ArrayList<Exam>) {
        this.mArrayListExam = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_exam, parent, false)
        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return mArrayListExam.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mArrayListExam[position]
        if (mArrayListExam[position].mIsFinished) {
            holder.tvDo.text = "XEM KẾT QUẢ"
        }
        holder.tvNameOfExam.text = "Đề số ${mArrayListExam[position].mID}"
        holder.tvDo.setOnClickListener {
            holder.mItem?.let {
                mListener.onItemClick(position, it)
            }
        }
        when (position % 8) {
            0 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_red)
            1 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_indigo)
            2 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_yellow)
            3 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_blue)
            4 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_green)
            5 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_violet)
            6 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_black)
            7 -> holder.clBg.setBackgroundResource(R.drawable.background_custom_four_corner_orange)
        }
    }

}

interface ItemListener {
    fun onItemClick(position: Int, mItem: Exam)
}