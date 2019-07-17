package com.example.rodrigosouza.presencebarcode.api.endpoints;

import com.example.rodrigosouza.presencebarcode.model.Horario;
import com.example.rodrigosouza.presencebarcode.model.Turma;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HorarioEndPoint {
    @GET("turmas/")
    Call<List<Horario>> getHorarios();
}
