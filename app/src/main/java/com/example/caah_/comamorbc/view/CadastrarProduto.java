package com.example.caah_.comamorbc.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.caah_.comamorbc.entity.Produto;
import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.persistencia.ComAmorBCDatabase;
import com.example.caah_.comamorbc.utils.UtilsGUI;

public class CadastrarProduto extends AppCompatActivity {
    public static final String MODO    = "MODO";
    public static final String ITEMLISTA = "ITEMLISTA";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;

    private int    modo;
    private int idSelecionado;

    EditText editTextNomeProduto;
    EditText editTextDescricao;
    EditText editTextPreco;

    Produto produto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);
        editTextNomeProduto = findViewById(R.id.editTextCadastrarNomeCliente);
        editTextDescricao = findViewById(R.id.editTextDescricaoProduto);
        editTextPreco = findViewById(R.id.editTextPrecoProduto);

        if(!verificarModoIntent(getIntent())){
            setTitle(R.string.cadastrar_produto);
        }

    }
    public boolean verificarModoIntent(Intent intent){
        final Bundle bundle = intent.getExtras();
        if(bundle != null){
            setTitle(R.string.alterar_produto);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    idSelecionado = bundle.getInt(ITEMLISTA);

                    ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(CadastrarProduto.this);
                    produto = database.produtoDAO().queryForId(idSelecionado);
                    CadastrarProduto.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            editTextNomeProduto.setText(produto.getNome());
                            editTextDescricao.setText(produto.getDescricao());
                            editTextPreco.setText(String.valueOf(produto.getPreco()));
                        }
                    });
                }
            });
            return true;
        }
        return false;
    }


    public void cadastrar(){
        final Bundle bundle = getIntent().getExtras();
        final String nome  = UtilsGUI.validaCampoTexto(this,editTextNomeProduto,R.string.erro_salvar_produto);
        final String descricao = UtilsGUI.validaCampoTexto(this,editTextDescricao,R.string.erro_salvar_produto);
        final Double preco = Double.parseDouble(UtilsGUI.validaCampoTexto(this,editTextPreco,R.string.erro_salvar_produto));

        if(nome == null || nome.trim().isEmpty()){
            return;
        }
        if(preco == null ){
            return;
        }
        if( descricao == null ||descricao.trim().isEmpty()){
            return;
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(CadastrarProduto.this);
                if(bundle == null){
                    Produto p = new Produto();
                    p.setNome(nome);
                    p.setPreco(preco);
                    p.setDescricao(descricao);
                    database.produtoDAO().insert(p);
                }else {
                    if (!nome.equals(produto.getNome())) {
                        produto.setNome(nome);
                    }
                    if (!descricao.equals(produto.getDescricao())) {
                        produto.setDescricao(descricao);
                    }
                    if (!preco.equals(produto.getPreco())) {
                        produto.setPreco(preco);
                    }
                    database.produtoDAO().update(produto);
                }
                finish();
            }
        });
    }

    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemSalvar:
                cadastrar();
                return true;
            case R.id.menuItemCancelar:
                cancelar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
