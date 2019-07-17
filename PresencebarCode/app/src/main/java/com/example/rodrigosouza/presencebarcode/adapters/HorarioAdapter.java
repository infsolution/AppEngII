package com.example.rodrigosouza.presencebarcode.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigosouza.presencebarcode.R;
import com.example.rodrigosouza.presencebarcode.api.ApiService;
import com.example.rodrigosouza.presencebarcode.app.HomeActivity;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.model.Horario;
import com.example.rodrigosouza.presencebarcode.utils.Constants;
import com.example.rodrigosouza.presencebarcode.utils.SecurityPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ViewHolder>{
    private List<Horario> mDataset;
    private Context mContext;
    private ApiService apiService;
    private SecurityPreferences securityPreferences;
    private String data;
    public HorarioAdapter(String data, List<Horario> Dataset, Context context){
        this.data = data;
        mDataset = Dataset;
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horario, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final HorarioAdapter.ViewHolder viewHolder, int i) {

        final Horario horario = mDataset.get(i);
        viewHolder.dia.setText(horario.getDia_semana());
        viewHolder.horaInicio.setText(horario.getHora_inicio());
        viewHolder.horaFin.setText((horario.getHora_fim()));
        viewHolder.turma.setText(horario.getTurma());
            final DeclaracaoAusencia ausencia = new DeclaracaoAusencia();
            ausencia.setData_falta(this.data);
            ausencia.setHorario(horario.getHora_inicio());
            ausencia.setTurma(horario.getTurma());
            ausencia.setProfessor(securityPreferences.getSavedString(Constants.USUARIO_LOGADO));
            ausencia.setJustificativa("Pessoal");
        viewHolder.disponibilizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        declararAusencia(ausencia);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dia, turma, horaInicio, horaFin;
        private Button disponibilizar;

        public ViewHolder(View itemView){
            super(itemView);

            dia = itemView.findViewById(R.id.tv_dia_semana);
            turma = itemView.findViewById(R.id.tv_turma);
            horaInicio = itemView.findViewById(R.id.tv_hora_inicio);
            horaFin = itemView.findViewById(R.id.tv_hora_fim);
            disponibilizar = itemView.findViewById(R.id.bt_solicita);
        }
    }

public void declararAusencia(DeclaracaoAusencia ausencia){
    Call <DeclaracaoAusencia> declaracaoAusenciaCall = apiService.declaracaoAusencia.declararAusencia(ausencia);
    declaracaoAusenciaCall.enqueue(new Callback<DeclaracaoAusencia>() {
        @Override
        public void onResponse(Call<DeclaracaoAusencia> call, Response<DeclaracaoAusencia> response) {
            if(response.isSuccessful()){
                Toast.makeText(mContext, "Horario disponibilizado com sucesso", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(Call<DeclaracaoAusencia> call, Throwable t) {

        }
    });
}
}
