package com.example.recepti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recepti.data.AutentifikacijaLoginPostVM;
import com.example.recepti.data.AutentifikacijaResultVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyRunnable;
import com.example.recepti.helper.MySession;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private EditText _txtKorisnickoIme;
    private EditText _txtLozinka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _txtKorisnickoIme = findViewById(R.id.txtKorisnickoIme);
        _txtLozinka = findViewById(R.id.txtLozinka);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegistracija);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnLoginClick();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnRegistracijaClick();
            }
        });
    }

    private void do_btnRegistracijaClick() {
        startActivity(new Intent(this, Registracija.class));
    }

    private void do_btnLoginClick() {

        String Username = _txtKorisnickoIme.getText().toString();
        String Password = _txtLozinka.getText().toString();

        if (validacija()) {
            AutentifikacijaLoginPostVM model = new AutentifikacijaLoginPostVM(Username, Password);

            MyApiRequest.post(this, "Autentifikacija/LoginCheck", model, new MyRunnable<AutentifikacijaResultVM>() {
                @Override
                public void run(AutentifikacijaResultVM x) {
                    ProvjeriLogin(x);
                }
            });
        } else {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Unesite korisničko ime i lozinku", Snackbar.LENGTH_LONG).show();
        }
    }

    private void ProvjeriLogin(AutentifikacijaResultVM x) {
        if (x == null) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Pogrešno korisničko ime/lozinka", Snackbar.LENGTH_LONG).show();
        } else {
            MySession.setKorisnik(x);
            startActivity(new Intent(this, GlavniActivity.class));
        }
    }

    private boolean validacija() {

        if (TextUtils.isEmpty(_txtKorisnickoIme.getText().toString()) || TextUtils.isEmpty(_txtLozinka.getText().toString())) {
            return false;
        } else
            return true;
    }
}
