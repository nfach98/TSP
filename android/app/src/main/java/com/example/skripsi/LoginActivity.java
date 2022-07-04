package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Model.LoginModel.ResponUserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button btnLogin;
    String stts, Username, Password;
   // Integer Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                login();
                break;
        }
    }


    private void login() {
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponUserModel> loginUser = ardData.ardLogin(Username, Password, stts);

        loginUser.enqueue(new Callback<ResponUserModel>() {
            @Override
            public void onResponse(Call<ResponUserModel> call, Response<ResponUserModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(LoginActivity.this, "Kode : "+kode+" | Pesan : "
                        +pesan, Toast.LENGTH_SHORT).show();
                finish();
                /*if(response.code()==200){
                    if(response.body().getKode() == 1){
                        if(response.body().getPesan().equals("Login Berhasil")){
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Gagal!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }*/

                /*Toast.makeText(LoginActivity.this, "Kode : "+response.body().getKode()+" | Pesan : "
                        +response.body().getPesan(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);*/
            }

            @Override
            public void onFailure(Call<ResponUserModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Menghubungi Server! | "
                        +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}