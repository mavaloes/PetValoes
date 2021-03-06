package com.valoes.petvaloes.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.valoes.petvaloes.R;

public class InicializacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicializacao);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirAutenticacao();
            }
        }, 3000);
    }

    private void abrirAutenticacao() {
        Intent i = new Intent(InicializacaoActivity.this, AutenticacaoActivity.class);
        startActivity(i);
        finish();
    }

}