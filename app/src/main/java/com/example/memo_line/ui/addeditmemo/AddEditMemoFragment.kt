package com.example.memo_line.ui.addeditmemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memo_line.R
import com.example.memo_line.di.DaggerFragmentComponent
import com.example.memo_line.di.module.FragmentModule
import com.example.memo_line.ui.main.AddEditMemoContract
import javax.inject.Inject

class AddEditMemoFragment : Fragment(), AddEditMemoContract.View {

    @Inject
    lateinit var presenter: AddEditMemoContract.Presenter
    private lateinit var rootView: View

    fun newInstance(): AddEditMemoFragment {
        return AddEditMemoFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_add_edit_memo, container, false)
        return rootView
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

    private fun injectDependency() {
        val addEditMemoFragment =
            DaggerFragmentComponent.builder().fragmentModule(FragmentModule()).build()
        addEditMemoFragment.inject(this)
    }

}
