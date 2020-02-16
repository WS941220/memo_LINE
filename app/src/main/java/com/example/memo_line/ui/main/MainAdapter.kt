package com.example.memo_line.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.memo_line.R
import com.example.memo_line.data.Memo
import com.example.memo_line.ui.addeditmemo.AddEditMemoAdapter

class MainAdapter(private val context: Context?, var memos: List<Memo>,
                  fragment: Fragment
): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val listener: onItemClickListener

    init {
        this.listener = fragment as onItemClickListener
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var title = memos[position].title
        holder.title!!.setText(title)
    }

    override fun getItemCount(): Int = memos.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.MainViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)

        return MainViewHolder(itemView)
    }

    class MainViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)

    }

    interface onItemClickListener {

    }

}