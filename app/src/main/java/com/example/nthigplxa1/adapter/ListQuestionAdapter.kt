package com.example.nthigplxa1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nthigplxa1.R
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.model.ExamWithQuestion
import com.example.nthigplxa1.model.Question

class ListQuestionAdapter(var mContext: Context) :
    RecyclerView.Adapter<ListQuestionAdapter.ViewHolder>() {

    private var mIsShowExplain = false
    private var mArrayListQuestion = ArrayList<Question>()
    private var mArrayListAnswer = ArrayList<Answer>()
    private var mArrayListEWQ = ArrayList<ExamWithQuestion>()
    var mArrayAnsSelect = ArrayList<Int>()
    var mArrayCorrectAns = ArrayList<Int>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderQues: TextView = itemView.findViewById(R.id.tv_orderExam)
        val imgContent: ImageView = itemView.findViewById(R.id.img_contentQues)
        val tvContent: TextView = itemView.findViewById(R.id.tv_contentQuestion)
        val tvAns1 = itemView.findViewById<TextView>(R.id.tv_ans1)
        val tvAns2 = itemView.findViewById<TextView>(R.id.tv_ans2)
        val tvAns3 = itemView.findViewById<TextView>(R.id.tv_ans3)
        val tvAns4 = itemView.findViewById<TextView>(R.id.tv_ans4)
        val view1 = itemView.findViewById<View>(R.id.underLine1)
        val view2 = itemView.findViewById<View>(R.id.underLine2)
        val view3 = itemView.findViewById<View>(R.id.underLine3)
        val view4 = itemView.findViewById<View>(R.id.underLine4)
        val tvExplain = itemView.findViewById<TextView>(R.id.tv_explain)
        val tvW = itemView.findViewById<TextView>(R.id.tv_Warning)
    }

    fun setList(list: ArrayList<Question>, listAns: ArrayList<Answer>, listExamWithQuestion: ArrayList<ExamWithQuestion>) {
        this.mArrayListQuestion = list
        this.mArrayListAnswer = listAns
        this.mArrayListEWQ = listExamWithQuestion
        for (i in 0..24) {
            this.mArrayAnsSelect.add(-1)
            this.mArrayCorrectAns.add(-2)
        }
        notifyDataSetChanged()
    }

    fun showExplain() {
        mIsShowExplain = true
        notifyDataSetChanged()
    }

    fun getMark(): Int{
        var mark = 0
        for (i in 0..24) {
            if (mArrayAnsSelect[i] == mArrayCorrectAns[i]) {
                mark++
            }
        }
        return mark
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
        holder.orderQues.text = "Câu ${position + 1}"
        if (mArrayListQuestion[position].mContentImg != -1) {
            holder.imgContent.setImageResource(mArrayListQuestion[position].mContentImg)
        }
        holder.tvContent.text = mArrayListQuestion[position].mContent.trim()
        var countText = 1
        holder.tvAns1.visibility = View.GONE
        holder.tvAns2.visibility = View.GONE
        holder.tvAns3.visibility = View.GONE
        holder.tvAns4.visibility = View.GONE
        holder.view1.visibility = View.GONE
        holder.view2.visibility = View.GONE
        holder.view3.visibility = View.GONE
        holder.view4.visibility = View.GONE
        holder.tvExplain.visibility = View.GONE

        if (mArrayListQuestion[position].mIsParalysisPoint) {
            holder.tvW.visibility = View.VISIBLE
        } else {
            holder.tvW.visibility = View.INVISIBLE
        }
        when (mArrayAnsSelect[position]) {
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
        mArrayListAnswer.forEach {
            if (it.mQuestionID == mArrayListQuestion[position].mID) {
                when (countText) {
                    1 -> {
                        holder.tvAns1.text = "1, ${it.mContent.trim()}"
                        holder.tvAns1.visibility = View.VISIBLE
                        holder.view1.visibility = View.VISIBLE
                        if (it.mID == mArrayListQuestion[position].mAnsIdCorrect) {
                            mArrayCorrectAns.add(position,1)
                        }
                    }
                    2 -> {
                        holder.tvAns2.text = "2, ${it.mContent.trim()}"
                        holder.tvAns2.visibility = View.VISIBLE
                        holder.view2.visibility = View.VISIBLE
                        if (it.mID == mArrayListQuestion[position].mAnsIdCorrect) {
                            mArrayCorrectAns.add(position,2)
                        }
                    }
                    3 -> {
                        holder.tvAns3.text = "3, ${it.mContent.trim()}"
                        holder.tvAns3.visibility = View.VISIBLE
                        holder.tvAns3.visibility = View.VISIBLE
                        holder.view3.visibility = View.VISIBLE
                        if (it.mID == mArrayListQuestion[position].mAnsIdCorrect) {
                            mArrayCorrectAns.add(position,3)
                        }
                    }
                    4 -> {
                        holder.tvAns4.text = "4, ${it.mContent.trim()}"
                        holder.tvAns4.visibility = View.VISIBLE
                        holder.view4.visibility = View.VISIBLE
                        if (it.mID == mArrayListQuestion[position].mAnsIdCorrect) {
                            mArrayCorrectAns.add(position,4)
                        }
                    }
                }
                countText++
            }
        }

        if (mIsShowExplain) {
            holder.tvExplain.visibility = View.VISIBLE
            holder.tvExplain.text = "Giải thích: ${mArrayListQuestion[position].mExplain}"
        }

        holder.tvAns1.setOnClickListener {
            if (!mIsShowExplain) {
                holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                mArrayAnsSelect[position] = 1
            }

        }
        holder.tvAns2.setOnClickListener {
           if (!mIsShowExplain) {
               holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
               holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.green))
               holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
               holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
               mArrayAnsSelect[position] = 2
           }
        }
        holder.tvAns3.setOnClickListener {
            if (!mIsShowExplain) {
                holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                mArrayAnsSelect[position] = 3
            }
        }
        holder.tvAns4.setOnClickListener {
           if (!mIsShowExplain) {
               holder.tvAns1.setTextColor(ContextCompat.getColor(mContext, R.color.black))
               holder.tvAns2.setTextColor(ContextCompat.getColor(mContext, R.color.black))
               holder.tvAns3.setTextColor(ContextCompat.getColor(mContext, R.color.black))
               holder.tvAns4.setTextColor(ContextCompat.getColor(mContext, R.color.green))
               mArrayAnsSelect[position] = 4
           }
        }

    }


}
