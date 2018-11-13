package com.example.caah_.comamorbc.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.caah_.comamorbc.entity.Produto;

import java.util.List;

@Dao
public interface ProdutoDAO {
    @Insert
    long insert(Produto produto);
    @Delete
    void delete(Produto produto);
    @Update
    void update(Produto produto);
    @Query("SELECT * FROM produtos WHERE id = :id")
    Produto queryForId(long id);
    @Query("SELECT * FROM produtos ORDER BY nome ASC")
    List<Produto> queryAll();
    @Query("SELECT * FROM produtos ORDER BY preco ASC")
    List<Produto> queryAllPrice();
}
