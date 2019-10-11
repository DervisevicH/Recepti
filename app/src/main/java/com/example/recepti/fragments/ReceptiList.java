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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recepti.R;
import com.example.recepti.data.ReceptiVM;
import com.example.recepti.helper.MyApiRequest;
import com.example.recepti.helper.MyFragments;
import com.example.recepti.helper.MyRunnable;

public class ReceptiList extends Fragment {
    private ReceptiVM podaci;
    private RecyclerView rv;
    private SearchView searchView;

    public static ReceptiList newInstance(){return new ReceptiList();}

    private ListView lvRecepti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recepti_list,container,false);
         lvRecepti= view.findViewById(R.id.lvRecepti);
         searchView = view.findViewById(R.id.txtPretraga);
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override

            public boolean onQueryTextSubmit(String query) {
                bindRecepteTask(query);
                return false;
            }

            @Override

            public boolean onQueryTextChange(String query) {

                bindRecepteTask(query);

                return false;

            }

        });

        searchView.setIconifiedByDefault(false);

         bindRecepteTask("");
         return view;

    }



    private void  bindRecepteTask(String query){
        if(query.equals("")){
            MyApiRequest.get(getActivity(),"recepti/index", new MyRunnable<ReceptiVM>() {

                @Override

                public void run(ReceptiVM x) {

                    podaci = x;
                    bindRecepte();

                }

            });
        }
        else{
            MyApiRequest.get(getActivity(),"recepti/GetReceptByNaziv/"+query, new MyRunnable<ReceptiVM>() {

            @Override

            public void run(ReceptiVM x) {

                podaci = x;
                bindRecepte();

            }

        });}




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
