package com.natixis.tricount.repository;

import com.natixis.tricount.entity.ExpenseList;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseListRepository extends JpaRepository<ExpenseList, Long> {

}
