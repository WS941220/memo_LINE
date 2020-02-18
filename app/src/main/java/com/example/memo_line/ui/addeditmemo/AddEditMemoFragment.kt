package com.example.memo_line.ui.addeditmemo

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
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memo_line.R
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
        const val ARGUMENT_EDIT_MEMO_ID = "EDIT_MEMO_ID"
        const val PICK_GALLERY_ID = 1
        const val PICK_CAMERA_ID = 2

        fun newInstance(memoId: String?) =
            AddEditMemoFragment().apply {
                arguments = Bundle().apply {
                    putString(AddEditMemoFragment.ARGUMENT_EDIT_MEMO_ID, memoId)
                }
            }
    }


    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var picRecyler: RecyclerView

    private var currentPhotoPath = ""
    private lateinit var photoURI: Uri

    private val picItem = ArrayList<Uri>()
    private val picItem2 = ArrayList<String>()
    private lateinit var picAdapter: AddEditMemoAdapter

    @Inject
    lateinit var presenter: AddEditMemoContract.Presenter

    private lateinit var rootView: View

//    override fun onResume() {
//        super.onResume()
//        //Bind view to the presenter which will signal for the presenter to load the task.
//        presenter.start(this)
//    }
//
//    override fun onPause() {
//        presenter.dropView()
//        super.onPause()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
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
                for(i in 0..picItem.size - 1) {
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
        picAdapter = AddEditMemoAdapter(context, picItem, this)

        with(rootView) {
            picRecyler = findViewById(R.id.picRecyler)
            title = findViewById(R.id.add_memo_title)
            content = findViewById(R.id.add_memo_content)
        }
        picRecyler.layoutManager = GridLayoutManager(context, 3)
        picRecyler.adapter = picAdapter

        setHasOptionsMenu(true)

        return rootView
    }


    /**
     * 요청 응답
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode, data)
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
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    /**
     * 툴바메뉴 설정
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
     * 갤러리 실행
     */
    @SuppressLint("IntentReset")
    override fun showGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, PICK_GALLERY_ID)
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
        if (Build.VERSION.SDK_INT >= 23) {
            if (context?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.CAMERA
                    )
                } != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    context!!,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), PICK_GALLERY_ID
                    )
                }
            } else {
                openCamera()
            }
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



}
