package com.valoes.petvaloes.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.valoes.petvaloes.R;
import com.valoes.petvaloes.helper.ConfiguracaoFirebase;

public class AutenticacaoActivity extends AppCompatActivity {

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();

        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if(!email.isEmpty()) {
                    if(!senha.isEmpty()) {

                        //verifica o estado do switch
                        if(tipoAcesso.isChecked()) { //realiza cadastro do usuário
                            autenticacao.createUserWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(AutenticacaoActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), ));
                                    } else {
                                        String erroExcecao = "";

                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthWeakPasswordException e) {
                                            erroExcecao = "Digite uma senha mais forte!";
                                        } catch (FirebaseAuthInvalidCredentialsException e) {
                                            erroExcecao = "Por favor, digite um e-mail válido!";
                                        } catch (FirebaseAuthUserCollisionException e) {
                                            erroExcecao = "Esta conta jjá foi cadastrada!";
                                        } catch (Exception e) {
                                            erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(AutenticacaoActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else { // realiza login do usuário

                        }

                    } else {
                        Toast.makeText(AutenticacaoActivity.this, "Preencha a Senhal!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AutenticacaoActivity.this, "Preencha o E-mail!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void inicializarComponentes() {
        campoEmail = findViewById(R.id.edit_autenticacao_email);
        campoSenha = findViewById(R.id.edit_autenticacao_senha);
        botaoAcessar = findViewById(R.id.botao_autenticacao_acesso);
        tipoAcesso = findViewById(R.id.switch_autenticacao);
    }

}