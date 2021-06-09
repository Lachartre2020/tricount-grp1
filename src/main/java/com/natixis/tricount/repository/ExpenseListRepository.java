package com.natixis.tricount.repository;

import com.natixis.tricount.entity.ExpenseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseListRepository extends JpaRepository<ExpenseList, Long> {

}
