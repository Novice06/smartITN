package com.itn.smartitn.auth.network;


import com.itn.smartitn.auth.models.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("/API/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("/API/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
