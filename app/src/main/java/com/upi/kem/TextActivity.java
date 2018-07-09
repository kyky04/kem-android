package com.upi.kem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.adapter.AdapterText;
import com.upi.kem.models.TextResponse;
import com.upi.kem.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.upi.kem.data.Constans.KELAS;
import static com.upi.kem.data.Constans.LIST_TEXT_BY_KELAS;

public class TextActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    AdapterText adapterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapterText = new AdapterText(this);
        recycler.setAdapter(adapterText);
        getListTExt();
        adapterText.setOnItemClickListener(new AdapterText.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                dialogSiap(position);
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListTExt();
            }
        });
    }

    private void getListTExt() {
        refresh.setRefreshing(true);
        AndroidNetworking.get(LIST_TEXT_BY_KELAS)
                .addQueryParameter("id_kelas", KELAS)
                .build()
                .getAsObject(TextResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof TextResponse) {
                            adapterText.swap(((TextResponse) response).getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(TextActivity.this, "Harap Cek Koneksi Internet Anda");
                    }
                });
    }

    public void dialogSiap(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Sudah siap ?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TextActivity.this, DetailTextActivity.class);
                        intent.putExtra("id_soal", adapterText.getItem(position).getId());
                        intent.putExtra("jumlah_kata", adapterText.getItem(position).getJumlahKata());
                        intent.putExtra("judul", adapterText.getItem(position).getJudul());
                        intent.putExtra("isi", adapterText.getItem(position).getPertanyaan());
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}
