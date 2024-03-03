package com.example.workshop

import com.example.workshop.ApplicationUtils.secretKey
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Authorization: Bearer sk_test_51OoUtKLXSkHpkE9znyOLsRxbsTeOvmXwdQTLkxsHVUcOhf0zf8fspGQOPoIuZpMyfe3b2RAUG6ZnHGdto2W3CeFE00bbBfyAXv")
    @POST("v1/customers")
    suspend fun getCustomer():Response<CustomerModel>

    @Headers("Authorization: Bearer sk_test_51OoUtKLXSkHpkE9znyOLsRxbsTeOvmXwdQTLkxsHVUcOhf0zf8fspGQOPoIuZpMyfe3b2RAUG6ZnHGdto2W3CeFE00bbBfyAXv",
        "Stripe-Version: 2023-10-16")
    @POST("v1/ephemeral_keys")
    suspend fun getEphemeralKey(
        @Query("customer") customer: String
    ):Response<CustomerModel>

    @Headers("Authorization: Bearer sk_test_51OoUtKLXSkHpkE9znyOLsRxbsTeOvmXwdQTLkxsHVUcOhf0zf8fspGQOPoIuZpMyfe3b2RAUG6ZnHGdto2W3CeFE00bbBfyAXv")
    @POST("v1/payment_intents")
    suspend fun getPaymentIntent(
        @Query("customer") customer: String,
        @Query("amount") amount: String= "100",
        @Query("currency") currency: String ="usd",
    ):Response<PaymenetIntentModel>

}