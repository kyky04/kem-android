package com.upi.kem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.upi.kem.models.JawabanItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Comp on 2/11/2018.
 */

public class AdapterJawaban extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<JawabanItem> itemJawaban;


    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterJawaban(Context ctx) {
        this.ctx = ctx;
        itemJawaban = new ArrayList<>();
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.jawaban)
        TextView jawaban;
        @BindView(R.id.llClik)
        LinearLayout llClik;

        public OriginalViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jawaban, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            if(position == 0){
                view.jawaban.setText("A. "+ itemJawaban.get(position).getJawaban());
            }else if(position == 1){
                view.jawaban.setText("B. "+ itemJawaban.get(position).getJawaban());
            }else if(position == 2){
                view.jawaban.setText("C. "+ itemJawaban.get(position).getJawaban());
            }else if(position == 3){
                view.jawaban.setText("D. "+ itemJawaban.get(position).getJawaban());
            }else {
                view.jawaban.setText(itemJawaban.get(position).getJawaban());
            }
            view.llClik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return itemJawaban.size();
    }

    public void add(JawabanItem dataItemRapatK3) {
        itemJawaban.add(dataItemRapatK3);
        notifyItemInserted(itemJawaban.size() + 1);
    }

    public void addAll(List<JawabanItem> dataItemRapatK3List) {
        for (JawabanItem rapatK3 : dataItemRapatK3List) {
            add(rapatK3);
        }
    }

    public void removeAll() {
        itemJawaban.clear();
        notifyDataSetChanged();
    }

    public void swap(List<JawabanItem> datas) {
        if (datas == null || datas.size() == 0){
            itemJawaban.clear();
            return;
        }
        if (itemJawaban != null && itemJawaban.size() > 0)
            itemJawaban.clear();
        itemJawaban.addAll(datas);
        notifyDataSetChanged();

    }

    public JawabanItem getItem(int pos) {
        return itemJawaban.get(pos);
    }
}
