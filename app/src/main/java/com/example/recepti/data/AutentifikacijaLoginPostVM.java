package com.example.recepti.data;

import java.io.Serializable;

public class AutentifikacijaLoginPostVM implements Serializable {
    public String username ;
    public String password ;
    public AutentifikacijaLoginPostVM(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
