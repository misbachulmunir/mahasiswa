package com.kubangkangkung.mahasiswa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.kubangkangkung.mahasiswa.Adapter.AdapterMhs;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.Model.ResponseMhs;
import com.kubangkangkung.mahasiswa.R;
import com.kubangkangkung.mahasiswa.Retrofit.InterfcMhs;
import com.kubangkangkung.mahasiswa.Retrofit.RetroConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvData;
    private List<ModelMhs> listdata=new ArrayList<>();
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

        ambilData();
        rvData.setAdapter(new AdapterMhs(MainActivity.this,listdata));
        rvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       //ketika tombol cari di tekan
        TombolCari();
        //ketika di swipe
        ketikaDiSwipe();
    }
//method swipe
    private void ketikaDiSwipe() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                ambilData();
                swipe.setRefreshing(false);
            }
        });
    }

    private void TombolCari() {
        search.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                List<ModelMhs> filterdatanya=fiterData(listdata, newQuery);
                rvData.setAdapter(new AdapterMhs(MainActivity.this,filterdatanya));
            }
        });
    }


    //method ambil data menggunakan retrofit
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
        ambilData();
    }
}

