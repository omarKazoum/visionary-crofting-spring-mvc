package com.visionary.crofting.repository;

import com.visionary.crofting.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Optional<Stock> findStockByEmail(String email);
}
