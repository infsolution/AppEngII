package com.example.rodrigosouza.presencebarcode.api.endpoints;

import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DisponibilizarEndPoint {
        @POST("declaracoes-ausencias/")
        Call<DeclaracaoAusencia> declararAusencia(@Body DeclaracaoAusencia ausencia);
}
