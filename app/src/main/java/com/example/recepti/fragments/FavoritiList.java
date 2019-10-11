package com.example.recepti.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recepti.R;
import com.example.recepti.data.FavoritiVM;
import com.example.recepti.data.ReceptiVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyRunnable;
import com.example.recepti.helper.MySession;

public class FavoritiList extends Fragment {
    private BaseAdapter _adapter;
    private ListView _lvFavoriti;
    private int _korisnikId;
    private FavoritiVM _podaci;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favoriti_list,container,false);
         _lvFavoriti= view.findViewById(R.id.lvFavoriti);
         BindFavoriteTask();
        return view;
    }

    private void BindFavoriteTask() {

        MyApiRequest.get(getActivity(), "/Favoriti/GetByKorisnik/" + MySession.getKorisnik().korisnikId, new MyRunnable<FavoritiVM>() {

            @Override

            public void run(FavoritiVM x) {

                _podaci = x;

                BindFavorite();

            }

        });
    }

    private void BindFavorite() {
        _adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return _podaci.rows.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if(view==null){
                    LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.favoriti_detalji,parent,false);
                }
                FavoritiVM.Row x=_podaci.rows.get(position);
                TextView txtNaziv=view.findViewById(R.id.txtNaziv);
                TextView txtOpis=view.findViewById(R.id.txtOpis);
                ImageView slika=view.findViewById(R.id.slikaMjesto);

                txtNaziv.setText(x.naziv);
                txtOpis.setText(x.opis);


                byte[] decodedString = Base64.decode(x.slika, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                slika.setImageBitmap(decodedByte);
                return view;
            }
        };
        _lvFavoriti.setAdapter(_adapter);
    }

}
