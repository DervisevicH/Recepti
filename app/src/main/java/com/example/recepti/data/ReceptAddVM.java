package com.example.recepti.data;

public class ReceptAddVM {
    public String naziv;
    public String sastojci;
    public String opis;
    public String level;
    public Integer kategorijaId;
    public String vrijemeKuhanja;
    public Integer korisnikId;
    public String slika;


    public ReceptAddVM(String naziv, String sastojci, String opis, String level, Integer kategorijaId, String vrijemeKuhanja,Integer korisnikId,String slika) {
        this.naziv = naziv;
        this.sastojci = sastojci;
        this.opis = opis;
        this.level = level;
        this.kategorijaId = kategorijaId;
        this.vrijemeKuhanja = vrijemeKuhanja;
        this.korisnikId=korisnikId;
        this.slika=slika;
    }
}
