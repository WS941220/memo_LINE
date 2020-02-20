package com.example.memo_line.ui.addeditmemo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memo_line.R
import com.example.memo_line.ui.FullScreenImgActivity
import com.example.memo_line.ui.main.AddEditMemoContract
import com.example.memo_line.util.showSnackBar
import com.example.practice_test.di.Scoped.ActivityScoped
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ActivityScoped
class AddEditMemoFragment : DaggerFragment(), AddEditMemoContract.View,
    AddEditMemoAdapter.onItemClickListener {

    companion object {
        const val ARGUMENT_SHOW_MEMO_ID = "SHOW_MEMO_ID"
        const val PICK_GALLERY_ID = 1
        const val PICK_CAMERA_ID = 2

        fun newInstance(memoId: String?) =
            AddEditMemoFragment().apply {
                arguments = Bundle().apply {
                    putString(AddEditMemoFragment.ARGUMENT_SHOW_MEMO_ID, memoId)
                }
            }
    }

    override var isShow: Boolean = false
    override var isEdit: Boolean = false

    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var picRecyler: RecyclerView

    private var currentPhotoPath = ""
    private lateinit var photoURI: Uri

    private val picItem = ArrayList<Uri>(0)
    private val picItem2 = ArrayList<String>(0)
    private lateinit var picAdapter: AddEditMemoAdapter

    @Inject
    lateinit var presenter: AddEditMemoContract.Presenter

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_memo_done)?.apply {
            setImageResource(R.drawable.ic_done)
            setOnClickListener {
                for (i in 0..picItem.size - 1) {
                    picItem2.add(picItem.get(i).toString())
                }
                presenter.saveMemo(title.text.toString(), content.text.toString(), picItem2)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_add_edit_memo, container, false)
        picAdapter = AddEditMemoAdapter(context, picItem, View.GONE, this)

        with(rootView) {
            picRecyler = findViewById(R.id.picRecyler)
            title = findViewById(R.id.add_memo_title)
            content = findViewById(R.id.add_memo_content)
        }
        picRecyler.layoutManager = GridLayoutManager(context, 3)
        picRecyler.adapter = picAdapter

        editFocusListener()
        setHasOptionsMenu(true)

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode, data)
    }


    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setContent(content: String) {
        this.content.text = content
    }

    override fun setImages(images: List<String>) {
        for (i in 0..images.size - 1) {
            if (!images.get(i).equals("")) {
                picItem.add(Uri.parse(images.get(i)))
            }
        }
        picAdapter.pics = picItem
        picAdapter.notifyDataSetChanged()
    }

    override fun onShow() {
        isShow = true
        isEdit = false
        title.clearFocus()
        content.clearFocus()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.nothing)
        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_memo_done)?.visibility =
            View.GONE
        requireActivity().invalidateOptionsMenu();
    }

    override fun onEdit() {
        isEdit = true
        requireActivity().invalidateOptionsMenu();
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.edit_memo)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab_edit_memo_done)
        fab?.visibility = View.VISIBLE
        fab?.apply {
            setImageResource(R.drawable.ic_done)
            setOnClickListener {
                for (i in 0..picItem.size - 1) {
                    picItem2.add(picItem.get(i).toString())
                }
                presenter.saveMemo(title.text.toString(), content.text.toString(), picItem2)
            }
        }
    }


    /**
     * 스낵바 메시지
     */
    override fun showMessage(msg: String) {
        view?.showSnackBar(msg, Snackbar.LENGTH_LONG)
    }

    /**
     * 툴바메뉴 아이템 inflate
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!isShow) {
            inflater.inflate(R.menu.add_fragment_menu, menu)
        } else if (isShow && isEdit) {
            inflater.inflate(R.menu.add_fragment_menu, menu)
        }
    }

    /**
     * 툴바메뉴 설정
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
            R.id.menu_attach -> showFilteringPopUpMenu()
        }
        return true
    }

    /**
     * 툴바메뉴 아이템
     */
    override fun showFilteringPopUpMenu() {
        val activity = activity ?: return
        val context = context ?: return
        PopupMenu(context, activity.findViewById(R.id.menu_attach)).apply {
            menuInflater.inflate(R.menu.attach_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.camera -> presenter.callCamera()
                    R.id.gallery -> presenter.callGallery()
                }
//                presenter.loadTasks(false)
                true
            }
            show()
        }
    }

    /**
     * 갤러리 권한 체크
     */
    override fun showGallery() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_GALLERY_ID
            )
        } else {
            openGallery()
        }
    }

    @SuppressLint("IntentReset")
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*")
        startActivityForResult(Intent.createChooser(intent, "photo"), PICK_GALLERY_ID)
    }

    /**
     * 갤러리 사진 선택 후
     */
    override fun showSuccessGallery(data: Intent?) {
        val clipData = data?.clipData
        if (data == null) {
            presenter.showMessage(getString(R.string.fail_multi_gallery))
        } else {
            if (clipData == null) {
                picItem.add(data.data!!)
            } else {
                for (i in 0..clipData.itemCount - 1) {
                    val imagePath = clipData.getItemAt(i).uri
                    picItem.add(imagePath)
                }
            }
            picAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 카메라 권한 체크
     */
    override fun showCamera() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.CAMERA), PICK_CAMERA_ID
            )
        } else {
            openCamera()
        }
    }

    /**
     * 카메라 실행
     */
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            // Ensure that there's a camera activity to handle the intent
            intent.resolveActivity(context!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        context!!,
                        "com.example.memo_line",
                        it
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, PICK_CAMERA_ID)
                }
            }
        }

    }


    /**
     * custom file 경로
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    /**
     * 카메라 사진 추가
     */
    override fun showSuccessCamera() {
        picItem.add(photoURI)
        picAdapter.notifyDataSetChanged()
    }

    /**
     * 사진 삭제
     */
    override fun itemRemove(position: Int) {
        picItem.removeAt(position)
        picAdapter.notifyDataSetChanged()
    }


    override fun showMemosList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun fullImage(image: Uri) {
        val intent = Intent(this.context, FullScreenImgActivity::class.java)
        intent.putExtra("uri", image)
        startActivity(intent)
    }

    private fun editFocusListener() {
        title.setOnFocusChangeListener { v, hasFocus ->
            if (isShow) {
                onEdit()
            }
        }

        content.setOnFocusChangeListener { v, hasFocus ->
            if (isShow) {
                onEdit()
            }
        }
    }


}
