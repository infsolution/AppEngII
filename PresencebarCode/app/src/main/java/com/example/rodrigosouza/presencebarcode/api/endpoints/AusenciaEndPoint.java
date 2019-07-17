package com.example.rodrigosouza.presencebarcode.api.endpoints;

import com.example.rodrigosouza.presencebarcode.model.AusenciaInteresse;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AusenciaEndPoint {
    @GET("declaracoes-ausencias/")
    Call<List<DeclaracaoAusencia>> getAusencia();
}


