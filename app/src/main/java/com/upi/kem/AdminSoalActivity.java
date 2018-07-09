package com.upi.kem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.adapter.AdapterSoalAdmin;
import com.upi.kem.fragment.DialogSoal;
import com.upi.kem.models.DataItemSoal;
import com.upi.kem.models.SoalResponse;
import com.upi.kem.utils.CommonUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.upi.kem.data.Constans.LIST_PERTANYAAN;
import static com.upi.kem.data.Constans.LIST_SOAL;
import static com.upi.kem.data.Constans.LIST_TEXT;

public class AdminSoalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    AdapterSoalAdmin adapterText;
    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.judul_text)
    AppCompatEditText judulText;
    private int id_soal;
    private String text;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_soal);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapterText = new AdapterSoalAdmin(this);
        recycler.setAdapter(adapterText);
        id_soal = getIntent().getIntExtra("id_soal", 0);
        text = getIntent().getStringExtra("soal");
        judulText.setText(text);
        getListTExt();
        adapterText.setOnItemClickListener(new AdapterSoalAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                editPost(adapterText.getItem(position));
            }

            @Override
            public void onDelete(int position) {
                dialogHapus(adapterText.getItem(position).getId());
            }

            @Override
            public void onSoal(int position) {
                Intent intent = new Intent(AdminSoalActivity.this, AdminJawabanActivity.class);
                intent.putExtra("id_soal", adapterText.getItem(position).getId());
                intent.putExtra("soal", adapterText.getItem(position).getPertanyaan());
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
        AndroidNetworking.post(LIST_PERTANYAAN)
                .addQueryParameter("id_soal", String.valueOf(id_soal))
                .build()
                .getAsObject(SoalResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof SoalResponse) {
                            adapterText.swap(((SoalResponse) response).getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(AdminSoalActivity.this, "Harap Cek Koneksi Internet Anda");
                    }
                });
    }

    @OnClick(R.id.add)
    public void onViewClicked() {
        showDialogPost();
    }

    void delete(int id) {
        openDialog();
        AndroidNetworking.delete(LIST_SOAL + "/{id}")
                .addPathParameter("id", String.valueOf(id))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        CommonUtil.showToast(AdminSoalActivity.this, "Succes Menghapus");
                        getListTExt();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(AdminSoalActivity.this, "Gagal " + anError.getErrorDetail());
                    }
                });
    }

    void dialogHapus(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda yakin menghapus data ini ?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(id);
                    }
                }).setNegativeButton("KEMBALI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void openDialog() {
        progressDialog = ProgressDialog.show(AdminSoalActivity.this, "Loading .. ", "Menyiapkan Data");
        progressDialog.setCancelable(false);
    }

    public void closeDialog() {
        progressDialog.dismiss();
    }

    void showDialogPost() {
        Bundle data = new Bundle();
        data.putInt("id_soal", id_soal);
        data.putString("status", "post");

        DialogSoal dialogText = new DialogSoal();
        dialogText.setArguments(data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(android.R.id.content, dialogText).addToBackStack(null).commit();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        dialogText.setSucces(new DialogSoal.OnSucces() {
            @Override
            public void onSuccesPost() {
                getListTExt();
            }
        });
    }

    void editPost(DataItemSoal itemText) {
        DialogSoal dialogText = DialogSoal.newInstance(itemText);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(android.R.id.content, dialogText).addToBackStack(null).commit();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        dialogText.setSucces(new DialogSoal.OnSucces() {
            @Override
            public void onSuccesPost() {
                getListTExt();
            }
        });
    }
}
