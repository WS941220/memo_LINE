package com.example.memo_line.ui.addeditmemo

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.memo_line.R
import com.example.memo_line.ui.FullScreenImgActivity
import java.io.File

class AddEditMemoAdapter(
    private val context: Context?, var pics: ArrayList<Uri>, var visible: Int,
    fragment: Fragment
) : RecyclerView.Adapter<AddEditMemoAdapter.AddEditMemoViewHolder>() {

    private val listener: onItemClickListener

    init {
        this.listener = fragment as onItemClickListener
    }

    override fun getItemCount(): Int = pics.size

    override fun onBindViewHolder(holder: AddEditMemoViewHolder, position: Int) {

        val image = pics[position]
        Glide.with(this.context!!).load(image)
            .error(R.drawable.ic_not_found)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    p2: com.bumptech.glide.request.target.Target<Drawable>?,
                    p3: Boolean
                ): Boolean {
                    listener.failImage()
                    return false
                }

                override fun onResourceReady(
                    p0: Drawable?,
                    p1: Any?,
                    p2: com.bumptech.glide.request.target.Target<Drawable>?,
                    p3: DataSource?,
                    p4: Boolean
                ): Boolean {
                    return false
                }
            })
            .centerCrop().into(holder.pic)

        holder.picRemove.visibility = visible

        holder.picRemove.setOnClickListener {
            listener.itemRemove(position)
        }

        holder.itemView.setOnClickListener {
            listener.fullImage(image)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddEditMemoViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.item_add_edit_memo, parent, false)

        return AddEditMemoViewHolder(itemView)
    }


    class AddEditMemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picRemove: ImageButton = itemView.findViewById(R.id.picRemove)
        val pic: ImageView = itemView.findViewById(R.id.pic)

    }

    interface onItemClickListener {
        fun itemRemove(position: Int)

        fun fullImage(image: Uri)

        fun failImage()
    }

}
