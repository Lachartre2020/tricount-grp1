package com.natixis.tricount.service;

import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseListService {

    @Autowired
    private ExpenseListRepository expenseListRepository;

    public List<ExpenseList> findAll() {
        return expenseListRepository.findAll();
    }

    public Optional<ExpenseList> findById(Long id) { return expenseListRepository.findById(id); }

}

	public void save(ExpenseList expenseList) {
		expenseListRepository.save(expenseList);		
	}
}

