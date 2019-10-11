package com.example.recepti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.recepti.fragments.FavoritiList;
import com.example.recepti.fragments.PrikazPoKategorijama;
import com.example.recepti.fragments.ReceptAdd;
import com.example.recepti.fragments.ReceptiList;
import com.example.recepti.fragments.ReceptiListKategorije;
import com.example.recepti.helper.MyFragments;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.recepti.helper.MySession;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class GlavniActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        MyFragments.openAsReplace(this,R.id.mjestoZaFragment, new ReceptiList());



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.glavni, menu);
        TextView txtUsername=(TextView) findViewById(R.id.username);
        txtUsername.setText(MySession.getKorisnik().username);
        TextView txtmail=(TextView) findViewById(R.id.textView);
        txtmail.setText(MySession.getKorisnik().email);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            MyFragments.openAsReplace(this,R.id.mjestoZaFragment, new ReceptiList());

        } else if (id == R.id.nav_gallery) {
            MyFragments.openAsReplace(this,R.id.mjestoZaFragment,new FavoritiList());

        } else if (id == R.id.nav_slideshow) {
            MyFragments.openAsReplace(this,R.id.mjestoZaFragment,new PrikazPoKategorijama());

        } else if (id == R.id.nav_tools) {
            MyFragments.openAsReplace(this,R.id.mjestoZaFragment,new ReceptAdd());

        }  else if (id == R.id.nav_send) {
            SharedPreferences preferences =getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("Key_korisnik");
            editor.apply();

            startActivity(new Intent(this, LoginActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
