package com.kubangkangkung.mahasiswa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kubangkangkung.mahasiswa.Adapter.AdapterMhs;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.R;

import org.parceler.Parcels;

import static com.kubangkangkung.mahasiswa.Adapter.AdapterMhs.DATA_MHS;

public class DetailActivity extends AppCompatActivity {
    private ModelMhs datamhs;
    private TextView txtid,txtnama,txttgllahir,txtjenkel,txtjurusan,txtalamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtid=findViewById(R.id.det_id);
        txtnama=findViewById(R.id.det_nama);
        txttgllahir=findViewById(R.id.det_tgllahir);
        txtjenkel=findViewById(R.id.detjenkel);
        txtjurusan=findViewById(R.id.det_jur);
        txtalamat=findViewById(R.id.det_alamat);

        Bundle bundle=getIntent().getBundleExtra(AdapterMhs.DATA_EXTRA);
        datamhs = Parcels.unwrap(bundle.getParcelable(DATA_MHS));

        txtnama.setText(datamhs.getNama());
        txttgllahir.setText(datamhs.getTgl_lahir());
        txtjenkel.setText(datamhs.getJenis_kelamin());
        txtjurusan.setText(datamhs.getJurusan());
        txtalamat.setText(datamhs.getAlamat());



    }

    public void EditDAta(View view) {
//        Intent pindah=new Intent(DetailActivity.this, UpdateActivity.class);
//                pindah.putExtra(DetailActivity.KEY_ID,datamhs.getId());
//                pindah.putExtra(UpdateActivity.KEY_NAMA,listdata.get(position).getNama());
//                pindah.putExtra(UpdateActivity.KEY_ALAMAT,listdata.get(position).getAlamat());
//                pindah.putExtra(UpdateActivity.KEY_TELEPON,listdata.get(position).getTelepon());
//
//        ctx.startActivity(pindah);
    }

}