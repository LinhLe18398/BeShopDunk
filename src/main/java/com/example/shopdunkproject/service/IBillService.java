package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBillService {
    Page<Bill> getAllBills(Pageable pageable);
    Bill getBillById(Long id);
}
