package com.kubangkangkung.mahasiswa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubangkangkung.mahasiswa.Activity.DetailActivity;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.R;

import org.parceler.Parcels;

import java.util.List;

import static com.kubangkangkung.mahasiswa.R.id.id_nama;

public class AdapterMhs extends RecyclerView.Adapter<AdapterMhs.HolderData>{
    public static final String DATA_MHS = "data_mhs";
    public static final String DATA_EXTRA = "data_ekstra";
    private Context ctx;
    private List<ModelMhs> listdata;

    public AdapterMhs(Context ctx, List<ModelMhs> listdata) {
        this.ctx = ctx;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mahasiswa,parent,false);
        HolderData holder=new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelMhs dm = listdata.get(position);
        holder.tvid.setText( String.valueOf(dm.getId()));
        holder.tvnama.setText(dm.getNama());
        holder.tvjurusan.setText( dm.getJurusan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah=new Intent(ctx, DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable(DATA_MHS, Parcels.wrap(listdata.get(position)));
                pindah.putExtra(DATA_EXTRA,bundle);
                ctx.startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
    TextView tvid,tvnama,tvtgl_lahir,tvjeniskel,tvjurusan,tvalamat;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvid=itemView.findViewById(R.id.id_id);
            tvnama=itemView.findViewById(id_nama);
            tvjurusan=itemView.findViewById(R.id.id_jurusan);


        }
    }
}
