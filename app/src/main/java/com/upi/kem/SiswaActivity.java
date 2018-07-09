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
import com.upi.kem.adapter.AdapterSiswa;
import com.upi.kem.adapter.AdapterText;
import com.upi.kem.models.SiswaResponse;
import com.upi.kem.models.TextResponse;
import com.upi.kem.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.upi.kem.data.Constans.KELAS;
import static com.upi.kem.data.Constans.LIST_SISWA;
import static com.upi.kem.data.Constans.LIST_SISWA_BY_KLEAS;
import static com.upi.kem.data.Constans.LIST_TEXT;

public class SiswaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    AdapterSiswa adapterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapterText = new AdapterSiswa(this);
        recycler.setAdapter(adapterText);
        getListTExt();
        adapterText.setOnItemClickListener(new AdapterSiswa.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                dialogSiap(position);
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
        AndroidNetworking.get(LIST_SISWA_BY_KLEAS)
                .addQueryParameter("id_kelas", KELAS)
                .build()
                .getAsObject(SiswaResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof SiswaResponse) {
                            adapterText.swap(((SiswaResponse) response).getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(SiswaActivity.this, "Harap Cek Koneksi Internet Anda");
                    }
                });
    }

}
