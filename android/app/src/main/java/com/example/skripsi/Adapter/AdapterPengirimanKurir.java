package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.Model.PengirimanModel.DataPengirimanModel;
import com.example.skripsi.OnClickListener;
import com.example.skripsi.R;

import java.util.List;

public class AdapterPengirimanKurir extends RecyclerView.Adapter<AdapterPengirimanKurir.HolderDataHitung>{
    private Context ctx;
    private final List<DataPengirimanModel> listLatLong;
    private OnClickListener onClickListener;

    public AdapterPengirimanKurir(Context ctx, List<DataPengirimanModel> listPengiriman) {
        this.ctx = ctx;
        this.listLatLong = listPengiriman;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HolderDataHitung onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pengiriman_kurir, parent, false);
        return new HolderDataHitung(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataHitung holder, int position) {
        DataPengirimanModel dhm = listLatLong.get(position);

        holder.tvId.setText(String.valueOf(dhm.getAlamat()));
        holder.tvLatitude.setText(String.valueOf(dhm.getLatitude()));
        holder.tvLongitude.setText(String.valueOf(dhm.getLongitude()));
        holder.itemView.setOnClickListener(view -> onClickListener.onClick(dhm));
    }

    @Override
    public int getItemCount() {
        return listLatLong.size();
    }

    public static class HolderDataHitung extends RecyclerView.ViewHolder{
        TextView tvId, tvLatitude, tvLongitude;

        public HolderDataHitung(@NonNull View itemView){
            super(itemView);

            tvId = itemView.findViewById(R.id.tvIdH);
            tvLatitude = itemView.findViewById(R.id.tvLatitudeH);
            tvLongitude = itemView.findViewById(R.id.tvLongitudeH);
        }
    }
}
