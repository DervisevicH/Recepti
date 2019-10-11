package com.example.recepti.data;

import java.io.Serializable;

public class FavoritiAddVM implements Serializable {
    int korisnikId;
    int receptId;
    public FavoritiAddVM(int korisnikId,int receptId){
        this.korisnikId=korisnikId;this.receptId=receptId;
    }
}
