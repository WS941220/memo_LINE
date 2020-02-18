package com.example.memo_line.ui.main


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.memo_line.R
import com.example.memo_line.data.Memo
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.practice_test.di.Scoped.ActivityScoped
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.math.acos

@ActivityScoped
class MainFragment : DaggerFragment(), MainContract.View, MainAdapter.onItemClickListener,
    MainActivity.onBackPressedListener {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
    @Inject
    lateinit var presenter: MainContract.Presenter

    override var isDelete: Boolean = false

    private lateinit var rootView: View

    private lateinit var mainRecycler: RecyclerView

    private val mainItem = ArrayList<Memo>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainAdapter = MainAdapter(context, mainItem, View.GONE,this)
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_main, container, false)

        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply {
            setImageResource(R.drawable.ic_create)
            setOnClickListener { presenter.addNewMemo() }
        }

        with(rootView) {
            mainRecycler = findViewById(R.id.mainRecycler)

            findViewById<ScrollChildSwipeRefreshLayout>(R.id.refresh_layout).apply {
                setColorSchemeColors(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark),
                    ContextCompat.getColor(requireContext(), R.color.white),
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                // Set the scrolling view in the custom SwipeRefreshLayout.
                scrollUpChild = mainRecycler
                setOnRefreshListener { presenter.loadMemos(false) }
            }
        }
        mainRecycler.layoutManager = LinearLayoutManager(context)
        mainRecycler.adapter = mainAdapter

        setHasOptionsMenu(true)

        return rootView
    }

    /**
     * 메뉴 inflate
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_fragment_menu, menu)
    }

    /**
     * 툴바 메뉴 선택
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> showCheckBox()
        }
        return true
    }

    /**
     * 편집 클릭 체크박스 활성화
     */
    private fun showCheckBox() {
        isDelete = true
        mainAdapter.visible = View.VISIBLE
        mainAdapter.notifyDataSetChanged()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun showMemos(memos: List<Memo>) {
        mainAdapter.memos = memos
        mainAdapter.notifyDataSetChanged()
    }


    override fun showAddMemo() {
        val intent = Intent(context, AddEditMemoActivity::class.java)
        startActivityForResult(intent, AddEditMemoActivity.REQUEST_ADD_MEMO)
    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)) {
            // Make sure setRefreshing() is called after the layout is done with everything else.
            post { isRefreshing = active }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity: MainActivity = MainActivity()
        activity.setOnBackPressedListener(this)
    }

    override fun onBack() {
        if(isDelete == true) {
            mainAdapter.visible = View.GONE
            mainAdapter.notifyDataSetChanged()
        } else {
            val activity: MainActivity = MainActivity()
            activity.setOnBackPressedListener(null)
        }
    }

}
