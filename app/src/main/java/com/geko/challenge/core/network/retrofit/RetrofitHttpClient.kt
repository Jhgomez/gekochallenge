package com.geko.challenge.core.network.retrofit

import com.geko.challenge.core.network.model.NetworkUser
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitNetworkApi {
    @POST("login")
    suspend fun login(@Body parameters: Map<String, Any>): NetworkResponse<NetworkUser>

    @POST("signUp")
    suspend fun signUp(@Body parameters: Map<String, Any>): NetworkResponse<NetworkUser>

    @POST("requestPasswordReset")
    suspend fun resetPassword(@Body parameters: Map<String, Any>): NetworkResponse<Any>

    @POST("logout")
    suspend fun logout(): NetworkResponse<Any>
}

@Serializable
data class NetworkResponse<T>(
    val data: T,
)

//@Singleton
//internal class RetrofitNiaNetwork @Inject constructor(
//    networkJson: Json,
//    okhttpCallFactory: dagger.Lazy<Call.Factory>,
//) : ChallengeHttpClient {
//
//    private val networkApi =
//        Retrofit.Builder()
//            .baseUrl("URL")
//            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
//            // to prevent initializing OkHttp on the main thread.
//            .callFactory { okhttpCallFactory.get().newCall(it) }
//            .addConverterFactory(
//                networkJson.asConverterFactory("application/json".toMediaType()),
//            )
//            .build()
//            .create(RetrofitNetworkApi::class.java)
//
//}
