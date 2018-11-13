package com.example.caah_.comamorbc.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.caah_.comamorbc.view.ProdutosView;

import java.util.Comparator;

@Entity(tableName = "produtos" ,indices = @Index(value = {"nome"}))
public class Produto {

    public static Comparator<Produto> comparador = new Comparator<Produto>() {
        @Override
        public int compare(Produto p1, Produto p2) {

            if (getTipoComparacao() == POR_NOME) {
                if (p1.getNome() != null && p2.getNome() != null) {
                    int ordemNome = p1.getNome().compareToIgnoreCase(p2.getNome());
                    return ordemNome;
                }
            } else if (getTipoComparacao() == POR_PRECO) {
                if (p1.getPreco() != 0 && p2.getPreco() != 0) {
                    String preco1 = String.valueOf(p1.getPreco());
                    String preco2 = String.valueOf(p2.getPreco());
                    int ordemPreco = preco1.compareToIgnoreCase(preco2);
                    return ordemPreco;
                }
            }else{
               int ordemNome = p1.getNome().compareToIgnoreCase(p2.getNome());
               return ordemNome;
            }
            return 0;
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String nome;
    private String descricao;
    @NonNull
    private double preco;

    @Ignore
    public static final int POR_NOME = 1;
    @Ignore
    public static final int POR_PRECO = 2;
    @Ignore
    private static int tipoComparacao;

    public static int getTipoComparacao() {
        return tipoComparacao;
    }

    public static void setTipoComparacao(int tipoComparacao) {
        Produto.tipoComparacao = tipoComparacao;
    }


    @Override
    public String toString() {
        return this.getNome() + " " + this.getDescricao();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    public double getPreco() {
        return preco;
    }

    public void setPreco(@NonNull double preco) {
        this.preco = preco;
    }
}
