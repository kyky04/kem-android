package com.upi.kem.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.upi.kem.R;
import com.upi.kem.models.JawabanItem;
import com.upi.kem.utils.CommonUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.upi.kem.data.Constans.JAWABAN;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogJawaban extends DialogFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    Unbinder unbinder;
    @BindView(R.id.pertanyaan)
    AppCompatEditText pertanyaan;
    @BindView(R.id.sp_pilihan)
    Spinner spPilihan;

    private ProgressDialog progressDialog;
    private int jumlahKata, id_soal;
    private String pertanyaanText, judulText;

    public OnSucces succes;
    private int pilihan;

    public OnSucces getSucces() {
        return succes;
    }

    public void setSucces(OnSucces succes) {
        this.succes = succes;
    }

    public DialogJawaban() {
        // Required empty public constructor
    }

    public static DialogJawaban newInstance(JawabanItem itemText) {
        Bundle args = new Bundle();
        args.putString("status", "edit");
        args.putInt("id", itemText.getId());
        args.putInt("id_soal", itemText.getIdSoal());
        args.putString("jawaban", itemText.getJawaban());
        args.putInt("benar", itemText.getBenar());
        DialogJawaban fragment = new DialogJawaban();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_jawaban, container, false);
        unbinder = ButterKnife.bind(this, v);
        id_soal = getArguments().getInt("id_soal");
        if (getArguments() != null && getArguments().getString("status").equals("edit")) {
            toolbar.setTitle("Edit Jawaban");
            btnFinish.setText("Edit");
            pertanyaan.setText(getArguments().getString("jawaban"));
            if(getArguments().getInt("benar") == 1){
                spPilihan.setSelection(0);
            }else {
                spPilihan.setSelection(1);
            }
        }

        spPilihan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    pilihan = 1;
                } else if (position == 1) {
                    pilihan = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        AndroidNetworking.post(JAWABAN)
                .addBodyParameter("id_soal", String.valueOf(id_soal))
                .addBodyParameter("jawaban", String.valueOf(pertanyaanText))
                .addBodyParameter("benar", String.valueOf(pilihan))
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
        AndroidNetworking.put(JAWABAN + "/{id}")
                .addPathParameter("id", String.valueOf(getArguments().getInt("id")))
                .addBodyParameter("id_soal", String.valueOf(id_soal))
                .addBodyParameter("jawaban", String.valueOf(pertanyaanText))
                .addBodyParameter("benar", String.valueOf(pilihan))
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
