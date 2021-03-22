package com.example.nthigplxa1

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import kotlinx.android.synthetic.main.activity_list_exam.*
import kotlinx.android.synthetic.main.dialog_confirm_delete.*

class ListExamActivity : AppCompatActivity(),
    ListExamItemTouchHelper.RecyclerItemTouchHelperListener, View.OnClickListener {
    private var mListExamAdapter: ListExamAdapter? = null
    private lateinit var mDialog: MaterialDialog
    private var helper: ItemTouchHelper? = null
    val arr = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_exam)
        initRecycleView()
        btn_addExam.setOnClickListener(this)
        btn_backListExam.setOnClickListener(this)
    }

    private fun initRecycleView() {
        mListExamAdapter = ListExamAdapter(this)
        rv_listExam.layoutManager = LinearLayoutManager(this)
        rv_listExam.adapter = mListExamAdapter
        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback = ListExamItemTouchHelper(
            0, ItemTouchHelper.LEFT, this
        )
        helper = ItemTouchHelper(itemTouchHelperCallback)
        helper?.attachToRecyclerView(rv_listExam)
        for (i in 0..20) {
            arr.add(i)
        }
        mListExamAdapter?.setList(arr)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if (viewHolder is ListExamAdapter.ViewHolder) {
            val mItem = viewHolder.mItem
            if (direction != ItemTouchHelper.RIGHT) {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    arr.removeAt(position)
                    mListExamAdapter?.setList(arr)
                    mDialog.dismiss()
                    cl_list_exam_activity.alpha = 1F
                    Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mListExamAdapter?.setList(arr)
                    mDialog.dismiss()
                }
                cl_list_exam_activity.alpha = 0.2F
                mDialog.show()

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_backListExam -> {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có muốn thoát?"
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    this.finish()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mDialog.dismiss()
                }
                cl_list_exam_activity.alpha = 0.2F
                mDialog.show()
            }
            R.id.btn_addExam -> {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có muốn tạo một đề thi mới ?"
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    arr.add(arr.size)
                    mListExamAdapter?.setList(arr)
                    mDialog.dismiss()
                    cl_list_exam_activity.alpha = 1F
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mDialog.dismiss()
                }
                cl_list_exam_activity.alpha = 0.2F
                mDialog.show()
            }
        }
    }
}