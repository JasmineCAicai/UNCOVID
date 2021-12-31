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
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import com.example.uncovid.DB.CDBHelper
import com.example.uncovid.DB.QrResultDataBase
import com.example.uncovid.entity.DBHelperI
import com.example.uncovid.entity.QrResult
import com.example.uncovid.utils.toFormattedDisplay
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler{
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 102
    }

//    private lateinit var mView: View
    lateinit var scannerView: ZBarScannerView
    private lateinit var dbHelperI: DBHelperI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkForPermission()

        init()
        initViews()
        setContentView(scannerView)


//        var qrResult = QrResult(result = "Text", resultType = "TEXT", IDCard = "TEXT", calendar = Calendar.getInstance().toFormattedDisplay())
//        QrResultDataBase.getAppDatabase(this)?.getQrDao()?.insertQrResult(qrResult)


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
        var id = this.intent.getStringExtra("id")
        val insertedResultId = id?.let { dbHelperI.insertQRResult(contents, it)}
//        val qrResult = dbHelperI.getQRResult(insertedResultId)
        val intent = Intent(this, LocationDetailActivity::class.java)
        intent.putExtra("QRid",insertedResultId)
        startActivity(intent)

        onDestroy()
//        resultDialog.show(qrResult)
    }

    }


