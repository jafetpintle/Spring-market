package com.japy.platzimarket.domain.repository;

import com.japy.platzimarket.domain.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(String cliendId);
    Purchase save(Purchase purchase);
}
