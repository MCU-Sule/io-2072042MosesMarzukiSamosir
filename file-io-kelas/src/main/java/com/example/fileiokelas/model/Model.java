package com.example.fileiokelas.model;

public class Model {
    private String username;
    private String komentar;

    @Override
    public String toString() {
        return username + " - " + komentar;
    }

    public Model(String username, String komentar) {
        this.username = username;
        this.komentar = komentar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
