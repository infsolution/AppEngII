package com.example.rodrigosouza.presencebarcode.app;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rodrigosouza.presencebarcode.R;
import com.example.rodrigosouza.presencebarcode.adapters.AusenciaAdapter;
import com.example.rodrigosouza.presencebarcode.api.ApiService;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.utils.Constants;
import com.example.rodrigosouza.presencebarcode.utils.SecurityPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InteresseActivity extends AppCompatActivity {

    public AppBarLayout appBarLayout;
    private ApiService apiService;
    private SecurityPreferences securityPreferences;
    private RecyclerView mRecyclerView;
    private AusenciaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesse);
        securityPreferences = new SecurityPreferences(this);
        apiService = new ApiService(securityPreferences.getSavedString(Constants.TOKEN));
        mRecyclerView = findViewById(R.id.rv_ausencias);
        mRecyclerView.setHasFixedSize(true);
        setupViews();
    }

    public void setupViews(){
        final Toolbar toolbar = findViewById(R.id.toolbar_interesse);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Declarar Interesse");
        appBarLayout = findViewById(R.id.app_bar_interesse);
        appBarLayout.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDeclaracoesAusencias();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDeclaracoesAusencias(){
        Call<List<DeclaracaoAusencia>> auseciasCall = apiService.ausenciaEndPoint.getAusencia();
        auseciasCall.enqueue(new Callback<List<DeclaracaoAusencia>>() {
            @Override
            public void onResponse(Call<List<DeclaracaoAusencia>> call, Response<List<DeclaracaoAusencia>> response) {
                exibirAusencias(response.body());
            }

            @Override
            public void onFailure(Call<List<DeclaracaoAusencia>> call, Throwable t) {

            }
        });
    }
    private void exibirAusencias(List<DeclaracaoAusencia> ausencias){
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AusenciaAdapter(ausencias, this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(mAdapter);
            }
        }, 300);
    }
}
