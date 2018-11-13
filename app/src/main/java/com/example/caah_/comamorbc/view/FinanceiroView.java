package com.example.caah_.comamorbc.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.view.CadastroFinancas;

public class FinanceiroView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);
        setTitle(R.string.finanças);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_financeiro,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void novaFinanca(MenuItem menuItem){
        Intent intent = new Intent(this, CadastroFinancas.class);
        startActivity(intent);
    }
}
