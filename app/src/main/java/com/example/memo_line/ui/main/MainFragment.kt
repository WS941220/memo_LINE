package com.example.memo_line.ui.main


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.memo_line.R
import com.example.memo_line.data.Memo
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import com.example.practice_test.di.Scoped.ActivityScoped
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.shared_toolbar.*
import javax.inject.Inject
import kotlin.math.acos

@ActivityScoped
class MainFragment : DaggerFragment(), MainContract.View, MainAdapter.MemoItemListener {

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

    private val mainItem = ArrayList<Memo>(0)
    private val mainItem2 = ArrayList<Memo>(0)
    private val idList = ArrayList<String>(0)

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainAdapter = MainAdapter(context, mainItem, View.GONE,this)

    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ mainRecycler.scrollToPosition(mainItem.size - 1) }, 200)
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

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.reverseLayout  = true
        mLayoutManager.stackFromEnd  = true

        mainRecycler.layoutManager = mLayoutManager
        mainRecycler.adapter = mainAdapter

        setHasOptionsMenu(true)

        return rootView
    }

    /**
     * 메뉴 inflate
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(isDelete == false) {
            inflater.inflate(R.menu.toolbar_fragment_menu, menu)
        } else {
            inflater.inflate(R.menu.delete_done_menu , menu)
        }
    }

    /**
     * 툴바 메뉴 선택
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> showCheckBox()
            R.id.menu_success -> showMain()
        }
        return true
    }

    /**
     * 편집 클릭 체크박스 활성화
     */
    fun showCheckBox() {
        isDelete = true
        requireActivity().invalidateOptionsMenu();
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.delete_memo)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply{
            setImageResource(R.drawable.ic_delete)
            setOnClickListener { deleteMemos(idList, mainItem2) }
        }
        mainAdapter.visible = View.VISIBLE
        mainAdapter.notifyDataSetChanged()
    }

    /**
     * 메인으로
     */
     fun showMain() {
        isDelete = false
        requireActivity().invalidateOptionsMenu();
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.title)
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply{
            setImageResource(R.drawable.ic_create)
            setOnClickListener { presenter.addNewMemo()}
        }
        mainAdapter.visible = View.GONE
        mainAdapter.notifyDataSetChanged()

    }

    /**
     * 메모삭제
     */
    override fun onMemoDelete(memo: Memo, id: String, check: Boolean) {
        if(check) {
            idList.add(id)
            mainItem2.add(memo)
        } else {
            idList.remove(id)
            mainItem2.remove(memo)
        }
    }

    private fun deleteMemos(IdList: List<String>, memoList: List<Memo>) {
        presenter.deleteMemos(IdList)
        mainItem.removeAll(memoList)
        mainAdapter.notifyDataSetChanged()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun showMemos(memos: List<Memo>) {
        mainItem.clear()
        mainItem.addAll(memos)
        mainAdapter.notifyDataSetChanged()
    }

    /**
     * 메모 작성
     */
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

    override fun onMemoClick(clickMemo: Memo) {
        presenter.openMemo(clickMemo)
    }

    override fun showOpenMemo(memoId: String) {
        val intent = Intent(context, AddEditMemoActivity::class.java).apply {
            putExtra(AddEditMemoFragment.ARGUMENT_SHOW_MEMO_ID, memoId)
        }
        startActivity(intent)
    }

}
