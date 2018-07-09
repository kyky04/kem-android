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
import com.upi.kem.R;
import com.upi.kem.models.DataItemSoal;
import com.upi.kem.utils.CommonUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.upi.kem.data.Constans.LIST_SOAL;
import static com.upi.kem.data.Constans.LIST_TEXT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogSoal extends DialogFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    Unbinder unbinder;
    @BindView(R.id.pertanyaan)
    AppCompatEditText pertanyaan;
    private ProgressDialog progressDialog;
    private int jumlahKata, id_soal;
    private String pertanyaanText, judulText;

    public OnSucces succes;

    public OnSucces getSucces() {
        return succes;
    }

    public void setSucces(OnSucces succes) {
        this.succes = succes;
    }

    public DialogSoal() {
        // Required empty public constructor
    }

    public static DialogSoal newInstance(DataItemSoal itemText) {
        Bundle args = new Bundle();
        args.putString("status", "edit");
        args.putInt("id", itemText.getId());
        args.putInt("id_soal", itemText.getIdSoal());
        args.putString("pertanyaan", itemText.getPertanyaan());
        DialogSoal fragment = new DialogSoal();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_pertanyaan, container, false);
        unbinder = ButterKnife.bind(this, v);
        id_soal = getArguments().getInt("id_soal");
        if (getArguments() != null && getArguments().getString("status").equals("edit")) {
            toolbar.setTitle("Edit Text");
            btnFinish.setText("Edit");
            pertanyaan.setText(getArguments().getString("pertanyaan"));
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
                pertanyaanText = pertanyaan.getText().toString();
                if (!pertanyaan.equals("")) {
                    if (getArguments().getString("status").equals("edit")) {
                        edit();
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
        AndroidNetworking.post(LIST_SOAL)
                .addBodyParameter("id_soal", String.valueOf(id_soal))
                .addBodyParameter("pertanyaan", String.valueOf(pertanyaanText))
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
        AndroidNetworking.put(LIST_SOAL + "/{id}")
                .addPathParameter("id", String.valueOf(getArguments().getInt("id")))
                .addBodyParameter("id_soal", String.valueOf(id_soal))
                .addBodyParameter("pertanyaan", String.valueOf(pertanyaanText))
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
                        CommonUtil.showToast(getActivity(), "Gagal " + anError.getErrorDetail());
                    }
                });
    }

    public interface OnSucces {
        void onSuccesPost();
    }
}
