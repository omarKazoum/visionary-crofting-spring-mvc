package com.visionary.crofting.service;

import com.visionary.crofting.dto.OrderDTO;
import com.visionary.crofting.entity.Order;
import com.visionary.crofting.entity.OrderItem;
import com.visionary.crofting.entity.Product;
import com.visionary.crofting.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order addOrder(OrderDTO order) throws BusinessException;
    Order confirmOrder(Order order) throws BusinessException;
    Order updateOrder(OrderDTO order) throws BusinessException;
    List<Order> getAll();
    List<OrderItem> getOrderItemsPerOrder(Long orderId) throws BusinessException;
    List<Order> getOrdersByStatus(Order.OrderStatusEnum status);
    Page<Order> findAll(Pageable of, String orderRef);

    boolean updateStatus(String reference, Order.OrderStatusEnum newStatus) throws BusinessException;
}
