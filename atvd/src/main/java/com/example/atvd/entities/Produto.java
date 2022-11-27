package com.example.atvd.entities;

import javax.persistence.*;
import java.util.ArrayList;

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
    private Double aliquota;
    @Column(nullable = false)
    private Long codigo;

    public static ArrayList<String> getFields() {
        ArrayList<String> result = new ArrayList<String>();
        result.add("id");
        result.add("nome");
        result.add("preco");
        result.add("aliquota");
        result.add("codigo");
        return result;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public void setAliquota(double aliquota) {
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

    public Double getAliquota() {
        return aliquota;
    }
}
