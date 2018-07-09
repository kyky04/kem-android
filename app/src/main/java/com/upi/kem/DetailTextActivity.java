package com.upi.kem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailTextActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.waktu)
    TextView waktu;
    @BindView(R.id.judul)
    TextView judul;
    @BindView(R.id.isi)
    TextView isi;

    String judulText, isiText;
    @BindView(R.id.btn_mulai)
    Button btnMulai;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    int detik,id_soal,jumlahKata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_text);
        ButterKnife.bind(this);

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

        judulText = getIntent().getStringExtra("judul");
        id_soal = getIntent().getIntExtra("id_soal",0);
        isiText = getIntent().getStringExtra("isi");
        jumlahKata = getIntent().getIntExtra("jumlah_kata",0);
        judul.setText(judulText);
        isi.setText(isiText);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            detik = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            waktu.setText("" + mins + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    };


    @OnClick(R.id.btn_mulai)
    public void onViewClicked() {
        Log.d("DETIK", String.valueOf(detik));
        Intent intent = new Intent(this, SoalActivity.class);
        intent.putExtra("waktu",detik);
        intent.putExtra("id_soal",id_soal);
        intent.putExtra("jumlah_kata",jumlahKata);
        startActivity(intent);
        finish();
    }
}
