package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Bill;
import com.example.shopdunkproject.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository iBillRepository;

    @Override
    public Page<Bill> getAllBills(Pageable pageable) {
        return iBillRepository.findAll(pageable);
    }

    @Override
    public Bill getBillById(Long id) {
        return iBillRepository.findById(id).orElse(null);
    }
}
