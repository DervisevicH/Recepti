package com.example.recepti.data;

import java.util.Date;
import java.util.List;

public class KomentariVM {
    public class Row
    {
        public int komentarId;
        public String komentar;
        public int receptId;
        public String korisnik;
        public String datumObjave;
    }

    public List<Row> rows;
}
