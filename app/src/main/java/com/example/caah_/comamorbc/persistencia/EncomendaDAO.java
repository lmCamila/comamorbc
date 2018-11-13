package com.example.caah_.comamorbc.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.caah_.comamorbc.entity.Encomenda;

import java.util.List;

@Dao
public interface EncomendaDAO {
    @Insert
    long insert(Encomenda encomenda);

    @Delete
    void delete(Encomenda encomenda);

    @Update
    void update(Encomenda encomenda);

    @Query("SELECT * FROM encomendas WHERE id = :id")
    Encomenda queryForId( long id);

    @Query("SELECT * FROM encomendas WHERE clienteId = :clienteId")
    List<Encomenda> queryForCliente(long clienteId);

}
