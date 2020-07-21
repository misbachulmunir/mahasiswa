package com.kubangkangkung.mahasiswa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.kubangkangkung.mahasiswa.Adapter.AdapterMhs;
import com.kubangkangkung.mahasiswa.Model.ModelMhs;
import com.kubangkangkung.mahasiswa.R;

import org.parceler.Parcels;

public class UpdateActivity extends AppCompatActivity {
    private ModelMhs datamhs;
    Spinner spinnerr;
    String jenkel;
    EditText txnama,txttgllahir,txtalamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        txnama=findViewById(R.id.up_nama);
        txttgllahir=findViewById(R.id.up_tgllhr);
        txtalamat=findViewById(R.id.up_alamat);

        Bundle bundle=getIntent().getBundleExtra(AdapterMhs.DATA_EXTRA);
        datamhs = Parcels.unwrap(bundle.getParcelable(AdapterMhs.DATA_MHS));
        txnama.setText(datamhs.getNama());
        txttgllahir.setText(datamhs.getTgl_lahir());
        txtalamat.setText(datamhs.getAlamat());
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
}