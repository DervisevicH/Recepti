package com.example.recepti.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.recepti.R;
import com.example.recepti.data.FavoritiAddVM;
import com.example.recepti.data.FavoritiVM;
import com.example.recepti.data.ReceptiVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyRunnable;
import com.example.recepti.helper.MySession;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class ReceptPregled extends Fragment {
    private ReceptiVM.Row _recept;
    private FloatingActionButton fabAdd;
    private MyRunnable<FavoritiVM.Row> callBack;
    private FavoritiVM.Row favorit = null;
    private boolean isAdd;

    public ReceptPregled(ReceptiVM.Row recept) {
        _recept = recept;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recept, container, false);

        TextView txtNaziv = view.findViewById(R.id.txtNaziv);
        txtNaziv.setText(_recept.naziv);
        TextView txtVrijemeKuhanja = view.findViewById(R.id.txtVrijemeKuhanja);
        txtVrijemeKuhanja.setText("Vrijeme kuhanja: " + _recept.vrijemeKuhanja);
        TextView txtLevel = view.findViewById(R.id.txtLevel);
        txtLevel.setText("Level: " + _recept.level);

        ImageView slika = view.findViewById(R.id.imgSlika);

        byte[] decodedString = Base64.decode(_recept.slika, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        slika.setImageBitmap(decodedByte);

        fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_fab_click();
            }
        });
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), _recept));

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    private void do_fab_click() {
        FavoritiAddVM newFavorit = new FavoritiAddVM(MySession.getKorisnik().korisnikId, _recept.receptId);

        try {
            MyApiRequest.post(getActivity(), "favoriti/Insert", newFavorit, new MyRunnable<Boolean>() {


                @Override

                public void run(Boolean o) {

                    isAdd = o;
                    if (isAdd) {
                        View parentLayout = getActivity().findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Dodali ste recept u favorite", Snackbar.LENGTH_LONG).show();
                    } else if (isAdd == false) {
                        View parentLayout = getActivity().findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Recept već postoji u favoritima", Snackbar.LENGTH_LONG).show();
                    }

                }

            });


        } catch (Exception e) {
            Toast.makeText(getActivity(), "Greška iz fragmenta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
