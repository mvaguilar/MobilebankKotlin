package com.finastra.mobilebankkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.finastra.mobilebankkotlin.application.NetworkCheckHelperImpl
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: NetworkCheckHelperImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectionCheck = findViewById<TextView>(R.id.connectionCheck)
        val simCheck = findViewById<TextView>(R.id.simCheck)

        connectionCheck.text = viewModel.isNetworkConnectedResult(this.application)
        simCheck.text = viewModel.getSimState(this.application)

        Toast.makeText(this, viewModel.isNetworkConnectedResult(this.application), Toast.LENGTH_LONG).show()
    }
}


