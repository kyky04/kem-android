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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.upi.kem.adapter.AdapterText;
import com.upi.kem.adapter.AdapterTextAdmin;
import com.upi.kem.fragment.DialogText;
import com.upi.kem.models.DataItemText;
import com.upi.kem.models.TextResponse;
import com.upi.kem.utils.CommonUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.upi.kem.data.Constans.KELAS;
import static com.upi.kem.data.Constans.LIST_TEXT;
import static com.upi.kem.data.Constans.LIST_TEXT_BY_KELAS;

public class AdminTextActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    AdapterTextAdmin adapterText;
    @BindView(R.id.add)
    FloatingActionButton add;
    private int id_kelas;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_text);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapterText = new AdapterTextAdmin(this);
        recycler.setAdapter(adapterText);
        id_kelas = getIntent().getIntExtra("id_kelas", 0);
        getListTExt();
        adapterText.setOnItemClickListener(new AdapterTextAdmin.OnItemClickListener() {
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
                Intent intent = new Intent(AdminTextActivity.this, AdminSoalActivity.class);
                intent.putExtra("id_soal", adapterText.getItem(position).getId());
                intent.putExtra("soal", adapterText.getItem(position).getJudul());
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
        AndroidNetworking.get(LIST_TEXT_BY_KELAS)
                .addQueryParameter("id_kelas", String.valueOf(id_kelas))
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
                        CommonUtil.showToast(AdminTextActivity.this, "Harap Cek Koneksi Internet Anda");
                    }
                });
    }

    public void dialogSiap(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Sudah siap ?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AdminTextActivity.this, DetailTextActivity.class);
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

    @OnClick(R.id.add)
    public void onViewClicked() {
        showDialogPost();
    }

    void showDialogPost() {
        Bundle data = new Bundle();
        data.putInt("id_kelas", id_kelas);
        data.putString("status", "post");

        DialogText dialogText = new DialogText();
        dialogText.setArguments(data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(android.R.id.content, dialogText).addToBackStack(null).commit();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        dialogText.setSucces(new DialogText.OnSucces() {
            @Override
            public void onSuccesPost() {
                getListTExt();
            }
        });
    }

    void editPost(DataItemText itemText) {
        DialogText dialogText = DialogText.newInstance(itemText);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(android.R.id.content, dialogText).addToBackStack(null).commit();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        dialogText.setSucces(new DialogText.OnSucces() {
            @Override
            public void onSuccesPost() {
                getListTExt();
            }
        });
    }

    void delete(int id) {
        openDialog();
        AndroidNetworking.delete(LIST_TEXT + "/{id}")
                .addPathParameter("id", String.valueOf(id))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        CommonUtil.showToast(AdminTextActivity.this, "Succes Menghapus");
                        getListTExt();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(AdminTextActivity.this, "Gagal " + anError.getErrorDetail());
                    }
                });
    }

    void dialogHapus(final int id){
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
        progressDialog = ProgressDialog.show(AdminTextActivity.this, "Loading .. ", "Menyiapkan Data");
        progressDialog.setCancelable(false);
    }

    public void closeDialog() {
        progressDialog.dismiss();
    }

}
