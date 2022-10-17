package com.example.atvd.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Cupom {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(length = 50, nullable = false)
    private Date dataVenda;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String status;

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getStatus() {
        return status;
    }
}
