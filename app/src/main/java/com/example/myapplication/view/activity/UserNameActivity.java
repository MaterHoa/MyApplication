package com.example.myapplication.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.DataCheck;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserNameActivity extends AppCompatActivity {

    EditText edtPhone;
    Button btnNext;
    UserService usernameService;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_username);

        edtPhone = findViewById(R.id.edtSdt);
        btnNext = findViewById(R.id.btnNext);

        usernameService = ApiUtils.getUsernameService();
        mPreferences = getSharedPreferences(sharePrefFile, MODE_PRIVATE);
        if (mPreferences.contains("accessToken")){
            startActivity(new Intent(UserNameActivity.this, HomeActivity.class));
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtPhone.getText().toString();
                if (username == null || username.trim().length() == 0){
                    Toast.makeText(UserNameActivity.this, "Username is required", Toast.LENGTH_SHORT).show();
                } else {
                    doCheck(username);
                }
            }

            private void doCheck(final String username) {
                Call<DataCheck> call = usernameService.checkUsername(username);
                call.enqueue(new Callback<DataCheck>() {
                    @Override
                    public void onResponse(Call<DataCheck> call, Response<DataCheck> response) {
                        if (response.isSuccessful()){
                            if (response.body().getData().getExists().equals("true")){
                                Intent iPass = new Intent(UserNameActivity.this, PasswordActivity.class);
                                iPass.putExtra("username", username);
                                startActivity(iPass);
                            } else {
                                Intent iRes = new Intent(UserNameActivity.this, RegisterActivity.class);
                                iRes.putExtra("username", username);
                                startActivity(iRes);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<DataCheck> call, Throwable t) {
                        Toast.makeText(UserNameActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
