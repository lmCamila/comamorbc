package com.example.caah_.comamorbc.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.caah_.comamorbc.entity.Cliente;

import java.util.List;

@Dao
public interface ClienteDAO {

    @Insert
    long insert(Cliente cliente);

    @Delete
    void delete(Cliente cliente);

    @Update
    void update(Cliente cliente);

    @Query("SELECT * FROM clientes WHERE id = :id")
    Cliente queryForId(long id);

    @Query("SELECT * FROM clientes")
    List<Cliente> queryAll();
}
