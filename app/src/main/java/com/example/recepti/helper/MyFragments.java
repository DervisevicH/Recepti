package com.example.recepti.helper;



import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyFragments {


    public static void openAsReplace(FragmentActivity activity, int id, Fragment fragment){
        FragmentManager fragmentManager=activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public static void openAsDialog(FragmentActivity activity, DialogFragment dlg) {

        FragmentManager fm = activity.getSupportFragmentManager();

        dlg.show(fm, "nekitag");

    }
}
