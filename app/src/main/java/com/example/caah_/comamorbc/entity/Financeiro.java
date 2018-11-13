package com.example.caah_.comamorbc.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "financeiro")
public class Financeiro {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String tipo;
    @NonNull
    private String descricao;
    @NonNull
    private double valor;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTipo() {
        return tipo;
    }

    public void setTipo(@NonNull String tipo) {
        this.tipo = tipo;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    public double getValor() {
        return valor;
    }

    public void setValor(@NonNull double valor) {
        this.valor = valor;
    }
}
