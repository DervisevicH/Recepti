package com.example.recepti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recepti.R;
import com.example.recepti.data.ReceptiVM;

public class ReceptPregledSastojci  extends Fragment {
    private ReceptiVM.Row _recept;
    public ReceptPregledSastojci(ReceptiVM.Row recept){
        _recept=recept;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recept_sastojci,container,false);
        TextView txtSastojci=view.findViewById(R.id.txtSastojci);
        txtSastojci.setText(_recept.sastojci);
        return view;
    }
}
