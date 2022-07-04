package com.example.skripsi.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Activity.Koor.AturPengiriman.DataPengirimanActivity;
import com.example.skripsi.Activity.Koor.AturPengiriman.UbahDataPengiriman;
import com.example.skripsi.Model.PengirimanModel.DataPengirimanModel;
import com.example.skripsi.Model.PengirimanModel.ResponPengirimanModel;
import com.example.skripsi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPengiriman extends RecyclerView.Adapter<AdapterPengiriman.HolderDataPengiriman> {
    private Context ctx;
    private List<DataPengirimanModel> listPengiriman;
    private List<DataPengirimanModel> listPengiriman2;
    private int idKirim;

    public AdapterPengiriman(Context ctx, List<DataPengirimanModel> listPengiriman) {
        this.ctx = ctx;
        this.listPengiriman = listPengiriman;
    }

    @NonNull
    @Override
    public HolderDataPengiriman onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pengiriman, parent, false);
        HolderDataPengiriman holder = new HolderDataPengiriman(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataPengiriman holder, int position) {
        DataPengirimanModel dpm = listPengiriman.get(position);

        holder.tvId.setText(String.valueOf(dpm.getId()));
        holder.tvNama.setText(dpm.getNama());
        holder.tvAlamat.setText(dpm.getAlamat());
        holder.tvLatitude.setText(String.valueOf(dpm.getLatitude()));
        holder.tvLongtitude.setText(String.valueOf(dpm.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return listPengiriman.size();
    }

    public class HolderDataPengiriman extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvAlamat, tvLatitude, tvLongtitude;

        public HolderDataPengiriman(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvLatitude = itemView.findViewById(R.id.tvLat);
            tvLongtitude = itemView.findViewById(R.id.tvLong);
            tvId = itemView.findViewById(R.id.tvId);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih operasi yang akan dilakukan");
                    dialogPesan.setCancelable(true);

                    idKirim = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData();
                            dialog.dismiss();
                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((DataPengirimanActivity) ctx).retrievePengiriman();
                                }
                            }, 1000);
                        }
                    });

                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getData();
                        }
                    });

                    dialogPesan.show();

                    return false;
                }
            });
        }

        private void deleteData() {
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponPengirimanModel> hapusData = ardData.ardDeletePengiriman(idKirim);

            hapusData.enqueue(new Callback<ResponPengirimanModel>() {
                @Override
                public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode + " | Pesan : "
                            + pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server! | "
                            + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponPengirimanModel> ambilData = ardData.ardGetPengiriman(idKirim);

            ambilData.enqueue(new Callback<ResponPengirimanModel>() {
                @Override
                public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listPengiriman2 = response.body().getData();

                    int varId = listPengiriman2.get(0).getId();
                    String varNama = listPengiriman2.get(0).getNama();
                    String varAlamat = listPengiriman2.get(0).getAlamat();
                    double varLat = listPengiriman2.get(0).getLatitude();
                    double varLong = listPengiriman2.get(0).getLongitude();


                   // Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan+" | Data : "+varNama+" | "+varAlamat+" | "+varLat+" | "+varLong, Toast.LENGTH_SHORT).show();

                    Intent kirim = new Intent(ctx, UbahDataPengiriman.class);
                    kirim.putExtra("xId", varId);
                    kirim.putExtra("xNama", varNama);
                    kirim.putExtra("xAlamat", varAlamat);
                    kirim.putExtra("xLat", varLat);
                    kirim.putExtra("xLong", varLong);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
