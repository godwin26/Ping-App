package com.example.ping

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.ping.databinding.MainActivityBinding
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity: Activity()
{
    private var mBinding : MainActivityBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

        mBinding?.ping?.setOnClickListener {

            val pingData =  pingIpAddress(mBinding?.ipEditText?.text.toString())

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("packets", pingData)
            intent.putExtra("ip", mBinding?.ipEditText?.text.toString())
            startActivity(intent)
        }

        // Assuming you have a TextView with id "textView" in your layout XML file
        // Assuming you have a TextView with id "textView" in your layout XML file
        mBinding?.ping1?.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }
    }

    private fun pingIpAddress(ipAddress: String): String {
        val runtime = Runtime.getRuntime()

        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 5 $ipAddress")
            val inputStream = BufferedReader(InputStreamReader(ipProcess.inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            var packetsReceived = 0

            while (inputStream.readLine().also { line = it } != null)
            {
                stringBuilder.append(line).append("\n")
                if (line?.contains("icmp_seq=") == true)
                {
                    packetsReceived++
                    if (packetsReceived >= 5)
                    {
                        break
                    }
                }
            }

            ipProcess.waitFor()

            return stringBuilder.toString()
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
        return ""
    }
}