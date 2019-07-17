package com.example.rodrigosouza.presencebarcode.model;

import com.google.gson.annotations.SerializedName;

public class Turma {

    @SerializedName("id") private long id;
    @SerializedName("disciplina") private String disciplina;
    @SerializedName("carga_horaria") private int carga_horaria;
    @SerializedName("carga_horaria_ministrada") private int carga_horaria_ministrada;
    @SerializedName("ministrante")private String ministrante;
    @SerializedName("curso")private String curso;
    @SerializedName("especificacao_disciplina") private String especificacao_disciplina;


//    public Turma(String disciplina){
//        this.disciplina = disciplina;
//    }

    public Turma(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getEspecificacao_disciplina(){return this.especificacao_disciplina; }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }
    public int getCarga_horaria_ministrada() {
        return carga_horaria_ministrada;
    }


}
