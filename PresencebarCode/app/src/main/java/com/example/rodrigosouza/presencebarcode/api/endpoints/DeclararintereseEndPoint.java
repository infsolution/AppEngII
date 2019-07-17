package com.example.rodrigosouza.presencebarcode.api.endpoints;

import com.example.rodrigosouza.presencebarcode.model.Interesse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DeclararintereseEndPoint {
    @POST("declaracoes-interesses/")
    Call<Interesse> addInteresse(@Body Interesse interesse);
}
