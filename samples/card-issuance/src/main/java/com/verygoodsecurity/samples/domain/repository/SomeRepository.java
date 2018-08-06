package com.verygoodsecurity.samples.domain.repository;

import com.verygoodsecurity.samples.domain.SomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeRepository extends JpaRepository<SomeEntity, Integer> {
}
