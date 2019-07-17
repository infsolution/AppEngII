package com.example.rodrigosouza.presencebarcode.api;

import com.example.rodrigosouza.presencebarcode.api.endpoints.AusenciaEndPoint;
import com.example.rodrigosouza.presencebarcode.api.endpoints.DeclararintereseEndPoint;
import com.example.rodrigosouza.presencebarcode.api.endpoints.DisponibilizarEndPoint;
import com.example.rodrigosouza.presencebarcode.api.endpoints.HorarioEndPoint;
import com.example.rodrigosouza.presencebarcode.api.endpoints.LoginEndPoint;
import com.example.rodrigosouza.presencebarcode.api.endpoints.RegistroEndPoint;
import com.example.rodrigosouza.presencebarcode.api.endpoints.TurmaEndPoint;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.model.Horario;
import com.example.rodrigosouza.presencebarcode.model.Interesse;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static  final String BASE_URL = "http://classroomifpi.ga/api/";
    public Retrofit retrofit;
    public Interceptor interceptor;

    public RegistroEndPoint registroEndPoint;
    public LoginEndPoint loginEndPoint;
    public TurmaEndPoint turmaEndPoint;
    public HorarioEndPoint horarioEndPoint;
    public AusenciaEndPoint ausenciaEndPoint;
    public DeclararintereseEndPoint declararintereseEndPoint;
    public DisponibilizarEndPoint declaracaoAusencia;

    public ApiService(String token){
        this.interceptor = new InterceptorAPI("token " + token);

        OkHttpClient.Builder builderCliente = new OkHttpClient.Builder();
        builderCliente.interceptors().add(this.interceptor);
        OkHttpClient cliente = builderCliente.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder.baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)
                .build();

        loginEndPoint = retrofit.create(LoginEndPoint.class);
        registroEndPoint = retrofit.create(RegistroEndPoint.class);
        turmaEndPoint = retrofit.create(TurmaEndPoint.class);
        horarioEndPoint = retrofit.create(HorarioEndPoint.class);
        ausenciaEndPoint = retrofit.create((AusenciaEndPoint.class));
        declararintereseEndPoint = retrofit.create((DeclararintereseEndPoint.class));
        declaracaoAusencia = retrofit.create(DisponibilizarEndPoint.class);

    }
}
