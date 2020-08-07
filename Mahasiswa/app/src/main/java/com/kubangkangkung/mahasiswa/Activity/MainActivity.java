package com.kubangkangkung.mahasiswa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kubangkangkung.mahasiswa.Adapter.AdapterMhs;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.Model.ResponseList;
import com.kubangkangkung.mahasiswa.Model.ResponseMhs;
import com.kubangkangkung.mahasiswa.R;
import com.kubangkangkung.mahasiswa.Retrofit.InterfcMhs;
import com.kubangkangkung.mahasiswa.Retrofit.RetroConfig;
import com.kubangkangkung.mahasiswa.Retrofit.RetroConfigCi;
import com.kubangkangkung.mahasiswa.Volley.Constant;
import com.kubangkangkung.mahasiswa.Volley.Model;
import com.kubangkangkung.mahasiswa.Volley.MyAdapter;
import com.kubangkangkung.mahasiswa.Volley.RequestHAndler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvData;
    private List<ModelMhs> listdata=new ArrayList<>();
    private  ArrayList<ResponseList>datamhs;
    private  List<ModelMhs>listItemvoley=new ArrayList<>();
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;


    FloatingSearchView search;
    SwipeRefreshLayout swipe;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvData=findViewById(R.id.recyclemain);
        swipe=findViewById(R.id.id_swipe);
        search=findViewById(R.id.floating_search_view);
//        ModelMhs data1=new ModelMhs(1,"Halo","29 Mei 1998","Laki laki","Ok","alamat");
//        listItemvoley.add(data1);
//        Log.d(TAG, "onCreate: "+data1);
//
//        ambilDataci();Tr
        try {
            refresh_list();
        } catch (Exception e){
            Toast.makeText(this, "Gagal Menghubungi Setrver", Toast.LENGTH_SHORT).show();
        }

//        Model datadummy= new Model("2","3","r","0909","sada");
//
//
//       listItemvoley.add(datadummy);
//        rvData.setAdapter(new AdapterMhs(MainActivity.this,listdata));
//        rvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       rvData.setAdapter(new MyAdapter(listItemvoley,MainActivity.this));
       rvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        //ketika tombol cari di tekan
      //  TombolCari();
        //ketika di swipe
      ketikaDiSwipe();
        TombolCari();
    }
//method swipe
    private void ketikaDiSwipe() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
               // ambilData();
                refresh_list();
                swipe.setRefreshing(false);
            }
        });
    }

    private void TombolCari() {
        search.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                List<ModelMhs> filterdatanya=fiterData(listItemvoley, newQuery);
                rvData.setAdapter(new AdapterMhs(MainActivity.this,filterdatanya));
            }
        });
    }


    //method ambil data menggunakan retrofit php native
    private void ambilData() {
        final ProgressDialog loding = new ProgressDialog(MainActivity.this);
        loding.setMessage("Tunggu Sebentar...");
        loding.show();

        InterfcMhs request = RetroConfig.konekRetrofit().create(InterfcMhs.class);

        Call<ResponseMhs> tampilData = request.RetrieveDataMhs();
        tampilData.enqueue(new Callback<ResponseMhs>() {
            @Override
            public void onResponse(Call<ResponseMhs> call, Response<ResponseMhs> response) {
                int kode=response.body().getKode();
                String pesan=response.body().getPesan();
                listdata=response.body().getData();
                rvData.setAdapter(new AdapterMhs(MainActivity.this,listdata));
                loding.dismiss();
                Log.d(TAG, "onResponse: "+kode+pesan);

            }

            @Override
            public void onFailure(Call<ResponseMhs> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menampilkan data", Toast.LENGTH_SHORT).show();
            }
        });

    }

   //get data menggunakan ci
    private void ambilDataci() {
        final ProgressDialog loding = new ProgressDialog(MainActivity.this);
        loding.setMessage("Tunggu Sebentar...");
        loding.show();

        InterfcMhs request = RetroConfigCi.konekRetrofit().create(InterfcMhs.class);
       Call<List<ModelMhs>>dataList=request.Ambildataci();
         dataList.enqueue(new Callback<List<ModelMhs>>() {
      @Override
      public void onResponse(Call<List<ModelMhs>> call, Response<List<ModelMhs>> response) {
          Toast.makeText(MainActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
          loding.dismiss();
          listdata=response.body();
          rvData.setAdapter(new AdapterMhs(MainActivity.this,listdata));

      }

      @Override
      public void onFailure(Call<List<ModelMhs>> call, Throwable t) {
          Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
          Log.d(TAG, "onFailure: "+t.getMessage());
      }
  });

    }
    
    
    //method pencarian
    private List<ModelMhs> fiterData(List<ModelMhs> listdata, String newQuery) {
        String lowercase=newQuery.toLowerCase();
        List<ModelMhs>filterData=new ArrayList<>();
        for (int i = 0; i < listdata.size(); i++) {
            String nama=listdata.get(i).getNama().toLowerCase();
            String jurusan=listdata.get(i).getJurusan().toLowerCase();

            if(nama.contains(lowercase)||jurusan.contains(lowercase)){
                filterData.add(listdata.get(i));
            }
        }
        return filterData;
    };

    //menuju activity tambah ketika fab ditekan
    public void KeTambahActivity(View view) {
        startActivity(new Intent(MainActivity.this,TambahActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            refresh_list();
        }catch (Exception e){
            Toast.makeText(this, "Gagal Menghubungi Setrver", Toast.LENGTH_SHORT).show();
        }

       //ambilDataci();
    }



    //get data menggunakan volley
    public void refresh_list(){
        listItemvoley.clear();
        adapter = new MyAdapter(listItemvoley,getApplicationContext());
        rvData.setAdapter(adapter);


        rvData.setItemAnimator(new DefaultItemAnimator());
       // progressDialog.setMessage("Loading");
       // progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GetUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  progressDialog.dismiss()
          //      Log.d(TAG, "response: "+stringRequest.getBody());
//                try{
//
//                  //  progressDialog.hide();
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("");
//                    Log.d(TAG, "Cek disini: ");
//
//                    Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
//                    for (int i = 0; i<jsonObject.length(); i++){
//                        JSONObject o = jsonArray.getJSONObject(i);
//                        ModelMhs item = new ModelMhs(
//                                o.getInt("id"),
//                                o.getString("nama"),
//                                o.getString("tgl_lahir"),
//                                o.getString("jenis_kelamin"),
//                                o.getString("jurusan"),
//                                o.getString("alamat")
//
//                        );
//                        Log.d(TAG, "Cek Item"+ item);
//                        listItemvoley.add(item);
//
//                        adapter = new MyAdapter(listItemvoley,getApplicationContext());
//                        rvData.setAdapter(adapter);
//
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
                try {

                   // JSONArray jsonarray = new JSONArray(response);
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        ModelMhs item = new ModelMhs(
                                jsonobject.getInt("id"),
                                jsonobject.getString("nama"),
                                jsonobject.getString("tgl_lahir"),
                                jsonobject.getString("jenis_kelamin"),
                                jsonobject.getString("jurusan"),
                                jsonobject.getString("alamat")
                        );
                        listItemvoley.add(item);
                        System.out.println(item);

                      adapter = new MyAdapter(listItemvoley,getApplicationContext());
                      rvData.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: "+e.getMessage());
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "Ada response", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            //    progressDialog.hide();

                Toast.makeText(MainActivity.this, "Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("data", "no");
                System.out.println(params);
                return params;
            }
        };
        RequestHAndler.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

}

