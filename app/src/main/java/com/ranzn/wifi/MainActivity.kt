package com.ranzn.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * Location should be given to get the wifi states..
 *
 */

class MainActivity : AppCompatActivity() {
    private lateinit var wifiManager: WifiManager
    lateinit var results: MutableList<ScanResult>
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        registerWifiReceiver()

        //For Manual Scan and checking all results
        scanBtn.setOnClickListener {
            results = wifiManager.scanResults
            Log.d("ranjan", "onCreate: $results")
            setText()
        }

    }


    private fun registerWifiReceiver() {

        registerReceiver(object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val success: Boolean = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                //Automatically getting wifi changes
                if (success) {
                    scanSuccess()
                } else {
                    scanFailure()
                }
            }
        }, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))

    }

    /**
     * For automatically getting wifi data
     */
    private fun scanSuccess() {
        results = wifiManager.scanResults
        setText()


    }

    private fun scanFailure() {
        results = wifiManager.scanResults
        setText()
    }

    private fun setText() {
        var str = ""
        for (i in 0 until results.size) {
            str += results[i].SSID.toString() + "\n"
        }
        text.text = str.toString()
    }
}