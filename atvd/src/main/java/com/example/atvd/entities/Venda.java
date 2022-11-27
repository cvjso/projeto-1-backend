package com.example.atvd.entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(length = 50, nullable = false)
    private long produtoId;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Integer precoFinal;

    @Column(nullable = false)
    private Integer impostoPago;

    private long cupomId;

    public static ArrayList<String> getFields() {
        ArrayList<String> result = new ArrayList<String>();
        result.add("id");
        result.add("produtoId");
        result.add("quantidade");
        result.add("precoFinal");
        result.add("impostoPago");
        result.add("cupomId");
        return result;
    }

    public void setCupomId(long cupomId) {
        this.cupomId = cupomId;
    }

    public long getCupomId() {
        return cupomId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImpostoPago(Integer impostoPago) {
        this.impostoPago = impostoPago;
    }

    public void setPrecoFinal(Integer precoFinal) {
        this.precoFinal = precoFinal;
    }

    public void setProdutoId(long produtoId) {
        this.produtoId = produtoId;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public Integer getImpostoPago() {
        return impostoPago;
    }

    public Integer getPrecoFinal() {
        return precoFinal;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public long getProdutoId() {
        return produtoId;
    }
}
