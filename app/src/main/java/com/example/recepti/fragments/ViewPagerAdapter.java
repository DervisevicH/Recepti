package com.example.recepti.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.recepti.data.ReceptiVM;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] childFragments;
    String [] titlesArray =
            new String []{
                    "Sastojci",
                    "Opis",
                    "Komentari"
            };



    public ViewPagerAdapter(FragmentManager fm, ReceptiVM.Row recept) {

        super(fm);

        childFragments = new Fragment[] {

                new ReceptPregledSastojci(recept),
                new ReceptPregledOpis(recept),
                new ReceptPregledKomentari(recept.receptId)
        };

    }



    @Override

    public Fragment getItem(int position) {

        return childFragments[position];

    }



    @Override

    public int getCount() {

        return childFragments.length; //3 items

    }



    @Override

    public CharSequence getPageTitle(int position) {

        return titlesArray [position];



    }

}
