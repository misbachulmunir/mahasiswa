package com.kubangkangkung.mahasiswa.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kubangkangkung.mahasiswa.Adapter.AdapterMhs;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.R;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import static com.kubangkangkung.mahasiswa.Adapter.AdapterMhs.DATA_MHS;

public class DetailActivity extends AppCompatActivity {
    public static final String KEY_ID = "key_id";
    public static final String KEY_NAMA = "key_nama";
    public static final String KEY_ALAMAT = "key_alamat";
    public static final String KEY_JURUSAN = "Key_jurusan";
    public static final String KEY_TGLLHR = "key_tgllhr";
    public static final String KEY_JENISKEL = "key_jenkel";
    private ModelMhs datamhs;
    private TextView txtid,txtnama,txttgllahir,txtjenkel,txtjurusan,txtalamat;
    ImageView imageview;
    private static final String TAG = "DetailActivity";

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
        imageview=findViewById(R.id.id_gambar_main);


        Bundle bundle=getIntent().getBundleExtra(AdapterMhs.DATA_EXTRA);
        datamhs = Parcels.unwrap(bundle.getParcelable(DATA_MHS));
        txtid.setText(String.valueOf(datamhs.getId()));
        txtnama.setText(datamhs.getNama());
        txttgllahir.setText(datamhs.getTgl_lahir());
        txtjenkel.setText(datamhs.getJenis_kelamin());
        txtjurusan.setText(datamhs.getJurusan());
        txtalamat.setText(datamhs.getAlamat());
try {
    Picasso.get().load("http://192.168.133.113/rest-api/mahasiswa/assets/img/logo/indonesia.png").into(imageview);
}catch (Exception e){
    Log.d(TAG, "onCreate: "+e.getMessage());
System.out.println(e);
}




    }

    public void EditDAta(View view) {
        Intent pindah=new Intent(DetailActivity.this, UpdateActivity.class);
                pindah.putExtra(KEY_ID,txtid.getText());
                pindah.putExtra(KEY_NAMA,txtnama.getText());
                pindah.putExtra(KEY_ALAMAT,txtalamat.getText());
                pindah.putExtra(KEY_TGLLHR,txttgllahir.getText());
                pindah.putExtra(KEY_JENISKEL,txtjenkel.getText());
                pindah.putExtra(KEY_JURUSAN,txtjurusan.getText());
        startActivity(pindah);
    }

//upload image to server ci
    private void UploadImage(Uri imageUrl){
        final ProgressDialog loding=new ProgressDialog(DetailActivity.this);
        loding.setMessage("Tunggu Sebentar...");
        loding.show();

    }

}