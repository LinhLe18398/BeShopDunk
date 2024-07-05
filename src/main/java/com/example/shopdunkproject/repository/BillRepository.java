package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Long> {
}
