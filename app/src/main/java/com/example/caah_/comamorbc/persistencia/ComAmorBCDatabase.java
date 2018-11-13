package com.example.caah_.comamorbc.persistencia;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.caah_.comamorbc.entity.Cliente;
import com.example.caah_.comamorbc.entity.Encomenda;
import com.example.caah_.comamorbc.entity.Financeiro;
import com.example.caah_.comamorbc.entity.Produto;


@Database(entities = {Cliente.class, Produto.class, Encomenda.class, Financeiro.class},version = 1)
@TypeConverters({Converters.class})

public abstract class ComAmorBCDatabase  extends RoomDatabase {

    public abstract ClienteDAO clienteDAO();
    public abstract ProdutoDAO produtoDAO();
    public abstract EncomendaDAO encomendaDAO();
    public abstract FinanceiroDAO financeiroDAO();
    public static ComAmorBCDatabase instance;

    public static ComAmorBCDatabase getDatabase(final Context context){
        if(instance == null){
           synchronized (ComAmorBCDatabase.class){
               if(instance == null){
                   Builder builder = Room.databaseBuilder(context,ComAmorBCDatabase.class,"comamorbc2.db");
                   instance = (ComAmorBCDatabase) builder.build();
               }
           }
        }
        return instance;
    }
}
