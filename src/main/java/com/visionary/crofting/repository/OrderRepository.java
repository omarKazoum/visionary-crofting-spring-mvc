package com.visionary.crofting.repository;

import com.visionary.crofting.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrderByStatus(Order.OrderStatusEnum status);
}
