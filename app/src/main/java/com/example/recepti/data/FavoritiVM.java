package com.example.recepti.data;

import java.io.Serializable;
import java.util.List;

public class FavoritiVM implements Serializable {
    public class Row
    {
        public int favoritId;
        public int receptId;
        public int korisnikId;
        public String naziv;
        public String vrijemeKuhanja;
        public String sastojci;
        public String opis;
        public String slika;
        public String level;
    }

    public List<Row> rows;
}
