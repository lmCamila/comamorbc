package com.example.caah_.comamorbc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caah_.comamorbc.R;
import com.example.caah_.comamorbc.entity.Produto;
import com.example.caah_.comamorbc.view.ProdutosView;

import java.util.List;

public class ProdutoAdapter extends BaseAdapter {
    private final List<Produto> produtos;
    private final Context context;

    public ProdutoAdapter(Context context , List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = produtos.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.list_produto,parent,false);
        }
        TextView nomeProduto = view.findViewById(R.id.textViewNomeProdutoList);
        TextView descricaoProduto = view.findViewById(R.id.textViewDescriçãoProdutoList);
        TextView valorProduto = view.findViewById(R.id.textViewValorProdutoList);

        nomeProduto.setText(produto.getNome());
        descricaoProduto.setText(produto.getDescricao());
        valorProduto.setText("R$ "+String.format("%.2f",produto.getPreco()));
        return view;
    }
}
