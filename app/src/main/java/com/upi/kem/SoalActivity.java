package com.upi.kem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.adapter.AdapterText;
import com.upi.kem.models.DataItemSoal;
import com.upi.kem.models.SoalResponse;
import com.upi.kem.models.TextResponse;
import com.upi.kem.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.upi.kem.data.Constans.LIST_PERTANYAAN;
import static com.upi.kem.data.Constans.LIST_TEXT;

public class SoalActivity extends AppCompatActivity {

    @BindView(R.id.soal)
    AppCompatEditText soal;
    @BindView(R.id.jawaban)
    TextView jawaban;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    AdapterJawaban adapterJawaban;
    int pos = 0;

    List<DataItemSoal> soalResponseList = new ArrayList<>();
    private int id_soal, waktu, jumlah_kata;
    int jawabanBenar;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        ButterKnife.bind(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapterJawaban = new AdapterJawaban(this);
        recycler.setAdapter(adapterJawaban);

        id_soal = getIntent().getIntExtra("id_soal", 0);
        waktu = getIntent().getIntExtra("waktu", 0);
        jumlah_kata = getIntent().getIntExtra("jumlah_kata", 0);
        getJawaban();
        adapterJawaban.setOnItemClickListener(new AdapterJawaban.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("SIZE", "onItemClick: " + pos + " " + soalResponseList.size());
                if (pos != soalResponseList.size() - 1) {
                    pos = pos + 1;
                    if (adapterJawaban.getItem(position).getBenar() == 1) {
                        jawabanBenar = jawabanBenar + 1;
                    }
                    setCurrentItem(soalResponseList, pos);
                } else {
                    Intent intent = new Intent(SoalActivity.this, HasilActivity.class);
                    intent.putExtra("waktu", waktu);
                    intent.putExtra("jumlah_kata", jumlah_kata);
                    intent.putExtra("benar", jawabanBenar);
                    intent.putExtra("ideal", soalResponseList.size());
                    intent.putExtra("id_soal", id_soal);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void getJawaban() {
        openDialog();
        AndroidNetworking.post(LIST_PERTANYAAN)
                .addBodyParameter("id_soal", String.valueOf(id_soal))
                .build()
                .getAsObject(SoalResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof SoalResponse) {
                            for (int i = 0; i < ((SoalResponse) response).getData().size(); i++) {
                                soalResponseList.add(((SoalResponse) response).getData().get(i));
                            }
                            try {
                                setCurrentItem(soalResponseList, 0);
                            } catch (NullPointerException e) {
                                CommonUtil.showToast(SoalActivity.this, "Terjadi Kesalahan");
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(SoalActivity.this, "Harap Cek Koneksi Internet Anda");
                    }
                });
    }

    public void setCurrentItem(List<DataItemSoal> dataItemSoals, int pos) {
        adapterJawaban.swap(dataItemSoals.get(pos).getJawaban());
        soal.setText(dataItemSoals.get(pos).getPertanyaan());
    }

    public void openDialog() {
        progressDialog = ProgressDialog.show(this, "Loading .. ", "Menyiapkan Data");
        progressDialog.setCancelable(false);
    }

    public void closeDialog() {
        progressDialog.dismiss();
    }
}
