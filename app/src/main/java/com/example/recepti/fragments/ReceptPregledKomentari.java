package com.example.recepti.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recepti.R;
import com.example.recepti.data.AutentifikacijaResultVM;
import com.example.recepti.data.KomentarAddVM;
import com.example.recepti.data.KomentariVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyRunnable;
import com.example.recepti.helper.MySession;

public class ReceptPregledKomentari extends Fragment {

    private EditText txtKomentar;

    public ReceptPregledKomentari(int receptId) {
        _receptId = receptId;
    }

    private ListView lvKomentari;
    private int _receptId;
    private KomentariVM _komentari;
    private BaseAdapter _adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recept_komentari, container, false);

        lvKomentari = view.findViewById(R.id.lvKomentari);
        Button btnDodajKomentar = view.findViewById(R.id.btnDodajKomentar);
        txtKomentar = view.findViewById(R.id.inputKomentar);

        btnDodajKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_dodajKomentarClick();
            }
        });
        BindTaskKomentare();


        return view;
    }

    private void do_dodajKomentarClick() {

        KomentarAddVM model = new KomentarAddVM(MySession.getKorisnik().korisnikId, txtKomentar.getText().toString(), _receptId);
        try {
            MyApiRequest.post(getActivity(), "Komentari/Insert", model, new MyRunnable<KomentariVM>() {
                @Override

                public void run(KomentariVM x) {

                    _komentari = x;
                    _adapter.notifyDataSetChanged();

                }
            });

        } catch (Exception e) {

        }

    }

    private void BindTaskKomentare() {
        MyApiRequest.get(getActivity(), "komentari/GetKomentariByRecept/" + _receptId, new MyRunnable<KomentariVM>() {
            @Override

            public void run(KomentariVM x) {
                _komentari = x;
                BindKomentare();
            }
        });
    }

    private void BindKomentare() {
        _adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return _komentari.rows.size();
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
                if (view == null) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    view = inflater.inflate(R.layout.komentari_detalji, parent, false);

                }

                TextView txtFirstLine = view.findViewById(R.id.txtUsername);
                TextView txtSecondLine = view.findViewById(R.id.txtKomentar);
                TextView txtDatum=view.findViewById(R.id.txtDatumObjave);


                KomentariVM.Row x = _komentari.rows.get(position);
                txtFirstLine.setText(x.korisnik);
                txtSecondLine.setText(x.komentar);
                txtDatum.setText(x.datumObjave);


                return view;
            }
        };
        lvKomentari.setAdapter(_adapter);
    }
}
