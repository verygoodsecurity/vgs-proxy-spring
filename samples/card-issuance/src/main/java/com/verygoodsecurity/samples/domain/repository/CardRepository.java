package com.verygoodsecurity.samples.domain.repository;

import com.verygoodsecurity.samples.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
