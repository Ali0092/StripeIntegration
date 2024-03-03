package com.example.workshop

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApplicationUtils {

    const val publishKey = "pk_test_51OoUtKLXSkHpkE9zD0TVnK3Ih11xEwiC7NRS4COq54BA0SKPZyzf1l7idRdshR1LlvxmexkGW2OBCwSk11eaEneu00jddfoWtx"
    const val secretKey = "sk_test_51KyUN7HLyCan9nQSTxGTJhRXTh0wG42ffa3kVF2L3AFRsZkLP950wxN0SsBmCpNSWHIGkRehjILYTmAcOExiFUcR00tHQXlKgM"

    fun getApiInterface():ApiInterface{
        return Retrofit.Builder()
            .baseUrl("https://api.stripe.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)

    }

}