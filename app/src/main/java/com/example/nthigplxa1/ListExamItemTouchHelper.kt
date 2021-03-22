package com.example.nthigplxa1

import android.graphics.Canvas
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ListExamItemTouchHelper(
    var dragDirs: Int,
    var swipeDirs: Int,
    var listener: RecyclerItemTouchHelperListener
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (viewHolder != null) {
            val foregroundView: View =
                (viewHolder as ListExamAdapter.ViewHolder).foregroundView
            getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder!!.adapterPosition != 0) {
            val foregroundView: View =
                (viewHolder as ListExamAdapter.ViewHolder).foregroundView

            getDefaultUIUtil().onDrawOver(
                c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive
            )
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView: View = (viewHolder as ListExamAdapter.ViewHolder).foregroundView
        foregroundView.visibility = View.VISIBLE
        getDefaultUIUtil().clearView(foregroundView)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
            val foregroundView: View =
                (viewHolder as ListExamAdapter.ViewHolder).foregroundView
            val textDeleteRight: TextView =
                (viewHolder).textDeleteRight
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (dX > 0) {
                    textDeleteRight.visibility = View.GONE
                } else {
                    textDeleteRight.visibility = View.VISIBLE
                }
                getDefaultUIUtil().onDraw(
                    c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive
                )
            }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int)
    }
}