package com.example.contatos.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.contatos.R;
import com.example.contatos.Util.ContatoUtil;

import java.io.FileNotFoundException;

public class ContatoFragment extends DialogFragment implements DialogInterface.OnClickListener, View.OnClickListener {

    Bitmap mFoto;
    ImageView mImgFoto;
    EditText mEdtNome, mEdtEndereco, mEdtPhone;

    /*É carregado os arquivos de layout e inicializamos com os componentes da interface gráfica. Ao clicarmos no image view
    * chamamos o onClick e nele abrimos a activity para selecionar uma foro do dispositivo.*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_contato, null);
        mImgFoto = view.findViewById(R.id.imgFoto);
        mImgFoto.setOnClickListener(this);

        mEdtNome = view.findViewById(R.id.edtNome);
        mEdtEndereco = view.findViewById(R.id.edtEndereco);
        mEdtPhone = view.findViewById(R.id.edtTelefone);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.adici_contato)
                .setView(view)
                .setPositiveButton(R.string.OK, (DialogInterface.OnClickListener) this)
                .setNegativeButton(R.string.cancelar, null)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ContatoUtil.inserirContato(getActivity(), mEdtNome.getText().toString(), mEdtEndereco.getText().toString(),
                mEdtPhone.getText().toString(), mFoto);
    }
    /*Ao clicarmos no ok chamamos nossa classe util que faz a inserção*/
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    /*Após selecionar essa imagem o método onActivityResult é invocado. Redimencionamos a a imagem para 1/4 do seu tamanho*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        try {
            mFoto = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(data.getData()), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImgFoto.setImageBitmap(mFoto);
    }
}