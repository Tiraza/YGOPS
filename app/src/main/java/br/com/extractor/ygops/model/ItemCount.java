package br.com.extractor.ygops.model;

/**
 * Created by Muryllo Tiraza on 12/02/2016.
 */
public class ItemCount {

    private String nome;
    private Integer quantidade;

    public ItemCount() {}

    public ItemCount(String nome, Integer quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
