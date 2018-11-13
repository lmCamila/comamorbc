package com.example.caah_.comamorbc.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "encomendas",
    foreignKeys = {@ForeignKey(entity = Cliente.class,
                    parentColumns = "id",
                    childColumns = "clienteId"),
            @ForeignKey(entity = Produto.class,
                    parentColumns = "id",
                    childColumns = "produtoId")})
public class Encomenda {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(index = true)
    private int clienteId;
    @ColumnInfo(index = true)
    private int produtoId;
    @NonNull
    private Date dataEncomenda;
    @NonNull
    private Date dataEntrega;
    private int qtdProduto;
    private boolean concluido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    @NonNull
    public Date getDataEncomenda() {
        return dataEncomenda;
    }

    public void setDataEncomenda(@NonNull Date dataEncomenda) {
        this.dataEncomenda = dataEncomenda;
    }

    @NonNull
    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(@NonNull Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}
