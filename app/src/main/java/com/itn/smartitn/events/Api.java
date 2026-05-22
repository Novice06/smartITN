package com.itn.smartitn.events;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("API/events")
    Call<Event.ListResponse> getEvents();

    @GET("API/event/{id}")
    Call<Event.DetailResponse> getEvent(@Path("id") int id);
}