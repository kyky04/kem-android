package com.upi.kem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.upi.kem.utils.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainAdminActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_soal)
    RelativeLayout btnSoal;
    @BindView(R.id.btn_jawaban)
    RelativeLayout btnJawaban;
    @BindView(R.id.btn_data_siswa)
    RelativeLayout btnDataSiswa;
    @BindView(R.id.btn_tentang)
    RelativeLayout btnTentang;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        ButterKnife.bind(this);
        session = new Session(this);
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_soal, R.id.btn_jawaban, R.id.btn_data_siswa, R.id.btn_tentang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_soal:
                startActivity(new Intent(this,AdminKelasActivity.class));
                break;
            case R.id.btn_jawaban:
                break;
            case R.id.btn_data_siswa:
                startActivity(new Intent(this,SiswaActivity.class));
                break;
            case R.id.btn_tentang:
                startActivity(new Intent(this,TextSoalActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                session.logoutUser();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
