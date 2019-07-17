package com.example.rodrigosouza.presencebarcode.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigosouza.presencebarcode.R;
import com.example.rodrigosouza.presencebarcode.api.ApiService;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.model.Interesse;
import com.example.rodrigosouza.presencebarcode.model.Turma;
import com.example.rodrigosouza.presencebarcode.utils.Constants;
import com.example.rodrigosouza.presencebarcode.utils.SecurityPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AusenciaAdapter extends RecyclerView.Adapter<AusenciaAdapter.ViewHolder>{
    private ApiService apiService;
    private SecurityPreferences securityPreferences;
    private List<DeclaracaoAusencia> mDataset;
    private Context mContext;
    public AusenciaAdapter(List<DeclaracaoAusencia> dataset, Context context){
        this.mDataset = dataset;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_ausencia, viewGroup, false);

        ViewHolder vh = new ViewHolder(view);
        securityPreferences = new SecurityPreferences(this.mContext);
        apiService = new ApiService(securityPreferences.getSavedString(Constants.TOKEN));
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final DeclaracaoAusencia declaracaoAusencia = mDataset.get(i);

        viewHolder.professor.setText(declaracaoAusencia.getProfessor());
        viewHolder.turma.setText(declaracaoAusencia.getTurma());
        viewHolder.data.setText(declaracaoAusencia.getData_falta());
        viewHolder.hora.setText(declaracaoAusencia.getHorario());

        viewHolder.solicita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Interesse interesse = new Interesse();
                        interesse.setInteressado(securityPreferences.getSavedString(Constants.USUARIO_LOGADO));
                        interesse.setTurma(declaracaoAusencia.getTurma());
                        //Toast.makeText(mContext, "TA dando certo", Toast.LENGTH_SHORT).show();
                        criaInteresse(interesse);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView professor, turma, data, hora;
        LinearLayout llCardausencia, llSelectTurma;
        Button solicita;
        public ViewHolder(View itemView){
            super(itemView);
            llCardausencia  = itemView.findViewById(R.id.ll_card_ausencias);
            professor = itemView.findViewById(R.id.tv_nome_professor);
            turma = itemView.findViewById(R.id.tv_nome_turma);
            data = itemView.findViewById(R.id.tv_data);
            hora = itemView.findViewById(R.id.tv_horario);
            solicita = itemView.findViewById(R.id.bt_solicita);
        }
    }

    public void criaInteresse(Interesse interesse){
        Call<Interesse> interesseCall = apiService.declararintereseEndPoint.addInteresse(interesse);
        interesseCall.enqueue(new Callback<Interesse>() {
            @Override
            public void onResponse(Call<Interesse> call, Response<Interesse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext, "Solicitação realizada com sucesso", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext, "Houve um erro temporário!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Interesse> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
