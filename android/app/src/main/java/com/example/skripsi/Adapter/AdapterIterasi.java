package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.Model.IterasiModel;
import com.example.skripsi.R;

import java.util.List;

public class AdapterIterasi extends RecyclerView.Adapter<AdapterIterasi.HolderData> {
    private Context ctx;
    private List<IterasiModel> listIterasi;

    public AdapterIterasi(Context ctx, List<IterasiModel> listIterasi) {
        this.ctx = ctx;
        this.listIterasi = listIterasi;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_iterasi, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        IterasiModel iterasi = listIterasi.get(position);

        holder.tvIterasi.setText("Iterasi " + (position + 1));
        holder.tvPenyisipan.setText("Penyisipan dari (" + iterasi.getArc1() + ", " + iterasi.getArc2() + ")");
        holder.tvBobot.setText("Bobot terpilih: " + "(" + iterasi.getArc1() + "," + iterasi.getInserted() + ") - (" + iterasi.getInserted() + "," + iterasi.getArc2() + ")");
    }

    @Override
    public int getItemCount() {
        return listIterasi.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvIterasi, tvPenyisipan, tvBobot;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvIterasi = itemView.findViewById(R.id.tvIterasi);
            tvPenyisipan = itemView.findViewById(R.id.tvPenyisipan);
            tvBobot = itemView.findViewById(R.id.tvBobot);
        }

    }
}
