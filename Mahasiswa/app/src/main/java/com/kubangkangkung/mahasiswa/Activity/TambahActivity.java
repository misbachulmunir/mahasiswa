package com.kubangkangkung.mahasiswa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kubangkangkung.mahasiswa.Model.ResponseMhs;
import com.kubangkangkung.mahasiswa.R;
import com.kubangkangkung.mahasiswa.Retrofit.InterfcMhs;
import com.kubangkangkung.mahasiswa.Retrofit.RetroConfig;
import com.kubangkangkung.mahasiswa.Volley.Constant;
import com.kubangkangkung.mahasiswa.Volley.RequestHAndler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
Button bttambah;
String nama,tgllahir,jenkel,jurusan,alamat;
EditText txnama,txttgllahir,txtalamat;
Spinner spinnerr;
RadioButton btnpria;
    private static final String TAG = "TambahActivity";
String [] pilihanjurusan={"Teknik Informatika","Sistem Informasi","Rekayasa Perangkat Lunak"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        bttambah=findViewById(R.id.add_tambah);
        txnama=findViewById(R.id.add_nama);
        txttgllahir=findViewById(R.id.add_tgllahir);
        txtalamat=findViewById(R.id.add_alamat);
        spinnerr=findViewById(R.id.spinner);
        btnpria=findViewById(R.id.radio_pirates);

        ArrayAdapter adapter=new ArrayAdapter(TambahActivity.this,android.R.layout.simple_spinner_dropdown_item,pilihanjurusan);
        spinnerr.setAdapter(adapter);
         btnpria.toggle();
         if(btnpria.isChecked()){
             jenkel="Laki - laki";
         }else {
             jenkel="Perempuan";
         }
         ValidasiInput();

        PickTanggal();


    }

    //pick tanggal
    private void PickTanggal() {
        txttgllahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();

                final int tahun=calendar.get(calendar.YEAR);
                final int bulan=calendar.get(calendar.MONTH);
                final int hari=calendar.get(calendar.DATE);


                DatePickerDialog dialog=new DatePickerDialog(TambahActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal=Calendar.getInstance();
                        cal.set(year,month,dayOfMonth);

                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                        txttgllahir.setText(dateFormat.format(cal.getTime()));

                    }
                },tahun,bulan,hari);
                dialog.show();
            }
        });
    }

    private void ValidasiInput() {
        bttambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama=txnama.getText().toString();
                tgllahir=txttgllahir.getText().toString();
                jurusan=spinnerr.getSelectedItem().toString();
                alamat=txtalamat.getText().toString();

                if(nama.trim().equals("")){
                    txnama.setError("Nama belum di isi");
                }else if (tgllahir.trim().equals("")){
                    txttgllahir.setError("Tanggal lahir belum di isi");
                }else if(alamat.trim().equals("")){
                    txtalamat.setError("Alamat belum di isi");
                }
                else{
                   //MasukanData() ;
                    AddData();
                }



            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    jenkel="Laki - laki";
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    jenkel="Perempuan";
                    break;
        }
    }

    //method memasukan data ke db
    private void MasukanData() {


        InterfcMhs request= RetroConfig.konekRetrofit().create(InterfcMhs.class);
        Call<ResponseMhs> simpanData=request.CreateDataMhs(nama,tgllahir,jenkel,jurusan,alamat);
        simpanData.enqueue(new Callback<ResponseMhs>() {
            @Override
            public void onResponse(Call<ResponseMhs> call, Response<ResponseMhs> response) {
                int kode=response.body().getKode();
                String pesan=response.body().getPesan();
                Toast.makeText(TambahActivity.this, "Berhasil Menambah Data ", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseMhs> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menambah Data"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //masukan data menggunakan voley
    private void AddData(){

        String nama = txnama.getText().toString();
        String tgllhr = txttgllahir.getText().toString();
        String jeniskel = jenkel;
        String alamat = txtalamat.getText().toString();
        String jurusan = spinnerr.getSelectedItem().toString();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CreateUrl, new com.android.volley.Response.Listener<String>() {

                @Override

                public void onResponse(String response) {
                    Log.d(TAG, "Ada Response");
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                       // JSONArray jsonarray = new JSONArray(response);
                        Toast.makeText(TambahActivity.this, "Ada response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(TambahActivity.this, jsonarray.getString("message"), Toast.LENGTH_SHORT).show();
//                        if(jsonarray.getString("message").equals("Data Added Successfully")){
//                            finish();
//                        }
                        finish();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(TambahActivity.this, "Failed to Data"+error,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onErrorResponse: "+error);
                }
            }){
                protected Map<String , String> getParams() throws AuthFailureError {
                    Map<String , String> params = new HashMap<>();
                    params.put("nama", nama);
                    params.put("tgl_lahir", tgllhr);
                    params.put("jenis_kelamin", jeniskel);
                    params.put("jurusan",jurusan);
                    params.put("alamat",alamat);
                    System.out.println(params);
                    return params;
                }
            };
            RequestHAndler.getInstance(TambahActivity.this).addToRequestQueue(stringRequest);

        }



}