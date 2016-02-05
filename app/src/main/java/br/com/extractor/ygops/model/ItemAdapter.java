package br.com.extractor.ygops.model;

import java.io.Serializable;

/**
 * Created by Muryllo Tiraza on 05/02/2016.
 */
public class ItemAdapter implements Serializable {

    private String nome;
    private Integer color;

    public ItemAdapter() {}

    public ItemAdapter(String nome, Integer color) {
        this.nome = nome;
        this.color = color;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getColor() {
        return color;
    }
    public void setColor(Integer color) {
        this.color = color;
    }
}
