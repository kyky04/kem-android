package com.upi.kem;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.adapter.AdapterSkor;
import com.upi.kem.models.SiswaResponse;
import com.upi.kem.models.SkorResponse;
import com.upi.kem.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.upi.kem.data.Constans.LIST_SISWA;
import static com.upi.kem.data.Constans.LIST_SKOR;
import static com.upi.kem.data.Constans.LIST_SKOR_BY_SISWA;

public class SkorActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    AdapterSkor adapterText;

    int id_soal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skor);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapterText = new AdapterSkor(this);
        recycler.setAdapter(adapterText);

        id_soal = getIntent().getIntExtra("id_soal",0);
        getListTExt();
        adapterText.setOnItemClickListener(new AdapterSkor.OnItemClickListener() {
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
        AndroidNetworking.get(LIST_SKOR_BY_SISWA)
                .addQueryParameter("id_soal", String.valueOf(id_soal))
                .build()
                .getAsObject(SkorResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof SkorResponse) {
                            adapterText.swap(((SkorResponse) response).getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(SkorActivity.this, "Harap Cek Koneksi Internet Anda");
                    }
                });
    }

}
