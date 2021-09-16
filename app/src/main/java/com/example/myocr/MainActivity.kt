package com.example.myocr

import android.Manifest
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myocr.camera.MyCamera
import com.example.myocr.databinding.ActivityMainBinding
import com.example.myocr.network.RetrofitClient
import com.example.myocr.network.RetrofitService
import com.example.myocr.util.MyEncoder
import com.example.myocr.viewmodel.MyViewModel
import com.example.myocr.vo.Example
import com.example.myocr.vo.Field
import com.example.myocr.vo2.RequestData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //camera
    private val camera = MyCamera()
    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private lateinit var cameraExecutor: ExecutorService

    //picture taken
    var imgFile: File? = null

    //picture data
    var data: String? = null

    //encoder
    private val myEncoder = MyEncoder()

    private val viewModel: MyViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.activity = this
        setContentView(binding.root)

        val dataObserver: Observer<java.lang.StringBuilder> =
            Observer {
                binding.progressCircular.visibility = View.GONE
                binding.tvResult.text = it
            }

        //attach observer to viewModel
        viewModel.liveData_String.observe(this, dataObserver)

        // Request camera permissions
        if (allPermissionsGranted()) {
            camera.startCamera(this, binding.viewFinder, this)
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        setCamera()
    }

    private fun setCamera() {
        camera.startCamera(this, binding.viewFinder, this)
        //directory for saving img file
        camera.outputDirectory = camera.getOutputDirectory(
            contextWrapper = ContextWrapper(this)
        )
        //to execute Camera
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(v: View) {
        when (v) {
            binding.btn -> {
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
                if (imgFile != null) {
                    //send request
                    binding.progressCircular.visibility = View.VISIBLE
                    data =
                        myEncoder.encodeImage(myEncoder.getBitmap(binding.iv.drawable.toBitmap()))
                    data?.let {
                        viewModel.setInferred(it)
                    }
                } else {
                    Toast.makeText(this, "photo_not_taken", Toast.LENGTH_SHORT).show()
                }
            }
            binding.imgRefreshButton -> {
                binding.iv.visibility = View.GONE
                binding.tvResult.text = ""
            }
            camera_capture_button -> {
                camera.takePhoto(this)
                camera.onFileReady = {
                    imgFile = it
                }
                camera.onUriReady = {
                    //show picture taken
                    binding.iv.setImageURI(it)
                    binding.iv.visibility = View.VISIBLE
                    val msg = "Photo capture succeeded: $it"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //request permission
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    //request Permission result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                camera.startCamera(this, binding.viewFinder, this)
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}