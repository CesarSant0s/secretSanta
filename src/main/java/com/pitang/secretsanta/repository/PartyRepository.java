package com.pitang.secretsanta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pitang.secretsanta.model.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {

}
