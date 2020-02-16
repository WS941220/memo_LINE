package com.example.memo_line.ui.main


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memo_line.R
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.di.DaggerFragmentComponent
import com.example.memo_line.di.module.FragmentModule
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.memo_line.ui.addeditmemo.AddEditMemoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainFragment : Fragment(), MainContract.View, MainAdapter.onItemClickListener {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    @Inject
    lateinit var presenter: MainContract.Presenter
    private lateinit var rootView: View

    private lateinit var mainRecycler: RecyclerView

    private val mainItem = ArrayList<Memo>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
//        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_main, container, false)
        mainAdapter = MainAdapter(context, mainItem, this)
        presenter.loadMemos()

        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply {
            setImageResource(R.drawable.ic_create)
            setOnClickListener { presenter.addNewMemo() }
        }

        with(rootView) {
            mainRecycler = findViewById(R.id.mainRecycler)
        }
        mainRecycler.layoutManager = LinearLayoutManager(context)
        mainRecycler.adapter = mainAdapter

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun showMemos(memos: ArrayList<Memo>) {
        mainAdapter.memos = memos
    }


    override fun showAddMemo() {
        val intent = Intent(context, AddEditMemoActivity::class.java)
        startActivityForResult(intent, AddEditMemoActivity.REQUEST_ADD_MEMO)
    }

    /**
     * fragment 의존성 주입
     */
    private fun injectDependency() {
        val mainComponent =
            DaggerFragmentComponent.builder().fragmentModule(FragmentModule()).build()
        mainComponent.inject(this)
    }

}
