package com.example.rodrigosouza.presencebarcode.app;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rodrigosouza.presencebarcode.R;
import com.example.rodrigosouza.presencebarcode.model.AusenciaInteresse;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.model.Turma;

public class CargaHorariaActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;

    public String nome_disciplina;
    public int carga_horaria_h;
    public int horas_restantes;
    public int horas_antecipadas;
    public int horas_ministradas;
    public int horas_ausencias;
    private int hora_total;
    private int possibilidadeResultado;

    public TextView txt_nome_disciplina;

    //variaveis views
    public TextView txt_carga_horaria;
    public TextView txt_horas_antecipadas;
    public TextView txt_horas_ausentes;
    public TextView txt_horas_ministradas;
    public TextView txt_horas_restantes;
    public TextView txt_possibilidade;
    public TextView txt_restante_total;

    public TextView txt_hora_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_horaria);

        txt_nome_disciplina = findViewById(R.id.nome_disciplina);
        txt_carga_horaria = findViewById(R.id.input_carg_horaria);
        txt_horas_antecipadas = findViewById(R.id.input_horas_antecipadas);
        txt_horas_ausentes = findViewById(R.id.input_ausencias);
        txt_horas_ministradas  = findViewById(R.id.input_horas_ministradas);
        txt_horas_restantes = findViewById(R.id.input_horas_restantes);
        txt_possibilidade = findViewById(R.id.possibilidade);
        txt_restante_total = findViewById(R.id.restante_total);
        txt_hora_total = findViewById(R.id.input_hora_total);

        Turma turma = new Turma(); //classe Turma
        DeclaracaoAusencia ausencia = new DeclaracaoAusencia(); //ausencia
        AusenciaInteresse antecipadas = new AusenciaInteresse(); //interesse de aula

        nome_disciplina = turma.getDisciplina();

        horas_antecipadas = antecipadas.getPeso();//vem da classe Declaracao de Interesse
        horas_ausencias = ausencia.getPeso(); // Vem da classe Declaracao de Ausencia
        carga_horaria_h = turma.getCarga_horaria(); // Vem da classe Turma
        horas_ministradas = (turma.getCarga_horaria_ministrada()+horas_antecipadas);
        hora_total = (horas_ministradas);//horas cumpridas
        horas_restantes = (carga_horaria_h - hora_total);
        possibilidadeResultado = (carga_horaria_h/4)-(hora_total/4); //Uma disciplina de 60horas tera 4 horas/semanal.

        txt_nome_disciplina.setText(String.valueOf(nome_disciplina));
        txt_carga_horaria.setText(String.valueOf(carga_horaria_h));
        txt_horas_ministradas.setText(String.valueOf(horas_ministradas));
        txt_horas_ausentes.setText(String.valueOf(horas_ausencias));
        txt_horas_antecipadas.setText(String.valueOf(horas_antecipadas));
        txt_horas_restantes.setText(String.valueOf(horas_restantes));

        txt_hora_total.setText(String.valueOf(hora_total));

        txt_restante_total.setText(String.valueOf(hora_total + "/"+carga_horaria_h +"h")); //para o ProgressBar

        ProgressBar simpleProgressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);
        simpleProgressBar.setMax(carga_horaria_h);
        simpleProgressBar.setProgress(hora_total);

        //para mostrar quantas semanas falta para finalizar a disciplina.
        if(horas_ministradas<=52){
            txt_possibilidade.setText(String.valueOf("Faltam "+possibilidadeResultado + " semanas para finalizar a disciplina!"));
        }
        if(horas_ministradas>=53 & horas_ministradas <=55){
            txt_possibilidade.setText(String.valueOf("Falta "+possibilidadeResultado + " semana e meia para finalizar a disciplina!"));
        }
        if(horas_ministradas>=56 & horas_ministradas <=59){
            txt_possibilidade.setText(String.valueOf("Falta "+possibilidadeResultado + " semana para finalizar a disciplina!"));
        }
        if(horas_ministradas>=carga_horaria_h){
            txt_possibilidade.setText(String.valueOf("Disciplina finalizada! "+ carga_horaria_h +"horas concluidas!"));
        }

        setupViews();
    }

    public void setupViews(){
        final Toolbar toolbar = findViewById(R.id.toolbar_carga_horaria);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Carga Horária");

        appBarLayout = findViewById(R.id.app_bar_carga_horaria);

        appBarLayout.bringToFront();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
