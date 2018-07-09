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
import com.upi.kem.utils.Session;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.upi.kem.data.Constans.LIST_SISWA;

public class ProfilActivity extends AppCompatActivity {

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

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);
        ButterKnife.bind(this);
        session = new Session(this);
        name.setText(session.getName());
        username.setText(session.getUserName());
        email.setText(session.getEmail());
        password.setText(session.getPassword());
        sekolah.setText(session.getSekolah());
    }


    @OnClick(R.id.btn_daftar_siswa)
    public void onViewClicked() {
    }
}
