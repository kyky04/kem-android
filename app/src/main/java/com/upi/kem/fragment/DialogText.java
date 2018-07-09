package com.upi.kem.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.upi.kem.HasilActivity;
import com.upi.kem.R;
import com.upi.kem.models.DataItemText;
import com.upi.kem.utils.CommonUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.upi.kem.data.Constans.LIST_SKOR;
import static com.upi.kem.data.Constans.LIST_TEXT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogText extends DialogFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.judul_text)
    AppCompatEditText judul;
    @BindView(R.id.kata)
    AppCompatEditText kata;
    @BindView(R.id.isi)
    AppCompatEditText isi;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    Unbinder unbinder;
    private ProgressDialog progressDialog;
    private int jumlahKata, id_kelas;
    private String isiText, judulText;

    public OnSucces succes;

    public OnSucces getSucces() {
        return succes;
    }

    public void setSucces(OnSucces succes) {
        this.succes = succes;
    }

    public DialogText() {
        // Required empty public constructor
    }

    public static DialogText newInstance(DataItemText itemText) {
        Bundle args = new Bundle();
        args.putString("status", "edit");
        args.putInt("id", itemText.getId());
        args.putInt("id_kelas", itemText.getIdKelas());
        args.putString("judul", itemText.getJudul());
        args.putString("isi", itemText.getPertanyaan());
        args.putInt("jumlah", itemText.getJumlahKata());
        DialogText fragment = new DialogText();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_text, container,false);
        unbinder = ButterKnife.bind(this, v);
        id_kelas = getArguments().getInt("id_kelas");
        if (getArguments() != null && getArguments().getString("status").equals("edit")) {
            toolbar.setTitle("Edit Text");
            btnFinish.setText("Edit");
            judul.setText(getArguments().getString("judul"));
            isi.setText(getArguments().getString("isi"));
            kata.setText(String.valueOf(getArguments().getInt("jumlah")));
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_cancel, R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_finish:
                isiText = isi.getText().toString();
                judulText = judul.getText().toString();
                jumlahKata = Integer.parseInt(kata.getText().toString());
                if (!isiText.equals("") || !judulText.equals("") || !kata.equals("")) {
                    if (getArguments().getString("status").equals("edit")) {
                        edit();
                        Log.d("id_text", String.valueOf(getArguments().getInt("id")));
                    } else {
                        postSkor();
                    }
                } else {
                    CommonUtil.showToast(getActivity(), "Harap Isi Semua Bidang");
                }
                break;
        }
    }

    public void openDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "Loading .. ", "Menyiapkan Data");
        progressDialog.setCancelable(false);
    }

    public void closeDialog() {
        progressDialog.dismiss();
    }

    void postSkor() {
        openDialog();
        AndroidNetworking.post(LIST_TEXT)
                .addBodyParameter("id_kelas", String.valueOf(id_kelas))
                .addBodyParameter("pertanyaan", isiText)
                .addBodyParameter("judul", judulText)
                .addBodyParameter("jumlah_kata", String.valueOf(jumlahKata))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        succes.onSuccesPost();
                        CommonUtil.showToast(getActivity(), "Succes");
                        dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Gagal");
                    }
                });
    }

    void edit() {
        openDialog();
        AndroidNetworking.put(LIST_TEXT + "/{id}")
                .addPathParameter("id", String.valueOf(getArguments().getInt("id")))
                .addBodyParameter("id_kelas", String.valueOf(id_kelas))
                .addBodyParameter("pertanyaan", isiText)
                .addBodyParameter("judul", judulText)
                .addBodyParameter("jumlah_kata", String.valueOf(jumlahKata))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        succes.onSuccesPost();
                        CommonUtil.showToast(getActivity(), "Succes");
                        dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Gagal "+anError.getErrorDetail());
                    }
                });
    }

    public interface OnSucces {
        void onSuccesPost();
    }
}
