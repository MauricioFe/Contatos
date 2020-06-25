package com.example.contatos.Adapter;

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

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        indices = new int[]{
                cursor.getColumnIndex(ContactsContract.Contacts._ID),
                cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY),
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        };

        return LayoutInflater.from(context).inflate(R.layout.item_contato, null);
    }

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
