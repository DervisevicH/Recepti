package com.example.recepti.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recepti.R;
import com.example.recepti.data.FavoritiVM;
import com.example.recepti.data.ReceptiVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyFragments;
import com.example.recepti.helper.MyRunnable;

public class ReceptiListKategorije extends Fragment {
    private int _kategorijaId;
    private int _korisnikId;
    private ListView lvRecepti;
    private ReceptiVM podaci;
    private FavoritiVM favoriti;
    private ReceptiVM.Row result;

    public ReceptiListKategorije(int kategorijaId,int korisnikId){_kategorijaId=kategorijaId;_korisnikId=korisnikId;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recepti_by_kategory,container,false);
        lvRecepti= view.findViewById(R.id.lvReceptiKategorije);



        bindRecepteTask();
        return view;
    }

    private void bindRecepteTask() {
        if(_kategorijaId!=0){
        MyApiRequest.get(getActivity(),"recepti/GetReceptiByKategorija/"+_kategorijaId, new MyRunnable<ReceptiVM>() {

            @Override

            public void run(ReceptiVM x) {

                podaci = x;
                bindRecepte();

            }

        });}
        else if(_korisnikId!=0){
            MyApiRequest.get(getActivity(),"favoriti/GetByKorisnik/"+_korisnikId, new MyRunnable<FavoritiVM>() {

                @Override

                public void run(FavoritiVM x) {

                    favoriti = x;
                    bindFavorite();

                }

            });
        }
    }

    private void bindFavorite() {
        lvRecepti.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return favoriti.rows.size();
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
                    view=inflater.inflate(R.layout.recepti_detalji,parent,false);
                }
                FavoritiVM.Row x=favoriti.rows.get(position);
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
        });
        lvRecepti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavoritiVM.Row x=favoriti.rows.get(position);

                MyApiRequest.get(getActivity(),"recepti/GetById/"+x.receptId, new MyRunnable<ReceptiVM.Row>() {

                    @Override

                    public void run(ReceptiVM.Row x) {

                        result = x;
                        MyFragments.openAsReplace(getActivity(),R.id.mjestoZaFragment,new ReceptPregled(result));


                    }

                });


            }
        });
    }

    private void bindRecepte() {
        lvRecepti.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return podaci.rows.size();
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
                    view=inflater.inflate(R.layout.recepti_detalji,parent,false);
                }
                ReceptiVM.Row x=podaci.rows.get(position);
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
        });

        lvRecepti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReceptiVM.Row x=podaci.rows.get(position);

                MyFragments.openAsReplace(getActivity(),R.id.mjestoZaFragment,new ReceptPregled(x));

            }
        });
    }
    }

