package com.example.memo_line.ui.main

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.memo_line.data.Memo
import com.example.memo_line.ui.addeditmemo.AddEditMemoAdapter

class MainAdapter(private val context: Context?, var itmes: ArrayList<Memo>,
                  fragment: Fragment
): RecyclerView.Adapter<AddEditMemoAdapter.AddEditMemoViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddEditMemoAdapter.AddEditMemoViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: AddEditMemoAdapter.AddEditMemoViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}