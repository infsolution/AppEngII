package com.example.rodrigosouza.presencebarcode.api.endpoints;

import com.example.rodrigosouza.presencebarcode.model.Horario;
import com.example.rodrigosouza.presencebarcode.model.Turma;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HorarioEndPoint {
    @GET("horarios/?")
    Call<List<Horario>>getHorarios(@Query("dia_semana") String dia);


}
