package com.example.rodrigosouza.presencebarcode.app;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.rodrigosouza.presencebarcode.R;
import com.example.rodrigosouza.presencebarcode.adapters.AusenciaAdapter;
import com.example.rodrigosouza.presencebarcode.adapters.HorarioAdapter;
import com.example.rodrigosouza.presencebarcode.adapters.TurmaAdapter;
import com.example.rodrigosouza.presencebarcode.api.ApiService;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.model.Horario;
import com.example.rodrigosouza.presencebarcode.utils.Constants;
import com.example.rodrigosouza.presencebarcode.utils.SecurityPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Calendar;
public class AusenciaActivity extends AppCompatActivity {

    public AppBarLayout appBarLayout;
    private CalendarView calendarView;
    private TextView dateCalen;
    private ApiService apiService;
    private SecurityPreferences securityPreferences;
    private String dia;
    private RecyclerView mRecyclerView;
    private HorarioAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ausencia);
        securityPreferences = new SecurityPreferences(this);
        apiService = new ApiService(securityPreferences.getSavedString(Constants.TOKEN));
        mRecyclerView = findViewById(R.id.rv_turmas);
        mRecyclerView.setHasFixedSize(true);
        setupViews();

    }

    public void setupViews(){
        final Toolbar toolbar = findViewById(R.id.toolbar_ausencia);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Declarar Ausência");

        appBarLayout = findViewById(R.id.app_bar_ausencia);

        appBarLayout.bringToFront();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendarView = findViewById(R.id.calendarView);
        dateCalen = findViewById(R.id.txt_calendar);
        setCalendarView();
    }

    public void setCalendarView(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                goDetail(year, month, dayOfMonth);
                getHorarios(dia);
            }
        });
    }

    public void goDetail(int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dia = diaSemana(dayOfWeek);
        data = formDate(""+year, ""+month, ""+dayOfMonth);
        month += 1;
        String date = ""+dayOfMonth+"/"+month+"/"+year;
        dateCalen.setText(date);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getHorarios(String diaSemana){
        Call <List<Horario>> horarioCall = apiService.horarioEndPoint.getHorarios(diaSemana);
        horarioCall.enqueue(new Callback<List<Horario>>() {
            @Override
            public void onResponse(Call<List<Horario>> call, Response<List<Horario>> response) {
                exibirHorarios(response.body());
            }

            @Override
            public void onFailure(Call<List<Horario>> call, Throwable t) {

            }
        });
    }

    public String diaSemana(int dia){
        Log.i("MyLOG",""+dia);
        String diaSemana[] = {"NULL","DOMINGO","SEGUNDA","TERCA","QUARTA", "QUINTA", "SEXTA", "SABADO"};
        String diaDaSemana = diaSemana[dia];
        return diaDaSemana;
    }
    public String formDate(String ano, String mes, String dia){
        if(dia.length() == 1){
            dia = "0"+dia;
        }
        if(mes.length() == 1){
            mes = "0"+mes;
        }
        return ano+"-"+mes+"-"+dia;
    }

    private void exibirHorarios(List<Horario> horarios){
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HorarioAdapter(data,horarios, this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(mAdapter);
            }
        }, 300);
    }

}
