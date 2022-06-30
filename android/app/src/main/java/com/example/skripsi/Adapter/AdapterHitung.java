package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Model.HitungModel.DataHitungModel;
import com.example.skripsi.Model.HitungModel.ResponHitungModel;
import com.example.skripsi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterHitung extends RecyclerView.Adapter<AdapterHitung.HolderDataHitung>{
    private Context ctx;
    private List<DataHitungModel> listLatLong;
    private int idKirim;

    public AdapterHitung(Context ctx, List<DataHitungModel> listPengiriman) {
        this.ctx = ctx;
        this.listLatLong = listPengiriman;
    }

    @NonNull
    @Override
    public HolderDataHitung onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_latlong, parent, false);
        HolderDataHitung holder = new HolderDataHitung(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataHitung holder, int position) {
        DataHitungModel dhm = listLatLong.get(position);

        holder.tvId.setText(String.valueOf(dhm.getId()));
        holder.tvLatitude.setText(String.valueOf(dhm.getLatitude()));
        holder.tvLongitude.setText(String.valueOf(dhm.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return listLatLong.size();
    }

    public class HolderDataHitung extends RecyclerView.ViewHolder{
        TextView tvId, tvLatitude, tvLongitude;

        public HolderDataHitung(@NonNull View itemView){
            super(itemView);

            tvId = itemView.findViewById(R.id.tvIdH);
            tvLatitude = itemView.findViewById(R.id.tvLatitudeH);
            tvLongitude = itemView.findViewById(R.id.tvLongitudeH);
        }
    }

    private void getDataLatLong(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponHitungModel> ambilLatLong = ardData.ardAmbilLatLong();

        ambilLatLong.enqueue(new Callback<ResponHitungModel>() {
            @Override
            public void onResponse(Call<ResponHitungModel> call, Response<ResponHitungModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                int jmlPengiriman = response.body().getTotal();


                for (int i=0; i<jmlPengiriman; i++){
                        int[] varId = {listLatLong.get(i).getId()};
                        double[] varLat = {listLatLong.get(i).getLatitude()};
                        double[] varLong = {listLatLong.get(i).getLongitude()};
                }


                // Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan+" | Data : "+varNama+" | "+varAlamat+" | "+varLat+" | "+varLong, Toast.LENGTH_SHORT).show();

                /*Intent kirim = new Intent(ctx, UbahDataPengiriman.class);
                kirim.putExtra("xId", varId);
                kirim.putExtra("xLat", varLat);
                kirim.putExtra("xLong", varLong);
                ctx.startActivity(kirim);*/
            }

            @Override
            public void onFailure(Call<ResponHitungModel> call, Throwable t) {
                Toast.makeText(ctx, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
