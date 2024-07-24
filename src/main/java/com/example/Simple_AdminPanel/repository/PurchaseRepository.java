package com.example.Simple_AdminPanel.repository;

import com.example.Simple_AdminPanel.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
