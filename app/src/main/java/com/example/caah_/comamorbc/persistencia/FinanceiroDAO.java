package com.example.caah_.comamorbc.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.caah_.comamorbc.entity.Financeiro;

import java.util.List;

@Dao
public interface FinanceiroDAO {
    @Insert
    long insert(Financeiro financeiro);
    @Delete
    void delete(Financeiro financeiro);
    @Update
    void update(Financeiro financeiro);

    @Query("SELECT * FROM financeiro WHERE id = :id")
    Financeiro queryForId(long id);

    @Query("SELECT * FROM financeiro WHERE tipo = :tipo")
    List<Financeiro> queryForTipo(String tipo);

    @Query("SELECT * FROM  financeiro ")
    List<Financeiro> queryAll();
}
