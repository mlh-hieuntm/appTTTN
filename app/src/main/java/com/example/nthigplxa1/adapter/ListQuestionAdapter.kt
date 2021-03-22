package com.example.nthigplxa1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nthigplxa1.R

class ListQuestionAdapter(var mContext: Context) :
    RecyclerView.Adapter<ListQuestionAdapter.ViewHolder>() {

    private var mArrayListQuestion = ArrayList<Int>()
    private var mCurrentSelect: TextView? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderQues: TextView = itemView.findViewById(R.id.tv_orderExam)
        val tvAns1 = itemView.findViewById<TextView>(R.id.tv_ans1)
        val tvAns2 = itemView.findViewById<TextView>(R.id.tv_ans2)
        val tvAns3 = itemView.findViewById<TextView>(R.id.tv_ans3)
        val tvAns4 = itemView.findViewById<TextView>(R.id.tv_ans4)
    }

    fun setList(list: ArrayList<Int>) {
        this.mArrayListQuestion = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_list_do_exam, parent, false)

        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return mArrayListQuestion.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.orderQues.text = "Câu $position"

        when (position) {
            1 -> {
                holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            }
            2 -> {
                holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            }
            3 -> {
                holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            }
            4 -> {
                holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.green))
            }
        }

        holder.tvAns1.setOnClickListener {
            holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.green))
            holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
        }
        holder.tvAns2.setOnClickListener {
            holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.green))
            holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
        }
        holder.tvAns3.setOnClickListener {
            holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.green))
            holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
        }
        holder.tvAns4.setOnClickListener {
            holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.green))
        }

    }


}
