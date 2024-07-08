package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface BillRepository extends JpaRepository<Bill,Long> {
    Page<Bill> findByUser_UserName(String userName, Pageable pageable);
    Page<Bill> findByOrderDate(Date orderDate, Pageable pageable);
    Page<Bill> findByTotalAmountContaining(Double totalAmount, Pageable pageable);
    Page<Bill> findByUser_UserNameAndOrderDate(String userName, Date orderDate, Pageable pageable);
    Page<Bill> findByUser_UserNameAndTotalAmountContaining(String userName, Double totalAmount, Pageable pageable);
    Page<Bill> findByOrderDateAndTotalAmountContaining(Date orderDate, Double totalAmount, Pageable pageable);
    Page<Bill> findByUser_UserNameAndOrderDateAndTotalAmountContaining(String userName, Date orderDate, Double totalAmount, Pageable pageable);
}
