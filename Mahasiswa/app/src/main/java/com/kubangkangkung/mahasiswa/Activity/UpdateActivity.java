package com.kubangkangkung.mahasiswa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.kubangkangkung.mahasiswa.Adapter.AdapterMhs;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.Model.ResponseMhs;
import com.kubangkangkung.mahasiswa.R;
import com.kubangkangkung.mahasiswa.Retrofit.InterfcMhs;
import com.kubangkangkung.mahasiswa.Retrofit.RetroConfig;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_ALAMAT;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_ID;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_JENISKEL;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_JURUSAN;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_NAMA;
import static com.kubangkangkung.mahasiswa.Activity.DetailActivity.KEY_TGLLHR;

public class UpdateActivity extends AppCompatActivity {
    private static final String TAG = "UpdateActivity";
    int id;
    ArrayAdapter adapter;
    RadioButton btnpria,btncewe;
    Spinner spinnerr;
    String jenkel;
    EditText txnama,txttgllahir,txtalamat;
    String [] pilihanjurusan={"Teknik Informatika","Sistem Informasi","Rekayasa Perangkat Lunak"};
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
                startActivity(new Intent(UpdateActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseMhs> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Gagal Update Data "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void MethodDeleteData(View view) {
        InterfcMhs request= RetroConfig.konekRetrofit().create(InterfcMhs.class);
        Call<ResponseMhs> hapus=request.HapusDataMhs(id);

        hapus.enqueue(new Callback<ResponseMhs>() {
            @Override
            public void onResponse(Call<ResponseMhs> call, Response<ResponseMhs> response) {
                Toast.makeText(UpdateActivity.this, "Berhasil Menghapus", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseMhs> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Data gagal dihapus"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}