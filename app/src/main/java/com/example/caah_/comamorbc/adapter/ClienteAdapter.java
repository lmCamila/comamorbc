package com.example.caah_.comamorbc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.entity.Cliente;

import java.util.List;

public class ClienteAdapter extends BaseAdapter {

    private final List<Cliente> clientes;
    private final Context context;

    public ClienteAdapter(Context context,List<Cliente> clientes){
        this.clientes = clientes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cliente cliente = clientes.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.list_cliente,parent,false);
        }
        TextView nomeCliente = view.findViewById(R.id.textViewListNomeCliente);
        TextView enderecoCliente = view.findViewById(R.id.textViewListEnderecoCliente);
        TextView telefoneCliente = view.findViewById(R.id.textViewListTelefoneCliente);

        nomeCliente.setText(cliente.getNome());
        enderecoCliente.setText(cliente.getRua()+" - "+cliente.getNumero()+" - "+cliente.getBairro());
        telefoneCliente.setText(cliente.getTelefone());
        return view;
    }
}
