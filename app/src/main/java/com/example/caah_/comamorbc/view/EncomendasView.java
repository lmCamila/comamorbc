package com.example.caah_.comamorbc.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.view.CadastrarEncomenda;

public class EncomendasView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encomendas);
        setTitle(R.string.encomendas);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_encomendas,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void novaEncomenda(MenuItem menuItem){
        Intent intent = new Intent(this, CadastrarEncomenda.class);
        startActivity(intent);
    }
}
