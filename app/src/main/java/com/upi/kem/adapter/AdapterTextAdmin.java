package com.upi.kem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.upi.kem.R;
import com.upi.kem.models.DataItemText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Comp on 2/11/2018.
 */

public class AdapterTextAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DataItemText> itemText;


    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDelete(int position);

        void onSoal(int position);

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterTextAdmin(Context ctx) {
        this.ctx = ctx;
        itemText = new ArrayList<>();
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.judul)
        TextView judul;
        @BindView(R.id.jumlahKata)
        TextView jumlahKata;
        @BindView(R.id.llClik)
        LinearLayout llClik;
        @BindView(R.id.soal)
        ImageButton soal;
        @BindView(R.id.delete)
        ImageButton delete;

        public OriginalViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_text, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.judul.setText(itemText.get(position).getJudul());
            view.jumlahKata.setText("Jumlah Kata : " + itemText.get(position).getJumlahKata());
            view.llClik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
            view.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onDelete(position);
                }
            });
            view.soal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onSoal(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return itemText.size();
    }

    public void add(DataItemText dataItemRapatK3) {
        itemText.add(dataItemRapatK3);
        notifyItemInserted(itemText.size() + 1);
    }

    public void addAll(List<DataItemText> dataItemRapatK3List) {
        for (DataItemText rapatK3 : dataItemRapatK3List) {
            add(rapatK3);
        }
    }

    public void removeAll() {
        itemText.clear();
        notifyDataSetChanged();
    }

    public void swap(List<DataItemText> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (itemText != null && itemText.size() > 0)
            itemText.clear();
        itemText.addAll(datas);
        notifyDataSetChanged();

    }

    public DataItemText getItem(int pos) {
        return itemText.get(pos);
    }
}
