package com.example.contatos;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.contatos.Adapter.ContatoAdapter;

public class ListaContatosFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /*É utilizado a classe CursorLoader para carregar os dados em background e a interface LoaderCallbacks para saber qando os dados
    * estão carregados.*/
    //O array colunas define os campos que utilizaremos na listagem. O ponto interessante é que estamos listando apenas contato
    //com numéro de telefone por meio da condição HAS_PHONE_NUMBER igual a 1 no método onCreateLoader
    private final static String[] COLUNAS = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME
    };

    private CursorAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mAdapter == null) {
            mAdapter = new ContatoAdapter(getActivity(), null);
            setListAdapter(mAdapter);

            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
    }
    /* No método onListItemClick, obtivemos o objeto Cursor que estamos listando, movemos para a linha selecionada e lemos o valor
    * das colunas _ID e LOOKUP_KEY. Em seguida, criamos uma Uri que representa esse contato e passamos para o objeto da intent.
    * */
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        long idContato = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));

        Uri uriContato = ContactsContract.Contacts.getLookupUri(idContato, lookupKey);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriContato);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                COLUNAS,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1",
                null,
                ContactsContract.Contacts.DISPLAY_NAME
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
