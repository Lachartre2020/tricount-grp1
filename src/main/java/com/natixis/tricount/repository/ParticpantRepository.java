package com.natixis.tricount.repository;

import com.natixis.tricount.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticpantRepository extends JpaRepository<Participant, Long> {
}
