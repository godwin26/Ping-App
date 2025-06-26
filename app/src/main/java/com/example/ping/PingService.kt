package com.example.ping

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class PingService : Service() {

    companion object {
        private var IP_ADDRESS = ""
    }


    private var isServiceRunning = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        IP_ADDRESS = intent?.getStringExtra("ip").orEmpty()

        if (!isServiceRunning) {
            isServiceRunning = true
            startPingTask()
        }

        return START_STICKY
    }

    private fun startPingTask() {
        Thread {
            while (isServiceRunning) {
                try {
                    val isReachable = pingIpAddress(IP_ADDRESS)
                    if (isReachable.first) {
                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("packets", isReachable.second)
                        intent.putExtra("ip", IP_ADDRESS)
                        this.startActivity(intent)

                    }

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                break
            }
        }.start()
    }

    private fun pingIpAddress(ipAddress: String): Pair<Boolean, String> {
        val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 $ipAddress")

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val result = StringBuilder()

        var packetSize = 5
        var line: String?
        while (reader.readLine().also { line = it } != null && packetSize >0)
        {
            packetSize-=1
            result.append(line).append("\n")
        }

        val exitValue = try {
            process.waitFor()
        } catch (e: InterruptedException) {
            throw IOException("Ping process interrupted", e)
        }

        return Pair(exitValue ==0, result.toString())
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        isServiceRunning = false
        super.onDestroy()
    }
}
