package com.upi.kem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.models.LoginResponse;
import com.upi.kem.utils.CommonUtil;
import com.upi.kem.utils.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.upi.kem.data.Constans.LOGIN;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btn_daftar_siswa)
    Button btnDaftarSiswa;
    @BindView(R.id.btn_admin)
    Button btnAdmin;
    private ProgressDialog progressDialog;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        session = new Session(this);

        if (session.isLoggedIn()) {
            goToMain();
        }
    }

    @OnClick({R.id.btnLogin, R.id.btn_daftar_siswa, R.id.btn_admin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if(!email.getText().toString().equals("") || !password.getText().toString().equals("")){
                    loging(email.getText().toString(), password.getText().toString());
                }
                break;
            case R.id.btn_daftar_siswa:
                startActivity(new Intent(LoginActivity.this,DaftarActivity.class));
                break;
            case R.id.btn_admin:
                startActivity(new Intent(LoginActivity.this,LoginAdminActivity.class));
                break;
        }
    }


    void loging(String email, String password) {
        openDialog();
        AndroidNetworking.post(LOGIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof LoginResponse) {
                            if (((LoginResponse) response).getStatus().equals("Login Berhasil")) {
                                CommonUtil.showToast(LoginActivity.this, "Login Berhasil");
                                session.setId(((LoginResponse) response).getSiswa().getId());
                                session.setFullName(((LoginResponse) response).getSiswa().getUsername());
                                session.setName(((LoginResponse) response).getSiswa().getNama());
                                session.setEmail(((LoginResponse) response).getSiswa().getEmail());
                                session.setKeyPassword(((LoginResponse) response).getSiswa().getPassword());
                                session.setSekolah(((LoginResponse) response).getSiswa().getSekolah());
                                session.setIsLogin(true);
                                goToMain();

                            } else {
                                CommonUtil.showToast(LoginActivity.this, ((LoginResponse) response).getStatus());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(LoginActivity.this, "Login Gagal");
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
        Intent i = new Intent(this, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Aktifitas
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Aktifitas
        startActivity(i);
        finish();
    }


}
