package com.example.rodrigosouza.presencebarcode.model;

import com.google.gson.annotations.SerializedName;

public class DeclaracaoAusencia {
    @SerializedName("id") private long id;
    @SerializedName("justificativad")private String justificativa;
    @SerializedName("professor")private String professor;
    @SerializedName("turma")private String turma;
    @SerializedName("horario")private String horario;
    @SerializedName("peso") private int peso = 6;
    @SerializedName("data_falta")private String data_falta;

    public DeclaracaoAusencia(){
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getData_falta() {
        return data_falta;
    }

    public void setData_falta(String data_falta) {
        this.data_falta = data_falta;
    }


    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

}