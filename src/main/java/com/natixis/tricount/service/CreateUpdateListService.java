package com.natixis.tricount.service;

import com.natixis.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUpdateListService {

    @Autowired
    private ExpenseListRepository expenseListRepository;

    public void save(Long idList) {
        //repo.save(idList);
    }
}
