package com.example.workshop

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.workshop.ApplicationUtils.publishKey
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var paymentSheet: PaymentSheet

    lateinit var customerId: String
    lateinit var ephemId: String
    lateinit var secretId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PaymentConfiguration.init(this, publishKey)
        getCustomerId()


        val btn = findViewById<Button>(R.id.payBtn)

        btn.setOnClickListener {
            paymentFlow()
        }

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
    }

    private fun paymentFlow() {
        paymentSheet.presentWithPaymentIntent(
            secretId,
            PaymentSheet.Configuration(
                "hola coderas",
                PaymentSheet.CustomerConfiguration(customerId,ephemId)
            )
        )
    }

    var apiInterface = ApplicationUtils.getApiInterface()

    fun getCustomerId(){
        lifecycleScope.launch(IO){
            val res = apiInterface.getCustomer()
            withContext(Main){
                if (res.isSuccessful && res.body()!=null){
                    Log.d("dfsasdfdf", "getCustomerId: ${res.body()!!.id}")
                    getEpheKey(res.body()!!.id)
                }
            }
        }
    }

    private fun getEpheKey(customerId: String) {
        lifecycleScope.launch(IO){
            val res = apiInterface.getEphemeralKey(customerId)
            withContext(Main){
                if (res.isSuccessful && res.body()!=null){
                    Log.d("dfsasdfdf", "getEpheKey: ${res.body()!!.id}")
                    getPaymentIntent(customerId, res.body()!!.id)
                }
            }
        }
    }

    private fun getPaymentIntent(cuId: String, epheId: String) {
        customerId = cuId
        ephemId = epheId
        lifecycleScope.launch(IO){
            val res = apiInterface.getPaymentIntent(cuId)
            withContext(Main){
                if (res.isSuccessful){
                    secretId = res.body()!!.client_secret
                    Log.d("dfsasdfdf", "getPaymentIntent: ${secretId}")
                    Toast.makeText(this@MainActivity, "Process the payment", Toast.LENGTH_SHORT).show()
//                    getPaymentIntent(customerId, res.body()!!.id)
                }else{
                    Log.d("dfsasdfdf", "getPaymentIntent: failed ${res.raw().body().toString()}")
                    Log.d("dfsasdfdf", "getPaymentIntent: failed ${res.toString()}")
                    Log.d("dfsasdfdf", "getPaymentIntent: failed ${res.message()}")
                    Log.d("dfsasdfdf", "getPaymentIntent: failed ${res.code()}")

                }
            }
        }
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {

        Log.d("dfsasdfdf", "onPaymentSheetResult: ${paymentSheetResult.toString()}")

        if (paymentSheetResult is PaymentSheetResult.Completed){
            Toast.makeText(this, "Payment Completed", Toast.LENGTH_SHORT).show()
        }

        if (paymentSheetResult is PaymentSheetResult.Failed){
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
        }

    }
}
