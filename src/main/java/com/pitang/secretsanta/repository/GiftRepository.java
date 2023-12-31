package com.pitang.secretsanta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pitang.secretsanta.model.Gift;

public interface GiftRepository extends JpaRepository<Gift, Long> {

}
