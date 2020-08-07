package com.kubangkangkung.mahasiswa.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kubangkangkung.mahasiswa.Adapter.AdapterMhs;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.Model.ResponseMhs;
import com.kubangkangkung.mahasiswa.R;
import com.kubangkangkung.mahasiswa.Retrofit.InterfcMhs;
import com.kubangkangkung.mahasiswa.Retrofit.RetroConfig;
import com.kubangkangkung.mahasiswa.Volley.Constant;
import com.kubangkangkung.mahasiswa.Volley.RequestHAndler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_ALAMAT;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_ID;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_JENISKEL;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_JURUSAN;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_NAMA;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_TGLLHR;
import static com.kubangkangkung.mahasiswa.Volley.Constant.UploadImageURL;

public class UpdateActivity extends AppCompatActivity {
    private static final String TAG = "UpdateActivity";
    int id;
    ArrayAdapter adapter;
    RadioButton btnpria,btncewe;
    Spinner spinnerr;
    String jenkel;
    Button ubahgambar;
    EditText txnama,txttgllahir,txtalamat;
    ImageView imageView;
    String [] pilihanjurusan={"Teknik Informatika","Sistem Informasi","Rekayasa Perangkat Lunak"};
    //upload image
    JSONObject jsonObject;
    RequestQueue rQueue;
    private String Document_img1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        txnama=findViewById(R.id.up_nama);
        txttgllahir=findViewById(R.id.up_tgllhr);
        txtalamat=findViewById(R.id.up_alamat);
        spinnerr=findViewById(R.id.spinners);
        btnpria=findViewById(R.id.radiopria);
        btncewe=findViewById(R.id.radiocewe);
        ubahgambar=findViewById(R.id.btnubahgambar2);
        imageView=findViewById(R.id.id_gambar_detail);

        id= Integer.parseInt(getIntent().getStringExtra(KEY_ID));
        String nama=getIntent().getStringExtra(KEY_NAMA);
        String alamat=getIntent().getStringExtra(KEY_ALAMAT);
        String tgllhr=getIntent().getStringExtra(KEY_TGLLHR);
        String jenskel=getIntent().getStringExtra(KEY_JENISKEL);
        String jurusan=getIntent().getStringExtra(KEY_JURUSAN);

        txnama.setText(nama);
        txtalamat.setText(alamat);
        txttgllahir.setText(tgllhr);
        if(jenskel.equals("Laki - laki")){
            btnpria.toggle();
        }else{
            btncewe.toggle();
        }

        spinnerr.setSelection(3);

        adapter=new ArrayAdapter(UpdateActivity.this,android.R.layout.simple_spinner_dropdown_item,pilihanjurusan);
        spinnerr.setAdapter(adapter);

        if(jurusan.equals(pilihanjurusan[0])){
            spinnerr.setSelection(0);
        }else if(jurusan.equals(pilihanjurusan[1])){
            spinnerr.setSelection(1);
        }else {
            spinnerr.setSelection(2);
        }
        Log.d(TAG, "onCreate: "+jurusan);

        PickTanggal();

        ubahgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OpenGalery =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(OpenGalery, 1000);
            }
        });
    }
    //ketika ga,bar dipilih


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == 1000) {
            if (data != null) {
                Uri contentURI = data.getData();
                Log.d(TAG, "onActivityResult: "+contentURI);
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageView.setImageBitmap(bitmap);
                    Log.d(TAG, "onActivityResult: "+bitmap);
                  //  Upload(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
//    //image to string
//    public String BitMapToString(Bitmap userImage1) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
//        byte[] b = baos.toByteArray();
//        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
//        return Document_img1;
//    }

    //resixe image
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float)width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }

    //UPLOAD image to bitmap

    //pick tanggal
    private void PickTanggal() {
        txttgllahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();

                final int tahun=calendar.get(calendar.YEAR);
                final int bulan=calendar.get(calendar.MONTH);
                final int hari=calendar.get(calendar.DATE);


                DatePickerDialog dialog=new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    public void onRadioButtonClicked(View view) {
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

    public void MethodUpdateData(View view) {
        //MethodUpdateRetrofit();
        //UpdateVoley();

        UpdateVoleyCi();


    }

    private void MethodUpdateRetrofit() {
        String nama=txnama.getText().toString();
        String tgllhr=txttgllahir.getText().toString();
        if(btnpria.isChecked()){
            jenkel="Laki - laki";
        }else {
            jenkel="Perempuan";
        }
        String jurusan=spinnerr.getSelectedItem().toString();
        String alamat=txtalamat.getText().toString();
        InterfcMhs request= RetroConfig.konekRetrofit().create(InterfcMhs.class);
        Call<ResponseMhs> dataUpdate=request.ardUpdateData(id,nama,tgllhr,jenkel,jurusan,alamat);

        dataUpdate.enqueue(new Callback<ResponseMhs>() {
            @Override
            public void onResponse(Call<ResponseMhs> call, Response<ResponseMhs> response) {
                int kode =response.body().getKode();
                String pesan =response.body().getPesan();
                Toast.makeText(UpdateActivity.this, "Berhasil di Update"+pesan +kode, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseMhs> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Gagal Update Data "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void MethodDeleteData(View view) {
        //DeletdenganRetrofit();
       // DeleteVolley();
        DeleteVolleyWithCi();
    }

    private void DeletdenganRetrofit() {
        InterfcMhs request= RetroConfig.konekRetrofit().create(InterfcMhs.class);
        Call<ResponseMhs> hapus=request.HapusDataMhs(id);

        hapus.enqueue(new Callback<ResponseMhs>() {
            @Override
            public void onResponse(Call<ResponseMhs> call, Response<ResponseMhs> response) {
                Toast.makeText(UpdateActivity.this, "Berhasil Menghapus", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseMhs> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Data gagal dihapus"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteVolley(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.DeleteUrlNtive, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                Toast.makeText(UpdateActivity.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                finish();

                Log.d(TAG, "onResponse: "+response);

             //   Toast.makeText(view.getContext(),"Successfully Deleted Data "+ listItem.getName(),Toast.LENGTH_LONG).show();
         //       DetailActivity.ma.refresh_list();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                dialog.hide();
//                dialog.dismiss();
            }
        }){
            protected HashMap<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("id", String.valueOf(id));
                //Log.d(TAG, "getParams: "+params);
                System.out.println(params);
                return (HashMap<String, String>) params;

            }
        };
        RequestHAndler.getInstance(UpdateActivity.this).addToRequestQueue(stringRequest);



    }

    private void DeleteVolleyWithCi(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.DeleteUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                Toast.makeText(UpdateActivity.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                finish();

                Log.d(TAG, "onResponse: "+response);

                //   Toast.makeText(view.getContext(),"Successfully Deleted Data "+ listItem.getName(),Toast.LENGTH_LONG).show();
                //       DetailActivity.ma.refresh_list();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                dialog.hide();
//                dialog.dismiss();
            }
        }){
            protected HashMap<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("id", String.valueOf(id));
                //Log.d(TAG, "getParams: "+params);
                System.out.println(params);
                return (HashMap<String, String>) params;

            }
        };
        RequestHAndler.getInstance(UpdateActivity.this).addToRequestQueue(stringRequest);



    }



    private void UpdateVoley(){
        String nama = txnama.getText().toString();
        String tgllhr = txttgllahir.getText().toString();
        if(btnpria.isChecked()){
            jenkel="Laki - laki";
        }else {
            jenkel="Perempuan";
        }
        String alamat = txtalamat.getText().toString();
        String jurusan = spinnerr.getSelectedItem().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UpdateUrlNtive, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                Toast.makeText(UpdateActivity.this, "Berhasil Di update", Toast.LENGTH_SHORT).show();
                finish();

                Log.d(TAG, "onResponse: "+response);

                //   Toast.makeText(view.getContext(),"Successfully Deleted Data "+ listItem.getName(),Toast.LENGTH_LONG).show();
                //       DetailActivity.ma.refresh_list();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                dialog.hide();
//                dialog.dismiss();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("nama", nama);
                params.put("tgl_lahir", tgllhr);
                params.put("jenis_kelamin", jenkel);
                params.put("jurusan",jurusan);
                params.put("alamat",alamat);
                System.out.println(params);
                return params;
            }
        };
        RequestHAndler.getInstance(UpdateActivity.this).addToRequestQueue(stringRequest);



    }

    private void UpdateVoleyCi(){
        String nama = txnama.getText().toString();
        String tgllhr = txttgllahir.getText().toString();
        if(btnpria.isChecked()){
            jenkel="Laki - laki";
        }else {
            jenkel="Perempuan";
        }
        String alamat = txtalamat.getText().toString();
        String jurusan = spinnerr.getSelectedItem().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UpdateUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                Toast.makeText(UpdateActivity.this, "Berhasil Di update", Toast.LENGTH_SHORT).show();
                finish();

                Log.d(TAG, "onResponse: "+response);

                //   Toast.makeText(view.getContext(),"Successfully Deleted Data "+ listItem.getName(),Toast.LENGTH_LONG).show();
                //       DetailActivity.ma.refresh_list();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                dialog.hide();
//                dialog.dismiss();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("nama", nama);
                params.put("tgl_lahir", tgllhr);
                params.put("jenis_kelamin", jenkel);
                params.put("jurusan",jurusan);
                params.put("alamat",alamat);
                System.out.println(params);
                return params;
            }
        };
        RequestHAndler.getInstance(UpdateActivity.this).addToRequestQueue(stringRequest);



    }




    }