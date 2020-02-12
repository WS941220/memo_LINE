package com.example.memo_line.ui.addeditmemo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memo_line.R
import kotlinx.android.synthetic.main.fragment_add_pic.*


class AddPicFragment : Fragment() {

    private lateinit var rootView: ViewGroup
    private lateinit var glm: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_add_pic, container, false) as ViewGroup
        glm.spanCount = 3

        return rootView
    }



}
