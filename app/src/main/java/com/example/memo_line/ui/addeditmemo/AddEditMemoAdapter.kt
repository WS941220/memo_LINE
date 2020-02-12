package com.example.memo_line.ui.addeditmemo

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.memo_line.R
import java.io.File

class AddEditMemoAdapter(private val context: Context?, private val list: ArrayList<Uri>,
                         fragment: Fragment
): RecyclerView.Adapter<AddEditMemoAdapter.AddEditMemoViewHolder>() {


    private val listener: onItemClickListener

    init {
        this.listener = fragment as onItemClickListener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddEditMemoViewHolder, position: Int){

        var image = list[position]
        holder.pic!!.setImageURI(image)

        holder.picCard!!.setOnClickListener {
//            listener.itemDetail(post.id.toString()!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddEditMemoViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_add_edit_memo, parent, false)

        return AddEditMemoViewHolder(itemView)
    }

    class AddEditMemoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val picCard = itemView.findViewById<CardView>(R.id.picCard)
        val pic = itemView.findViewById<ImageView>(R.id.pic)

    }

    interface onItemClickListener {

    }

}
