package br.com.extractor.ygops.model;

/**
 * Created by Muryllo Tiraza on 12/02/2016.
 */
public class ItemCount implements Comparable<ItemCount> {

    private String nome;
    private Integer quantidade;
    private Integer color;

    public ItemCount() {
    }

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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    @Override
    public int compareTo(ItemCount another) {
        if (this.quantidade < another.quantidade) {
            return -1;
        } else if (this.quantidade > another.quantidade) {
            return 1;
        }

        return 0;
    }
}
