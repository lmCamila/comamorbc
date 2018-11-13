package com.example.caah_.comamorbc.view;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.caah_.comamorbc.adapter.ClienteAdapter;
import com.example.caah_.comamorbc.entity.Cliente;
import com.example.caah_.comamorbc.entity.Produto;
import com.example.caah_.comamorbc.persistencia.ComAmorBCDatabase;
import com.example.caah_.comamorbc.utils.UtilsGUI;
import com.example.caah_.comamorbc.view.CadastrarCliente;

import java.util.List;

public class ClientesView extends AppCompatActivity {
    ListView listViewClientes;
    private static ClienteAdapter clienteAdapter;
    private List<Cliente> lista;
    private int MODO_EDICAO = 1;
    private int        posicaoSelecionada = -1;

    private View viewSelecionada;
    private ActionMode actionMode;


    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
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
                    intentMenu = new Intent(ClientesView.this,CadastrarCliente.class);
                    intentMenu.putExtra(CadastrarCliente.ITEMLISTA, lista.get(posicaoSelecionada).getId());
                    startActivityForResult(intentMenu,MODO_EDICAO);
                    mode.finish();
                    return true ;
                case R.id.menuItemExcluirProduto:
                    excluirCliente();
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
        setContentView(R.layout.activity_clientes);
        setTitle(R.string.clientes);
        listViewClientes = findViewById(R.id.listViewClientes);
        popularLista();
        listViewClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode != null){
                    return false;
                }
                viewSelecionada = view;
                posicaoSelecionada = position;
                view.setBackgroundColor(Color.LTGRAY);
                view.setSelected(true);
                actionMode = startActionMode(actionModeCallback);

                return true;
            }
        });


    }

    public void popularLista(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(ClientesView.this);
                lista = database.clienteDAO().queryAll();
                ClientesView.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clienteAdapter = new ClienteAdapter(ClientesView.this, lista);
                        listViewClientes.setAdapter(clienteAdapter);
                    }
                });
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clientes,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemNovoCliente:
                Intent intent = new Intent(this, CadastrarCliente.class);
                startActivity(intent);
             default:
                 return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        popularLista();
        if(clienteAdapter != null){
            clienteAdapter.notifyDataSetChanged();
        }
    }

    public void excluirCliente(){
        ClientesView.this.runOnUiThread(new Runnable() {
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
                                        ComAmorBCDatabase database = ComAmorBCDatabase.getDatabase(ClientesView.this);
                                        final Cliente cliente = database.clienteDAO().queryForId((lista.get(posicaoSelecionada).getId()));
                                        database.clienteDAO().delete(cliente);
                                        ClientesView.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                lista.remove(posicaoSelecionada);
                                                clienteAdapter.notifyDataSetChanged();
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
                UtilsGUI.confirmaAcao(ClientesView.this,mensagem,listener);
            }
        });
    }

}
