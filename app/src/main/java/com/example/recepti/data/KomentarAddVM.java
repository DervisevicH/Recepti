package com.example.recepti.data;

import java.util.Date;

public class KomentarAddVM {
    int korisnikId;
    String komentar;
    int receptId;
    Date datumObjave;

    public KomentarAddVM(int korisnikId,String komentar,int receptId){
        this.korisnikId=korisnikId;this.komentar=komentar;this.receptId=receptId;
    }
}
