package com.example.myapplication.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.DataResponse;
import com.example.myapplication.data.DataUser;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtpassword;
    Button btnSignin;
    UserService usernameService;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_password);

        edtpassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);
        toolbar = findViewById(R.id.toolBar);

        mPreferences = getSharedPreferences(sharePrefFile, MODE_PRIVATE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PasswordActivity.this, UserNameActivity.class));
            }
        });

        usernameService =  ApiUtils.getUsernameService();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                String password = edtpassword.getText().toString();
                if (password == null || password.trim().length() == 0){
                    Toast.makeText(PasswordActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                } else {
                    doCheck(username, password);
                }
            }

            private void doCheck(String username, final String password) {
                Call<DataResponse<DataUser>> call = usernameService.checkUser(username, password);

                call.enqueue(new Callback<DataResponse<DataUser>>() {
                    @Override
                    public void onResponse(Call<DataResponse<DataUser>> call, Response<DataResponse<DataUser>> response) {
                        if (response.isSuccessful()){
                            if (response.body().getData() != null){
                                Intent iHome = new Intent(PasswordActivity.this, HomeActivity.class);
                                SharedPreferences.Editor editor = mPreferences.edit();
                                editor.putString("id", response.body().getData().getUser().getId());
                                editor.putString("accessToken", response.body().getData().getAccessToken());
                                editor.putString("avatar", response.body().getData().getUser().getAvatar());
                                editor.putString("name", response.body().getData().getUser().getName());
                                editor.putBoolean("isVip", response.body().getData().getUser().getIsVIP());
                                editor.apply();
                                startActivity(iHome);
                            } else {
                                Toast.makeText(PasswordActivity.this, "Data empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PasswordActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataResponse<DataUser>> call, Throwable t) {
                        Toast.makeText(PasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
