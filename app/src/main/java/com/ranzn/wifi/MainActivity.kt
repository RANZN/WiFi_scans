package com.ranzn.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        registerWifiReceiver()

        //For Manual Scan
        scanBtn.setOnClickListener {
            val result = wifiManager.scanResults
            Log.d("ranjan", "getWifi: $result")
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
        val results = wifiManager.scanResults
        Log.d("ranjan", "scanSuccess: ${results.toString()}")
    }

    private fun scanFailure() {
        val results = wifiManager.scanResults
        Log.d("ranjan", "scanFailure: ${results.toString()}")
    }

}