package com.example.caah_.comamorbc.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.adapter.ProdutoAdapter;
import com.example.caah_.comamorbc.entity.Produto;
import com.example.caah_.comamorbc.persistencia.ComAmorBCDatabase;
import com.example.caah_.comamorbc.utils.UtilsGUI;

import java.util.Collections;
import java.util.List;

public class ProdutosView extends AppCompatActivity {
    ListView listViewProdutos;
    private static ProdutoAdapter produtoAdapter;
    private List<Produto> lista ;

    public static final String ARQUIVO = "com.exemple.caah_.comamorbc.PREFERENCIAS_ORDENACAO";
    public static final String ORDENACAO     = "ORDENACAO";
    private int opcao= NOME;
    private static final int NOME     = 1;
    private static final int PRECO     = 2;
    private int MODO_EDICAO = 1;
    private int        posicaoSelecionada = -1;

    private View viewSelecionada;
    private ActionMode actionMode;

    private ActionMode.Callback actionModeCallBack = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_item_selecionado,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Intent intentMenu;
            switch (item.getItemId()){
                case R.id.menuItemAlterarProduto:
                    intentMenu = new Intent(ProdutosView.this,CadastrarProduto.class);
                    intentMenu.putExtra(CadastrarProduto.ITEMLISTA, lista.get(posicaoSelecionada).getId());
                    startActivityForResult(intentMenu,MODO_EDICAO);
                    mode.finish();
                    return true ;
                case R.id.menuItemExcluirProduto:
                    excluirProduto();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode = null;
            viewSelecionada = null;
            posicaoSelecionada = -1;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        setTitle(R.string.produtos);
        listViewProdutos = findViewById(R.id.listViewProdutos);
        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode != null){
                    return false;
                }
                viewSelecionada = view;
                posicaoSelecionada = position;
                view.setBackgroundColor(Color.LTGRAY);
                view.setSelected(true);
                actionMode = startActionMode(actionModeCallBack);

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_produtos,menu);
        return true;
    }

    public void novoProduto(MenuItem menuItem){
        Intent intent = new Intent(this,CadastrarProduto.class);
        startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        switch (opcao) {
            case NOME:
                item = menu.findItem(R.id.menuItemOrdenarPorNome);
                item.setChecked(true);
                return true;
            case PRECO:
                item = menu.findItem(R.id.menuItemOrdenarPorPreco);
                item.setChecked(true);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.menuItemOrdenarPorNome:
                salvarPreferenciaOpcao(NOME);
                return true;
            case R.id.menuItemOrdenarPorPreco:
                salvarPreferenciaOpcao(PRECO);
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lerPreferenciaDeOrdenacao();
        if(produtoAdapter != null) {
            produtoAdapter.notifyDataSetChanged();
        }

    }

    public void lerPreferenciaDeOrdenacao(){
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        opcao = shared.getInt(ORDENACAO, opcao);
        mudaOrdenacaoLista();
        if(produtoAdapter != null) {
            produtoAdapter.notifyDataSetChanged();
        }
    }

    public void mudaOrdenacaoLista(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(ProdutosView.this);
                lista = database.produtoDAO().queryAll();
                switch (opcao){
                    case NOME:
                        Produto.setTipoComparacao(NOME);
                        Collections.sort(lista,Produto.comparador);

                        break;
                    case PRECO:
                        Produto.setTipoComparacao(PRECO);
                        Collections.sort(lista,Produto.comparador);

                        break;
                }
                ProdutosView.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        produtoAdapter = new ProdutoAdapter(ProdutosView.this,lista);
                        listViewProdutos.setAdapter(produtoAdapter);
                    }
                });
            }
        });
    }

    private void salvarPreferenciaOpcao(int novoValor){

        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(ORDENACAO, novoValor);

        editor.apply();

        opcao = novoValor;

        mudaOrdenacaoLista();
    }


   public void excluirProduto(){
       ProdutosView.this.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               String mensagem = getString(R.string.deseja_realmente_apagar);
               DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                           case DialogInterface.BUTTON_POSITIVE:
                               AsyncTask.execute(new Runnable() {
                                   @Override
                                   public void run() {
                                       ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(ProdutosView.this);
                                       final Produto p = database.produtoDAO().queryForId(lista.get(posicaoSelecionada).getId());
                                       database.produtoDAO().delete(p);
                                       ProdutosView.this.runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               lista.remove(posicaoSelecionada);
                                               produtoAdapter.notifyDataSetChanged();
                                           }
                                       });
                                   }
                               });
                               break;
                           case DialogInterface.BUTTON_NEGATIVE:
                               break;
                       }
                   }
               };
               UtilsGUI.confirmaAcao(ProdutosView.this, mensagem, listener);
           }
       });

   }




}
