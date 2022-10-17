package com.example.atvd.repository;

import com.example.atvd.entities.Produto;
import com.example.atvd.entities.Venda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long> {
}
