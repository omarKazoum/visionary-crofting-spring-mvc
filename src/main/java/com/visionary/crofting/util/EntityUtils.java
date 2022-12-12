package com.visionary.crofting.util;

import com.visionary.crofting.dto.OrderDTO;
import com.visionary.crofting.dto.OrderItemDTO;
import com.visionary.crofting.entity.Order;
import com.visionary.crofting.entity.OrderItem;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

public class EntityUtils {

    public static OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem){
        ModelMapper modelMapper =new ModelMapper();
        OrderItemDTO oidto=modelMapper.map(orderItem,OrderItemDTO.class);
        return oidto;
    }

    public static OrderDTO orderToOrderDTO(Order order) {
        ModelMapper modelMapper=new ModelMapper();
        OrderDTO orderDTO=modelMapper.map(order, OrderDTO.class);
        orderDTO.setItems(order.getOrderItems().stream().map(EntityUtils::orderItemToOrderItemDTO).collect(Collectors.toList()));
        return orderDTO;
    }
}
