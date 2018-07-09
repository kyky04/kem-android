package com.upi.kem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.models.LoginResponse;
import com.upi.kem.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.upi.kem.data.Constans.LOGIN;
import static com.upi.kem.data.Constans.LOGIN_ADMIN;

public class LoginAdminActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if(!email.getText().toString().equals("") || !password.getText().toString().equals("")){
                    loging(email.getText().toString(), password.getText().toString());
                }
                break;
        }
    }


    void loging(String email, String password) {
        openDialog();
        AndroidNetworking.post(LOGIN_ADMIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof LoginResponse) {
                            if (((LoginResponse) response).getStatus().equals("Login Berhasil")) {
                                CommonUtil.showToast(LoginAdminActivity.this, "Login Berhasil");
                                goToMain();
                            } else {
                                CommonUtil.showToast(LoginAdminActivity.this, ((LoginResponse) response).getStatus());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(LoginAdminActivity.this, "Login Gagal");
                    }
                });
    }

    public void openDialog() {
        progressDialog = ProgressDialog.show(this, "Loading .. ", "");
        progressDialog.setCancelable(false);
    }

    public void closeDialog() {
        progressDialog.dismiss();
    }

    void goToMain(){
        // user is not logged in redirect him to Login Aktifitas
        Intent i = new Intent(this, MainAdminActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Aktifitas
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Aktifitas
        startActivity(i);
        finish();
    }
}
