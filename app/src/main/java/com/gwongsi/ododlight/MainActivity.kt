package com.gwongsi.ododlight

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.camerakit.CameraKitView
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build




class MainActivity : AppCompatActivity() {
    lateinit var cameraKitView: CameraKitView
    lateinit var imageButton: Button
    lateinit var videoButton: Button
    lateinit var popoButton: Button

    private var torchIsOn = false
    private var hasTorch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraKitView = findViewById<View>(R.id.camera) as CameraKitView

        hasTorch = applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        imageButton = findViewById(R.id.imageBtn)
        imageButton.setOnClickListener(View.OnClickListener {
            cameraKitView.captureImage(CameraKitView.ImageCallback { cameraKitView, bytes ->
                Log.e("Return Image: ", "Size: ${bytes.size}")
            })

        })
        videoButton = findViewById(R.id.videoBtn)
        videoButton.setOnClickListener(View.OnClickListener {
            cameraKitView.captureVideo(CameraKitView.VideoCallback { cameraKitView, any ->
                Log.e("Return Video: ","")
            })

        })
        popoButton = findViewById(R.id.popoBtn)
        popoButton.setOnClickListener(View.OnClickListener {
            popoLightToggle()
            Log.e("popoLightToggle ","")
        })
    }

    private fun popoLightToggle() {
        if (torchIsOn){
            setTorchOff()
        } else {
            setTorchOn()
        }
    }

    private fun setTorchOn() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {

            val cameraId = cameraManager.getCameraIdList()[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true)
            }
            torchIsOn = true
//            imageFlashlight.setImageResource(R.drawable.btn_switch_on)
        } catch (e: CameraAccessException) {
            Log.e("popoLightToggle ", e.localizedMessage)
        }

    }

    private fun setTorchOff() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {

            val cameraId = cameraManager.getCameraIdList()[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false)
            }
            torchIsOn = true
//            imageFlashlight.setImageResource(R.drawable.btn_switch_on)
        } catch (e: CameraAccessException) {
        }

    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }

    override fun onPause() {
        cameraKitView.onPause()
        super.onPause()
    }

    override fun onStop() {
        cameraKitView.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
