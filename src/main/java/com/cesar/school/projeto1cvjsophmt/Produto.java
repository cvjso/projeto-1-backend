package com.cesar.school.projeto1cvjsophmt;

public class Produto {
    private final long id;
    private final String nome;
    private final Integer preco;
    private final Integer aliquota;

    public Produto(long id, String nome, Integer preco, Integer aliquota) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.aliquota = aliquota;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getPreco() {
        return preco;
    }

    public Integer getAliquota() {
        return aliquota;
    }
}
