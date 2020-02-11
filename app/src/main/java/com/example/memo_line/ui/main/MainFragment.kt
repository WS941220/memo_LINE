package com.example.memo_line.ui.main


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memo_line.R
import com.example.memo_line.di.DaggerFragmentComponent
import com.example.memo_line.di.module.FragmentModule
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainFragment : Fragment(), MainContract.View {
    companion object {
        val TAG: String = "MainFragment"
    }

    @Inject
    lateinit var presenter: MainContract.Presenter
    private lateinit var root: View

    fun newInstance(): MainFragment {
        return MainFragment()
    }

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
        root = inflater.inflate(R.layout.fragment_main, container, false)

        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply {
            setImageResource(R.drawable.ic_create)
            setOnClickListener { presenter.addNewMemo() }
        }
        setHasOptionsMenu(true)

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
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
