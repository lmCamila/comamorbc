package com.example.caah_.comamorbc.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.caah_.comamorbc.entity.Produto;
import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.entity.Cliente;
import com.example.caah_.comamorbc.entity.Encomenda;
import com.example.caah_.comamorbc.entity.Financeiro;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Encomenda> encomendas = new ArrayList<>();
    private static List<Financeiro> financeiro = new ArrayList<>();
    private static List<Produto> produto = new ArrayList<>();

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void irParaClientes(View view){
        Intent intent = new Intent(this,ClientesView.class);
        startActivity(intent);
    }

    public void irParaEncomendas(View view){
        Intent intent = new Intent(this,EncomendasView.class);
        startActivity(intent);
    }

    public void irParaProdutos(View view){
        Intent intent = new Intent(this,ProdutosView.class);
        startActivity(intent);
    }
    public void irParaFinanceiro(View view){
        Intent intent = new Intent(this,FinanceiroView.class);
        startActivity(intent);
    }

    public void sobreApp(View view){
        Intent intent = new Intent(this,SobreActivity.class);
        startActivity(intent);
    }

    public static void addClientes(Cliente c){
        clientes.add(c);
    }
    public static void addFinanceiro(Financeiro f){
        financeiro.add(f);
    }
    public static void addProduto(Produto p){
        produto.add(p);
    }
    public static void addEncomenda(Encomenda e){
        encomendas.add(e);
    }

    public static void removePessoa(String nome){
        for(int i =0 ; i< clientes.size();i++){
            Cliente c = clientes.get(i);

            if(c.getNome().equals(nome)){
                clientes.remove(c);
                break;
            }
        }
    }
    public static void removerProduto(String nome){
        for(int i =0 ; i< produto.size();i++){
            Produto p = produto.get(i);

            if(p.getNome().equals(nome)){
                produto.remove(p);
                break;
            }
        }
    }

    /*public static void removerEncomenda(String cliente){
        for(int i = 0; i< encomendas.size();i++){
            Encomenda e = encomendas.get(i);

            if(e.getCliente().getNome().equals(cliente)){
                encomendas.remove(e);
                break;
            }
        }
    }

    public static void removerFinanceiro(String nome){
        for (int i =0;i<financeiro.size();i++){
            Financeiro f = financeiro.get(i);

            if(f.getDescrcao().equals(nome)){
                financeiro.remove(f);
                break;
            }
        }
    }
    */
    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static List<Encomenda> getEncomendas() {
        return encomendas;
    }

    public static List<Financeiro> getFinanceiro() {
        return financeiro;
    }

    public static List<Produto> getProduto() {
        return produto;
    }

}
