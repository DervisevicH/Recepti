package com.example.recepti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recepti.data.AutentifikacijaLoginPostVM;
import com.example.recepti.data.AutentifikacijaResultVM;
import com.example.recepti.data.KorisniciAddVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyRunnable;
import com.example.recepti.helper.MySession;
import com.google.android.material.snackbar.Snackbar;

public class Registracija extends AppCompatActivity {

    private EditText _txtUsername;
    private EditText _txtLozinka;
    private EditText _txtLozinkaRetype;
    private EditText _txtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        _txtUsername = findViewById(R.id.inputUsername);
        _txtLozinka = findViewById(R.id.inputPassword);
        _txtLozinkaRetype = findViewById(R.id.inputRetypePassword);
        _txtMail = findViewById(R.id.inputEmail);

        Button btnRegistrujSe = findViewById(R.id.btnRegistrujSe);
        btnRegistrujSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnRegistrujSe_Click();
            }
        });
    }

    private void do_btnRegistrujSe_Click() {
        if (validacija()) {
            KorisniciAddVM model = new KorisniciAddVM(_txtUsername.getText().toString(), _txtLozinka.getText().toString(), _txtMail.getText().toString());

            MyApiRequest.post(this, "Korisnici/Insert", model, new MyRunnable<AutentifikacijaResultVM>() {
                @Override
                public void run(AutentifikacijaResultVM x) {
                    zapocniSesiju(x);
                }
            });
        }
    }

    private boolean validacija() {
        View parentLayout = findViewById(android.R.id.content);
        if (TextUtils.isEmpty(_txtUsername.getText().toString()) ||
                TextUtils.isEmpty(_txtLozinka.getText().toString()) ||
                TextUtils.isEmpty(_txtLozinkaRetype.getText().toString()) ||
                TextUtils.isEmpty(_txtMail.getText().toString())
        ) {
            Snackbar.make(parentLayout, "Potrebno je unijeti sva polja", Snackbar.LENGTH_LONG).show();
            return false;
        } else {
            if (_txtLozinka.getText().toString().trim().matches(_txtLozinkaRetype.getText().toString().trim())) {
                return true;
            } else {
                Snackbar.make(parentLayout, "Niste unijeli iste lozinke", Snackbar.LENGTH_LONG).show();
                return false;
            }
        }

    }

    private void zapocniSesiju(AutentifikacijaResultVM x) {
        MySession.setKorisnik(x);
        startActivity(new Intent(this, GlavniActivity.class));
    }

}
