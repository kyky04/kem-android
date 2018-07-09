package com.upi.kem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.upi.kem.utils.CommonUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.upi.kem.data.Constans.KELAS;
import static com.upi.kem.data.Constans.LIST_SISWA;

public class DaftarActivity extends AppCompatActivity {

    @BindView(R.id.kelas)
    EditText kelas;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sekolah)
    EditText sekolah;
    @BindView(R.id.btn_daftar_siswa)
    Button btnDaftarSiswa;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);

    }


    void daftar(String nama, String email, String password, String username, String sekolah) {
        openDialog();
        AndroidNetworking.post(LIST_SISWA)
                .addBodyParameter("id_kelas", KELAS)
                .addBodyParameter("nama", nama)
                .addBodyParameter("username", username)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .addBodyParameter("sekolah", sekolah)
                .addBodyParameter("jenis_kelamin", String.valueOf(1))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        CommonUtil.showToast(DaftarActivity.this, "Berhasil Daftar");
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(DaftarActivity.this, "Gagal Daftar");
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

    @OnClick(R.id.btn_daftar_siswa)
    public void onViewClicked() {
        daftar(name.getText().toString(), email.getText().toString(), password.getText().toString(), username.getText().toString(), sekolah.getText().toString());
    }
}
