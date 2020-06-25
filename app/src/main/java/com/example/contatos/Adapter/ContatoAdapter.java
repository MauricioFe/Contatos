package com.example.contatos.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.contatos.R;
import com.squareup.picasso.Picasso;

public class ContatoAdapter extends CursorAdapter {
    int[] indices;

    public ContatoAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
/*No método newView(), além de carregar o arquivo de layout, armazenamos a posição das colunas que leremos e armazenamos a posição
* em colunas que leremos e armazenamos no array indices. As colunas com os dados básicos do contato estão na classe
* ContactsContracts.Contacts; */
    @SuppressLint("InflateParams")
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        indices = new int[]{
                /*Aqui estamos pegando o _ID que é o identificador único do contato;*/
                cursor.getColumnIndex(ContactsContract.Contacts._ID),
                /*Lookup_key, que é um campo que facilita a obtenção de mais dados sobre o contato*/
                cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY),
                /*Nome do contato*/
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        };

        return LayoutInflater.from(context).inflate(R.layout.item_contato, null);
    }
    /*Nesse método criamos a Uri, que representa o endereço único do contato usando método getLookUri, em seguida atribuímos
    * o resultado ao QuickCOntactBadge por meio do método assignContactUri. Assim ao clicarmos no componente mais informações
    * do contyato serão exibidas.
    * Para preencher aa imagem do contato, usamos a biblioteca do Picasso que eu adicionei ao gradle*/
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtNome = view.findViewById(R.id.txtNome);
        QuickContactBadge qcbBadge = view.findViewById(R.id.qcbFoto);

        Uri contato = ContactsContract.Contacts.getLookupUri(cursor.getLong(indices[0]), cursor.getString(indices[1]));

        txtNome.setText(cursor.getString(indices[2]));
        qcbBadge.assignContactUri(contato);
        Picasso.with(context).load(contato).placeholder(R.mipmap.ic_launcher).into(qcbBadge);
    }
}
