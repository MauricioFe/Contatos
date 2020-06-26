package com.example.contatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.example.contatos.Fragments.ListaContatosFragment;

public class ContatosActivity extends AppCompatActivity {

    private static final String EXTRA_PERMISSAO = "pediu";
    private static final int REQUEST_ESCREVER_CONTATO = 1;
    private static final int REQUEST_LER_CONTATO = 2;
    private boolean mPediuPermissao;/*Serve para controlar a exibição da mensagem requisitando a permissão de escrita dos contatos
    no aparelho*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
        if (savedInstanceState != null){
            mPediuPermissao= savedInstanceState.getBoolean(EXTRA_PERMISSAO);
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(EXTRA_PERMISSAO, mPediuPermissao);
    }
    /*No onResume é verificado se a permissão foi concedida e se ainda não foi fazemos sua requisição.*/
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if (!mPediuPermissao){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_ESCREVER_CONTATO);
                mPediuPermissao = true;
            }
        }else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_LER_CONTATO);
        }
        else
            init();
    }
    /*No método init adicionamos o fragment ListaCont*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ESCREVER_CONTATO){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Você precisa aceitar as permissões", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        mPediuPermissao = false;
    }

    private void init() {
        if (getSupportFragmentManager().findFragmentByTag("contatos") == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListaContatosFragment(), "contatos")
                    .commit();
        }
    }
}