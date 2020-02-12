package com.example.memo_line.ui.addeditmemo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memo_line.R
import com.example.memo_line.di.DaggerFragmentComponent
import com.example.memo_line.di.module.FragmentModule
import com.example.memo_line.ui.main.AddEditMemoContract
import com.example.memo_line.util.showSnackBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_edit_memo.*
import java.io.File
import javax.inject.Inject

class AddEditMemoFragment : Fragment(), AddEditMemoContract.View, AddEditMemoAdapter.onItemClickListener {

    companion object {
        const val ARGUMENT_EDIT_MEMO_ID = "EDIT_MEMO_ID"
        const val PICK_GALLERY_ID = 1

        fun newInstance(memoId: String?) =
            AddEditMemoFragment().apply {
                arguments = Bundle().apply {
                    putString(AddEditMemoFragment.ARGUMENT_EDIT_MEMO_ID, memoId)
                }
            }
    }

    private lateinit var title: TextView
    private lateinit var content: TextView

    @Inject
    lateinit var presenter: AddEditMemoContract.Presenter
    private lateinit var rootView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_memo_done)?.apply {
            setImageResource(R.drawable.ic_done)
            setOnClickListener {
                presenter.saveMemo(title.text.toString(), content.text.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_add_edit_memo, container, false)

        with(rootView) {
            title = findViewById(R.id.add_memo_title)
            content = findViewById(R.id.add_memo_content)
        }
        setHasOptionsMenu(true)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode, data)
    }

    override fun showMessage(msg: String) {
        view?.showSnackBar(msg, Snackbar.LENGTH_LONG)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_attach -> showFilteringPopUpMenu()
        }
        return true
    }

    override fun showFilteringPopUpMenu() {
        val activity = activity ?: return
        val context = context ?: return
        PopupMenu(context, activity.findViewById(R.id.menu_attach)).apply {
            menuInflater.inflate(R.menu.attach_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
//                    R.id.active -> presenter.currentFiltering = TasksFilterType.ACTIVE_TASKS
                    R.id.gallery -> presenter.callGallery()
                }
//                presenter.loadTasks(false)
                true
            }
            show()
        }
    }

    @SuppressLint("IntentReset")
    override fun showGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_GALLERY_ID)
    }

    override fun showSuccessGallery(data: Intent?) {
        val clipData = data?.clipData
        if(data == null) {
        } else {
            if(clipData == null) {
                presenter.showMessage(getString(R.string.fail_multi_gallery))
            } else {
                if(clipData.itemCount > 9) {
                    presenter.showMessage(getString(R.string.fail_over_gallery))
                } else {
                    val picItem: ArrayList<Uri>
                    picItem = ArrayList()
                   for (i in 0..clipData.itemCount-1) {
                       val imagePath = clipData.getItemAt(i).uri
                       picItem.add(imagePath)
                   }

                    val adapter = AddEditMemoAdapter(context, picItem, this)
                    if(picRecycler!!.layoutManager == null) {
                        picRecycler!!.layoutManager = GridLayoutManager(context, 3)
                    } else {
                        adapter.notifyItemInserted(1)
                    }
                    picRecycler!!.setAdapter(adapter)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun showMemosList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun injectDependency() {
        val addEditMemoFragment =
            DaggerFragmentComponent.builder().fragmentModule(FragmentModule()).build()
        addEditMemoFragment.inject(this)
    }

}
