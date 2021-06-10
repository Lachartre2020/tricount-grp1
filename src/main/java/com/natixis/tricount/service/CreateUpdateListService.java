package com.natixis.tricount.service;

import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUpdateListService {

    @Autowired
    private ExpenseListRepository expenseListRepository;

    public void save(ExpenseList expenseList) {
        expenseListRepository.save(expenseList);
    }

    public Optional<ExpenseList> findById(Long idList) {
        return expenseListRepository.findById(idList);
    }
}
