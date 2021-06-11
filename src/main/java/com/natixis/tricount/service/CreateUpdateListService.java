package com.natixis.tricount.service;

import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.repository.ExpenseListRepository;
import com.natixis.tricount.repository.ParticpantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreateUpdateListService {

    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Autowired
    private ParticpantRepository participantRepository;

    public void save(ExpenseList expenseList) {
        expenseListRepository.save(expenseList);
    }

    public Optional<ExpenseList> findById(Long idList) {
        return expenseListRepository.findById(idList);
    }

    public void addParticipantList(Participant participant, Long idList) {
        ExpenseList expenseList = expenseListRepository.findById(idList).get();
        participant.setExpenseList(expenseList);
        participantRepository.save(participant);
    }
}
