package com.visionary.crofting.repository;

import com.visionary.crofting.entity.Order;
import com.visionary.crofting.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    List<Order> findOrderByStatus(Order.OrderStatusEnum status);
    Optional<Order> findOrderByReference(String Reference);

}
