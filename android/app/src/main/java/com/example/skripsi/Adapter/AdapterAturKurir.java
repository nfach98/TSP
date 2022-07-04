package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.Model.PengirimanModel.DataPengirimanModel;
import com.example.skripsi.OnClickListener;
import com.example.skripsi.R;

import java.util.List;

public class AdapterAturKurir extends RecyclerView.Adapter<AdapterAturKurir.HolderAturKurir> {
    private Context ctx;
    private List<DataPengirimanModel> listPengiriman;
    private List<DataPengirimanModel> listPengiriman2;
    private int idKirim;
    private OnClickListener onClickListener;

    public AdapterAturKurir(Context ctx, List<DataPengirimanModel> listPengiriman) {
        this.ctx = ctx;
        this.listPengiriman = listPengiriman;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HolderAturKurir onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_atur_kurir, parent, false);
        HolderAturKurir holder = new HolderAturKurir(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAturKurir holder, int position) {
        DataPengirimanModel dpm = listPengiriman.get(position);

        holder.setId(dpm.getId());
        holder.tvNama.setText(dpm.getNama());
        holder.tvAlamat.setText(dpm.getAlamat());
        holder.tvLatLng.setText(dpm.getLatitude() + ", " + dpm.getLatitude());
    }

    @Override
    public int getItemCount() {
        return listPengiriman.size();
    }

    public class HolderAturKurir extends RecyclerView.ViewHolder {
        TextView tvNama, tvAlamat, tvLatLng;
        ImageView ivCheck;
        int id;

        public HolderAturKurir(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvLatLng = itemView.findViewById(R.id.tvLatLng);
            ivCheck = itemView.findViewById(R.id.ivCheck);

            itemView.setOnClickListener(view -> onClickListener.onClick(id, ivCheck));
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
