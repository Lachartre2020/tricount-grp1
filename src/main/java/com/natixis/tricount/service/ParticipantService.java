package com.natixis.tricount.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.repository.ParticpantRepository;

@Service
public class ParticipantService {
 
    @Autowired
    private ParticpantRepository particpantRepository; 
    
    public Optional<Participant> findById(Long id_participant) {
        return particpantRepository.findById(id_participant);
    } 
    
}
