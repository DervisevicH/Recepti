package com.example.recepti.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recepti.R;
import com.example.recepti.data.KategorijeVM;
import com.example.recepti.data.KomentariVM;
import com.example.recepti.data.ReceptiVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyFragments;
import com.example.recepti.helper.MyRunnable;

public class PrikazPoKategorijama extends Fragment {
    private KategorijeVM podaci;
    private ListView lvKategorije;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prikaz_kategorija, container, false);
         lvKategorije = view.findViewById(R.id.lvKategorije);
        BindTaskPodaci();
        return view;
    }

    private void BindTaskPodaci() {
        MyApiRequest.get(getActivity(), "kategorije/GetAll" , new MyRunnable<KategorijeVM>() {
            @Override

            public void run(KategorijeVM x) {
                podaci = x;
                BindKategorije();
            }
        });
    }

    private void BindKategorije() {
        lvKategorije.setAdapter(new BaseAdapter() {
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
                if (view == null) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    view = inflater.inflate(R.layout.kategorije_detalji, parent, false);

                }

                TextView txtFirstLine = view.findViewById(R.id.txtNazivKategorije);


                KategorijeVM.Row x = podaci.rows.get(position);
                txtFirstLine.setText(x.naziv);


                return view;
            }
        });
        lvKategorije.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KategorijeVM.Row x=podaci.rows.get(position);

                MyFragments.openAsReplace(getActivity(),R.id.mjestoZaFragment,new ReceptiListKategorije(x.kategorijaId,0));

            }
        });
    }
}
