package com.example.ping

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ping.databinding.SecondActivityBinding

class SecondActivity: AppCompatActivity()
{
    private var mBinding : SecondActivityBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mBinding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

        val packet = intent.getStringExtra("packets")
        val ip = intent.getStringExtra("ip")


        mBinding?.ipAddress?.text = ip
        mBinding?.result?.text = packet

        mBinding?.back?.setOnClickListener {
            this.finish()
        }

        mBinding?.mainMenu?.setOnClickListener{
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }


    }
}