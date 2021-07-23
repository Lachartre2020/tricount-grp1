package com.natixis.tricount.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.natixis.tricount.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	//public Page<Expense> findById(Long idList, Pageable pageable);
	//@Query("select e from Expense e where e.expense_list_id = :x")
	//public Page<Expense> findById(@Param("x")Long idList, Pageable pageable);
	public Page<Expense> findByExpenseListId(Long expense_list_id , Pageable pageable);
}

