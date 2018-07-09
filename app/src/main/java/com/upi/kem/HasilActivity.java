package com.upi.kem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.upi.kem.utils.CommonUtil;
import com.upi.kem.utils.Session;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.upi.kem.data.Constans.LIST_SKOR;

public class HasilActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.kata)
    AppCompatEditText kata;
    @BindView(R.id.waktu)
    AppCompatEditText waktu;
    @BindView(R.id.jawaban_benar)
    AppCompatEditText jawabanBenar;
    @BindView(R.id.skor_ideal)
    AppCompatEditText skorIdeal;
    @BindView(R.id.skor_kem)
    AppCompatEditText skorKem;
    @BindView(R.id.btn_finish)
    Button btnFinish;

    private int jumlah_kata, waktu_kem, ideal, jawaban_benar;
    private int id_soal;

    Session session;
    private long hasilAkhir;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        ButterKnife.bind(this);
        session = new Session(this);

        waktu_kem = getIntent().getIntExtra("waktu", 0);
        jumlah_kata = getIntent().getIntExtra("jumlah_kata", 0);
        ideal = getIntent().getIntExtra("ideal", 0);
        jawaban_benar = getIntent().getIntExtra("benar", 0);
        id_soal = getIntent().getIntExtra("id_soal", 0);

        waktu.setText(waktu_kem + " Detik");
        kata.setText(jumlah_kata + " Kata");
        skorIdeal.setText(ideal + " Soal");
        jawabanBenar.setText(jawaban_benar + " Soal");

        long hasil = (long) ((jumlah_kata / waktu_kem) * 60) * jawaban_benar;
        hasilAkhir = hasil / (long) ideal;

        skorKem.setText(hasilAkhir + " KPM (Kata Per Menit)");
        postSkor();
    }

    @OnClick(R.id.btn_finish)
    public void onViewClicked() {
        finish();
    }

    void postSkor(){
        openDialog();
        AndroidNetworking.post(LIST_SKOR)
                .addBodyParameter("id_siswa", String.valueOf(session.getID()))
                .addBodyParameter("id_soal", String.valueOf(id_soal))
                .addBodyParameter("skor", String.valueOf(hasilAkhir))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        CommonUtil.showToast(HasilActivity.this,"Succes");
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(HasilActivity.this,"Gagal");
                    }
                });
    }

    public void openDialog() {
        progressDialog = ProgressDialog.show(this, "Loading .. ", "Menyiapkan Data");
        progressDialog.setCancelable(false);
    }

    public void closeDialog() {
        progressDialog.dismiss();
    }

}
