//package com.example.memo_line.ui.maindetail
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.memo_line.R
//import com.example.memo_line.di.DaggerFragmentComponent
//import com.example.memo_line.di.module.FragmentModule
//import javax.inject.Inject
//
//class MainDetailFragment : Fragment(), MainDetailContract.View {
//
//    @Inject
//    lateinit var presenter: MainDetailContract.Presenter
//
//    private lateinit var rootView: View
//
//    fun newInstance(): MainDetailFragment {
//        return MainDetailFragment()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        injectDependency()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        rootView = inflater.inflate(R.layout.fragment_main_detail, container, false)
//        return rootView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        presenter.attach(this)
//        presenter.subscribe()
////        initView()
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        presenter.unsubscribe()
//    }
//
//    private fun injectDependency() {
//        val mainDetailComponent =
//            DaggerFragmentComponent.builder().fragmentModule(FragmentModule()).build()
//        mainDetailComponent.inject(this)
//    }
//
//}
