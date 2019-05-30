package com.example.myapplication.view.activity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtName, edtPassword;
    Button btnSignup;
    UserService userService;
    String username, password, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPasswordR);
        btnSignup = findViewById(R.id.btnSignup);

        toolbar = findViewById(R.id.toolBarR);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, UserNameActivity.class));
            }
        });


        userService = ApiUtils.getUsernameService();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                username = intent.getStringExtra("username");
                name = edtName.getText().toString();
                password = edtPassword.getText().toString();
                if (name == null || name.trim().length() == 0 || password == null || password.trim().length() == 0){
                    Toast.makeText(RegisterActivity.this, "Name and password is required!", Toast.LENGTH_SHORT).show();
                } else {
                    doRegister();
//                    Toast.makeText(RegisterActivity.this, "Pass", Toast.LENGTH_SHORT).show();
                }
            }

            private void doRegister() {
                Call<DataResponse<DataUser>> call = userService.registerRequest(username, name, password);
                call.enqueue(new Callback<DataResponse<DataUser>>() {
                    @Override
                    public void onResponse(Call<DataResponse<DataUser>> call, Response<DataResponse<DataUser>> response) {
                        if (response.isSuccessful()){
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", response.body().getData().getUser().getId());
                            bundle.putString("accessToken", response.body().getData().getAccessToken());
                            bundle.putString("avatar", response.body().getData().getUser().getAvatar());
                            bundle.putString("name", response.body().getData().getUser().getName());
                            bundle.putBoolean("isVip", response.body().getData().getUser().getIsVIP());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataResponse<DataUser>> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
