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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_latihan)
    RelativeLayout btnLatihan;
    @BindView(R.id.btn_petunjuk)
    RelativeLayout btnPetunjuk;
    @BindView(R.id.btn_info)
    RelativeLayout btnInfo;
    @BindView(R.id.btn_tentang)
    RelativeLayout btnTentang;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        session = new Session(this);
    }

    @OnClick({R.id.btn_latihan, R.id.btn_petunjuk, R.id.btn_info, R.id.btn_tentang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_latihan:
                startActivity(new Intent(MainActivity.this, TextActivity.class));
                break;
            case R.id.btn_petunjuk:
                startActivity(new Intent(MainActivity.this, PetunjukActivity.class));
                break;
            case R.id.btn_info:
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
                break;
            case R.id.btn_tentang:
                startActivity(new Intent(MainActivity.this, TentangkActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                session.logoutUser();
                finish();
                break;
            case R.id.menu_profil:
                startActivity(new Intent(MainActivity.this, ProfilActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
