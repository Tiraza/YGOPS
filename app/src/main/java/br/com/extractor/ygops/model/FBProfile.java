package br.com.extractor.ygops.model;

/**
 * Created by muryllo.santos on 01/06/2016.
 */
public class FBProfile {

    private String nome;
    private String email;
    private int totalVitorias;
    private int totalDerrotas;

    public FBProfile() {}

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalVitorias() {
        return totalVitorias;
    }
    public void setTotalVitorias(int totalVitorias) {
        this.totalVitorias = totalVitorias;
    }

    public int getTotalDerrotas() {
        return totalDerrotas;
    }
    public void setTotalDerrotas(int totalDerrotas) {
        this.totalDerrotas = totalDerrotas;
    }
}
