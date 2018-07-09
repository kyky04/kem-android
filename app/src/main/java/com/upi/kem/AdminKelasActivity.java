package com.upi.kem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.adapter.AdapterKelas;
import com.upi.kem.adapter.AdapterSiswa;
import com.upi.kem.models.KelasResponse;
import com.upi.kem.models.SiswaResponse;
import com.upi.kem.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.upi.kem.data.Constans.KELAS;
import static com.upi.kem.data.Constans.LIST_KELAS;
import static com.upi.kem.data.Constans.LIST_SISWA_BY_KLEAS;

public class AdminKelasActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    AdapterKelas adapterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapterText = new AdapterKelas(this);
        recycler.setAdapter(adapterText);
        getListTExt();
        adapterText.setOnItemClickListener(new AdapterKelas.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(AdminKelasActivity.this, AdminTextActivity.class);
                intent.putExtra("id_kelas", adapterText.getItem(position).getId());
                startActivity(intent);
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
        AndroidNetworking.get(LIST_KELAS)
                .build()
                .getAsObject(KelasResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof KelasResponse) {
                            adapterText.swap(((KelasResponse) response).getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(AdminKelasActivity.this, "Harap Cek Koneksi Internet Anda");
                    }
                });
    }

}
