package com.example.atvd.entities;

import javax.persistence.*;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column(length = 50, nullable = false)
    private String nome;
    @Column(nullable = false)
    private Integer preco;
    @Column(nullable = false)

    private Integer aliquota;


    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public void setAliquota(Integer aliquota) {
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
