package com.example.recepti.data;

import java.io.Serializable;
import java.util.List;

public class ReceptiVM implements Serializable{
    public static class Row implements Serializable
    {
        public int receptId;
        public String naziv;
        public String vrijemeKuhanja;
        public String sastojci;
        public String opis;
        public String slika;
        public String level;
    }
    public List<Row> rows;
}
