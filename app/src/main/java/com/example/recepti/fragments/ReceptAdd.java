package com.example.recepti.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recepti.R;
import com.example.recepti.data.KategorijeVM;
import com.example.recepti.data.ReceptAddVM;
import com.example.recepti.data.ReceptiVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyFragments;
import com.example.recepti.helper.MyRunnable;
import com.example.recepti.helper.MySession;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ReceptAdd extends Fragment {

    private KategorijeVM _podaci;
    private Spinner _spinerKategorije;
    private EditText txtNaziv;
    private EditText txtOpis;
    private EditText txtSastojci;
    private EditText txtVrijemeKuhanja;
    private Button btnDodajRecept;
    private RadioGroup radioGroup;
    private RadioButton oznaceniRadio;
    private ImageView imgSlika;
    private Button btnDodajSliku;
    private int Upload_slike_request = 1;
    private String slika;

    private int PICK_IMAGE_REQUEST = 1;
    private ReceptiVM.Row result;
    private KategorijeVM.Row odabranaKategorija;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recept_add, container, false);


        _spinerKategorije = view.findViewById(R.id.spinerKategorija);
        txtNaziv = view.findViewById(R.id.inputNaziv);
        txtOpis = view.findViewById(R.id.inputOpisRecepta);
        txtSastojci = view.findViewById(R.id.inputSastojci);
        txtVrijemeKuhanja = view.findViewById(R.id.inputVrijemeKuhanja);
        radioGroup = view.findViewById(R.id.radioGroup);
        imgSlika = view.findViewById(R.id.imgView);
        btnDodajSliku = view.findViewById(R.id.btnDodajSliku);
        radioGroup.clearCheck();
        btnDodajRecept = view.findViewById(R.id.btnDodajRecept);

        btnDodajRecept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btn_dodaj_click();
            }
        });
        btnDodajSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajSliku();
            }
        });

        // Add the Listener to the RadioGroup
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override

                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        // Get the selected Radio Button
                        RadioButton
                                radioButton
                                = (RadioButton) group
                                .findViewById(checkedId);
                    }
                });

        popuniKategorijeTask();
        return view;
    }

    private void dodajSliku() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Odaberi sliku"), Upload_slike_request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imgSlika.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                slika = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void do_btn_dodaj_click() {
        int position = _spinerKategorije.getSelectedItemPosition();

        for (KategorijeVM.Row item : _podaci.rows) {
            if(position==item.kategorijaId)
                odabranaKategorija=item;
        }

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getContext(),
                    "Odaberite level",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {

            oznaceniRadio
                    = (RadioButton) radioGroup
                    .findViewById(selectedId);
        }
        if(validacija()) {

            ReceptAddVM newRecept = new ReceptAddVM(txtNaziv.getText().toString(), txtSastojci.getText().toString(), txtOpis.getText().toString(), oznaceniRadio.getText().toString(),odabranaKategorija.kategorijaId, txtVrijemeKuhanja.getText().toString(), MySession.getKorisnik().korisnikId, slika);
            MyApiRequest.post(getActivity(), "Recepti/Insert", newRecept, new MyRunnable<ReceptiVM.Row>(){
                @Override

                public void run(ReceptiVM.Row x) {

                    result = x;
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Uspješno ste sačuvali recept", Snackbar.LENGTH_LONG).show();
                    MyFragments.openAsReplace(getActivity(),R.id.mjestoZaFragment, new ReceptAdd());


                }

            });
        }
        else{
            View parentLayout = getActivity().findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Unesite sva polja", Snackbar.LENGTH_LONG).show();
        }

    }

    private boolean validacija() {
        boolean isValidan=true;
        if(TextUtils.isEmpty(txtNaziv.getText().toString()) ||
        TextUtils.isEmpty(txtSastojci.getText().toString()) ||
                TextUtils.isEmpty(txtOpis.getText().toString()) ||
        TextUtils.isEmpty(txtVrijemeKuhanja.getText().toString())){ isValidan=false;}

        return isValidan;
    }

    private void popuniKategorijeTask() {
        MyApiRequest.get(getActivity(), "Kategorije/GetAll", new MyRunnable<KategorijeVM>() {
            @Override
            public void run(KategorijeVM x) {
                _podaci = x;
                popuniPodatke();
            }
        });
    }

    private void popuniPodatke() {
        List<String> result = new ArrayList<>();
        result.add(0, "Odaberite kategoriju");
        for (KategorijeVM.Row x : _podaci.rows) {
            result.add(x.naziv);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, result);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        _spinerKategorije.setAdapter(dataAdapter);

    }

}
