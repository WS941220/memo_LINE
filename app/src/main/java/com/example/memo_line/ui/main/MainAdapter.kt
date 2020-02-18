package com.example.memo_line.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memo_line.R
import com.example.memo_line.data.Memo
import com.example.memo_line.ui.addeditmemo.AddEditMemoAdapter
import java.io.File

class MainAdapter(
    private val context: Context?, var memos: List<Memo>, var visible: Int,
    fragment: Fragment
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val listener: onItemClickListener

    init {
        this.listener = fragment as onItemClickListener
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val title = memos[position].title
        val content = memos[position].content
        val image = Uri.parse(memos[position].image[0])

        holder.title.setText(title)
        holder.content.setText(content)
        Glide.with(this.context!!).load(image).centerCrop().into(holder.pic)

        holder.check.visibility = visible

        if(visible == View.VISIBLE) {
            holder.check.isChecked = false
            holder.mainCard.setOnClickListener(View.OnClickListener { v ->
                holder.check.isChecked = !holder.check.isChecked
            })
            holder.check.setOnCheckedChangeListener { v, isChecked ->
                memos[position].isCompleted = holder.check.isChecked
            }
        }

    }

    override fun getItemCount(): Int = memos.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.MainViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MainViewHolder(itemView)
    }



    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainCard = itemView.findViewById<CardView>(R.id.mainCard)
        val title = itemView.findViewById<TextView>(R.id.title)
        val content = itemView.findViewById<TextView>(R.id.content)
        val pic = itemView.findViewById<ImageView>(R.id.pic)
        val check = itemView.findViewById<CheckBox>(R.id.check)

    }

    interface onItemClickListener {

    }

}