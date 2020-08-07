package com.kubangkangkung.mahasiswa.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kubangkangkung.mahasiswa.Activity.DetailActivity;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.R;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kubangkangkung.mahasiswa.Adapter.AdapterMhs.DATA_EXTRA;
import static com.kubangkangkung.mahasiswa.Adapter.AdapterMhs.DATA_MHS;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ModelMhs> listItems;
    private Context context;
 //   private ProgressDialog dialog;


    public MyAdapter(List<ModelMhs> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView nama;
        public TextView jurusan;
        public CardView cardView;
        public ViewHolder(View itemView ) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id_idv);
            nama = (TextView) itemView.findViewById(R.id.id_namav);
            jurusan = (TextView) itemView.findViewById(R.id.id_jurusanv);
            cardView = (CardView) itemView.findViewById(R.id.cardid);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_voley, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelMhs listItem = listItems.get(position);
        holder.id.setText(String.valueOf( listItem.getId()));
        holder.nama.setText(listItem.getNama());
        holder.jurusan.setText(listItem.getJurusan());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah=new Intent(context, DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable(DATA_MHS, Parcels.wrap(listItems.get(position)));
                pindah.putExtra(DATA_EXTRA,bundle);
                context.startActivity(pindah);
            }
        });


//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                Intent intent;
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                final ProgressDialog dialog = new ProgressDialog(view.getContext());
//                dialog.setMessage("Loading Delete Data");
//                final CharSequence[] dialogitem = {"View Data","Edit Data","Delete Data"};
//                builder.setTitle(listItem.getNama());
//                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        switch (i){
//                            case 0 :
////                                Intent intent = new Intent(view.getContext(), DetailData.class);
////                                intent.putExtra("id", listItem.getId());
////                                intent.putExtra("uid",listItem.getUId());
////                                intent.putExtra("name",listItem.getName());
////                                intent.putExtra("address",listItem.getAddress());
////                                intent.putExtra("phone", listItem.getPhone());
////                                view.getContext().startActivity(intent);
//                                break;
//                            case 1 :
//
////                                Intent intent2 = new Intent(view.getContext(), EditActivity.class);
////                                intent2.putExtra("id", listItem.getId());
////                                intent2.putExtra("uid",listItem.getUId());
////                                intent2.putExtra("name",listItem.getName());
////                                intent2.putExtra("address",listItem.getAddress());
////                                intent2.putExtra("phone", listItem.getPhone());
////                                view.getContext().startActivity(intent2);
////                                break;
//                            case 2 :
//
//                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
//                                builderDel.setTitle(listItem.getNama());
//                                builderDel.setMessage("Are You Sure, You Want to Delete Data?");
//                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialog.show();
//
//                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL, new Response.Listener<String>() {
//                                            @Override
//                                            public void onResponse(String response) {
//                                                dialog.hide();
//                                                dialog.dismiss();
//                                                Toast.makeText(view.getContext(),"Successfully Deleted Data "+ listItem.getNama(),Toast.LENGTH_LONG).show();
//                                               // ListActivity.ma.refresh_list();
//
//                                            }
//                                        }, new Response.ErrorListener() {
//                                            @Override
//                                            public void onErrorResponse(VolleyError error) {
//                                                dialog.hide();
//                                                dialog.dismiss();
//                                            }
//                                        }){
//                                            protected HashMap<String, String> getParams() throws AuthFailureError {
//                                                Map<String, String> params= new HashMap<>();
//                                                params.put("id", String.valueOf(listItem.getId()));
//                                                return (HashMap<String, String>) params;
//
//                                            }
//                                        };
//                                        RequestHAndler.getInstance(view.getContext()).addToRequestQueue(stringRequest);
//                                        dialogInterface.dismiss();
//                                    }
//                                });
//
//                                builderDel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                });
//
//
//                                builderDel.create().show();
//                                break;
//                        }
//                    }
//                });
//
//                builder.create().show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
