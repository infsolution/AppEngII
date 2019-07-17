package com.example.rodrigosouza.presencebarcode.app;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.rodrigosouza.presencebarcode.R;
import com.example.rodrigosouza.presencebarcode.adapters.AusenciaAdapter;
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

public class AusenciaActivity extends AppCompatActivity {

    public AppBarLayout appBarLayout;
    private CalendarView calendarView;
    private TextView dateCalen;
    private ApiService apiService;
    private SecurityPreferences securityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ausencia);
        securityPreferences = new SecurityPreferences(this);
        apiService = new ApiService(securityPreferences.getSavedString(Constants.TOKEN));
        setupViews();

        getHorarios();
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
            }
        });
    }

    public void goDetail(int year, int month, int dayOfMonth){
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

    public void getHorarios(){
        Call <List<Horario>> horarioCall = apiService.horarioEndPoint.getHorarios();
        horarioCall.enqueue(new Callback<List<Horario>>() {
            @Override
            public void onResponse(Call<List<Horario>> call, Response<List<Horario>> response) {

            }

            @Override
            public void onFailure(Call<List<Horario>> call, Throwable t) {

            }
        });
    }

}
