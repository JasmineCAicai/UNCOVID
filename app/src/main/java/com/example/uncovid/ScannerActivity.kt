package com.example.uncovid

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import com.example.uncovid.DB.CDBHelper
import com.example.uncovid.DB.QrResultDataBase
import com.example.uncovid.entity.DBHelperI
import com.example.uncovid.entity.QrResult
import com.example.uncovid.utils.toFormattedDisplay
import java.util.*


class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler{
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 102
    }

//    private lateinit var mView: View
    lateinit var scannerView: ZBarScannerView
    lateinit var resultDialog: QrCodeResultDialog
    private lateinit var dbHelperI: DBHelperI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkForPermission()

        init()
        initViews()
        setContentView(scannerView)

        var qrResult = QrResult(result = "Text", resultType = "TEXT", favourite = false, calendar = Calendar.getInstance().toFormattedDisplay())
        QrResultDataBase.getAppDatabase(this)?.getQrDao()?.insertQrResult(qrResult)


//        scannerBack1Btn.setOnClickListener {
//            finish()
//        }

    }

    private fun initViews() {
        initializeQRCamera()
    }

    private fun init() {
        dbHelperI = CDBHelper(QrResultDataBase.getAppDatabase(this!!)!!)
    }


    private fun setResultDialog() {
        resultDialog = QrCodeResultDialog(this!!)
//        resultDialog.setOnDismissListener(object : QrCodeResultDialog.OnDismissListener {
//            override fun onDismiss() {
//                resetPreview()
//            }
//        })
    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Camera permission granted1", Toast.LENGTH_SHORT).show()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            } else if (isPermanentlyDenied()) {
                showGoToAppSettingsDialog()
            } else
                requestPermission()
        }
    }

    private fun isPermanentlyDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA).not()
        } else {
            return false
        }
    }

    private fun showGoToAppSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Grant Permissions!")
            .setMessage("We need camera permission to scan QR code. Go to App setting and manage permissions.")
            .setPositiveButton("Grant") { _, _ ->
                goToAppSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                run {
                    finish()
                }
            }.show()
    }

    private fun goToAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        checkForPermission()
    }

    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(this)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(this!!, R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(this!!, R.color.teal_200))
        scannerView.setLaserColor(ContextCompat.getColor(this!!, R.color.teal_200))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setupScanner()
        scannerView.setAutoFocus(true)
        startQRCamera()
        //mView.containerScanner.addView(scannerView)
    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult?.contents)
//        scannerView.resumeCameraPreview(this)
    }

    private fun onQrResult(contents: String?) {
        if (contents.isNullOrEmpty())
            showToast("Empty Qr Result")
        else
            saveToDataBase(contents)
    }

    private fun showToast(message: String) {
        Toast.makeText(this!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveToDataBase(contents: String) {
        val insertedResultId = dbHelperI.insertQRResult(contents)
//        val qrResult = dbHelperI.getQRResult(insertedResultId)

        val intent = Intent(this, LocationDetailActivity::class.java)
        intent.putExtra("QRid",insertedResultId)
        startActivity(intent)

        onDestroy()
//        resultDialog.show(qrResult)
    }


//    private fun startScanning(){
//        val scannerView = findViewById<CodeScannerView>(R.id.scannerView)
//        codeScanner = CodeScanner(this, scannerView)
//
//        // Parameters (default values)
//        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
//        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
//        // ex. listOf(BarcodeFormat.QR_CODE)
//        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
//        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
//        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
//        codeScanner.isFlashEnabled = false // Whether to enable flash or not
//
//        // Callbacks
//        codeScanner.decodeCallback = DecodeCallback {
//            runOnUiThread {
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
//            }
//        }
//        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
//            runOnUiThread {
//                Toast.makeText(
//                    this, "Camera initialization error: ${it.message}",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//
//        scannerView.setOnClickListener {
//            codeScanner.startPreview()
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode === 123){
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
//                startScanning()
//            }else{
//                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (::codeScanner.isInitialized) {
//            codeScanner?.startPreview()
//        }
//    }
//
//    override fun onPause() {
//        if (::codeScanner.isInitialized) {
//            codeScanner?.releaseResources()
//        }
//        super.onPause()
//    }
    }


