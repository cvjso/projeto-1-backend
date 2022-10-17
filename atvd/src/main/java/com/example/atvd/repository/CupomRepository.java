package com.example.atvd.repository;

import com.example.atvd.entities.Cupom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends CrudRepository<Cupom, Long> {
}
