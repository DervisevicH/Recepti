package com.example.recepti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.recepti.data.AutentifikacijaResultVM;
import com.example.recepti.helper.MySession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AutentifikacijaResultVM korisnik= MySession.getKorisnik();
       if(korisnik==null)
          startActivity(new Intent(this,LoginActivity.class));
        else
            startActivity(new Intent(this,GlavniActivity.class));
    }
}
