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
import com.example.skripsi.Activity.Kurir.AturPengirimanKurirActivity;
import com.example.skripsi.Activity.Koor.DaftarKurir.DataKurirActivity;
import com.example.skripsi.Activity.Koor.DaftarKurir.UbahDataKurirActivity;
import com.example.skripsi.Model.DataKurirModel.DataModel;
import com.example.skripsi.Model.DataKurirModel.ResponModel;
import com.example.skripsi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listDataKurir;
    private List<DataModel> listKurir;
    private String idKurir;

    public AdapterData(Context ctx, List<DataModel> listDataKurir) {
        this.ctx = ctx;
        this.listDataKurir = listDataKurir;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_kurir, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listDataKurir.get(position);

        holder.tvId.setText(dm.getId());
        holder.tvNama.setText(dm.getName());
        holder.tvAlamat.setText(dm.getAddress());
        holder.tvTelp.setText(dm.getNo());
    }

    @Override
    public int getItemCount() {
        return listDataKurir.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvNama, tvId, tvAlamat, tvTelp;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvId = itemView.findViewById(R.id.tvId);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvTelp = itemView.findViewById(R.id.tvTelp);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih Operasi Yang Akan Dilakukan");
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                    dialogPesan.setCancelable(true);

                    idKurir = tvId.getText().toString();

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((DataKurirActivity) ctx).retrieveData();
                                }
                            }, 1000);

                        }
                    });
                    dialogPesan.setNeutralButton("Atur Rute",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ctx, AturPengirimanKurirActivity.class);
                            intent.putExtra("id_kurir", idKurir);
                            ctx.startActivity(intent);
                            dialogInterface.dismiss();
                        }
                    });
                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getData();
                            dialogInterface.dismiss();
                        }
                    });

                    dialogPesan.show();
                    return false;
                }
            });
        }

        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponModel> hapusData = ardData.ardDeleteData(idKurir);

            hapusData.enqueue(new Callback<ResponModel>() {
                @Override
                public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponModel> ambilData = ardData.ardGetData(idKurir);

            ambilData.enqueue(new Callback<ResponModel>() {
                @Override
                public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listKurir = response.body().getData();

                    String varIdKurir = listKurir.get(0).getId();
                    String varNamaKurir = listKurir.get(0).getName();
                    String varAlamat = listKurir.get(0).getAddress();
                    String varTelp = listKurir.get(0).getNo();

                    Intent kirim = new Intent(ctx, UbahDataKurirActivity.class);
                    kirim.putExtra("xId", varIdKurir);
                    kirim.putExtra("xNama", varNamaKurir);
                    kirim.putExtra("xAlamat", varAlamat);
                    kirim.putExtra("xTelp", varTelp);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
