package com.example.caah_.comamorbc.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.entity.Cliente;
import com.example.caah_.comamorbc.persistencia.ComAmorBCDatabase;
import com.example.caah_.comamorbc.utils.UtilsGUI;

public class CadastrarCliente extends AppCompatActivity {

    public static final String MODO    = "MODO";
    public static final String ITEMLISTA = "ITEMLISTA";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;

    private int    modo;
    private int idSelecionado;
    private Cliente cliente;

    EditText editTextCadastrarNomeCliente;
    EditText editTextRua;
    EditText editTextBairro;
    EditText editTextNumero;
    EditText editTextTelefone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cliente);
        setTitle(R.string.cadastrar_cliente);

        editTextCadastrarNomeCliente = findViewById(R.id.editTextCadastrarNomeCliente);
        editTextRua= findViewById(R.id.editTextRua);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextTelefone = findViewById(R.id.editTextTelefone);

        if(!verificarModoIntent(getIntent())){
            setTitle(R.string.cadastrar_cliente);
        }
    }

    public boolean verificarModoIntent(Intent intent){
        final Bundle bundle = intent.getExtras();
        if(bundle != null){
            setTitle(R.string.alterar_cliente);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    idSelecionado = bundle.getInt(ITEMLISTA);

                    ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(CadastrarCliente.this);
                    cliente = database.clienteDAO().queryForId(idSelecionado);

                    CadastrarCliente.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextCadastrarNomeCliente.setText(cliente.getNome());
                            editTextRua.setText(cliente.getRua());
                            editTextBairro.setText(cliente.getBairro());
                            editTextNumero.setText(String.valueOf(cliente.getNumero()));
                            editTextTelefone.setText(cliente.getTelefone());
                        }
                    });
                }
            });
            return true;
        }
        return  false;
    }

    public void salvar(){
        final Bundle bundle = getIntent().getExtras();
        final String nome = UtilsGUI.validaCampoTexto(this,editTextCadastrarNomeCliente,R.string.erro_salvar_produto);
        final String rua = UtilsGUI.validaCampoTexto(this,editTextRua,R.string.erro_salvar_produto);
        final String bairro = UtilsGUI.validaCampoTexto(this,editTextBairro,R.string.erro_salvar_produto);
        final int numero = Integer.parseInt(UtilsGUI.validaCampoTexto(this,editTextNumero,R.string.erro_salvar_produto));
        final String telefone = UtilsGUI.validaCampoTexto(this, editTextTelefone , R.string.erro_salvar_produto);
        if(nome == null || nome.isEmpty()){
            return;
        }
        if(rua == null || rua.isEmpty()){
            return;
        }
        if(bairro == null || bairro.isEmpty()){
            return;
        }
        if(numero == 0){
            return;
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(CadastrarCliente.this);
                if(bundle == null){
                    Cliente c = new Cliente();
                    c.setNome(nome);
                    c.setRua(rua);
                    c.setBairro(bairro);
                    c.setNumero(numero);
                    c.setTelefone(telefone);
                    database.clienteDAO().insert(c);
                }else{
                    if(!nome.equals(cliente.getNome())){
                        cliente.setNome(nome);
                    }
                    if(!rua.equals(cliente.getRua())){
                        cliente.setRua(rua);
                    }
                    if(!bairro.equals(cliente.getBairro())){
                        cliente.setBairro(bairro);
                    }
                    if(numero != cliente.getNumero()){
                        cliente.setNumero(numero);
                    }
                    if(!telefone.equals(cliente.getTelefone())){
                        cliente.setTelefone(telefone);
                    }
                    database.clienteDAO().update(cliente);
                }
                finish();
            }
        });
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
                salvar();
                return true;
            case R.id.menuItemCancelar:
                cancelar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
