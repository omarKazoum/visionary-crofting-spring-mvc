package com.visionary.crofting.service.Impl;

import com.visionary.crofting.dto.OrderDTO;
import com.visionary.crofting.dto.OrderItemDTO;
import com.visionary.crofting.entity.Order;
import com.visionary.crofting.entity.OrderItem;
import com.visionary.crofting.entity.Product;
import com.visionary.crofting.exceptions.BusinessException;
import com.visionary.crofting.repository.ClientRepository;
import com.visionary.crofting.repository.OrderItemRepository;
import com.visionary.crofting.repository.OrderRepository;
import com.visionary.crofting.repository.ProductRepository;
import com.visionary.crofting.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Override
    public Order addOrder(OrderDTO orderDTO) throws BusinessException{
        List<String> errors=new ArrayList<>();
        if(!isOrderDTOValide(orderDTO,errors,OperationENum.ADD))
            throw new BusinessException("invalid token",errors);

            ModelMapper modelMapper=new ModelMapper();
            Order orderToSave=new Order();
            orderToSave.setClient(clientRepository.findById(orderDTO.getClientId()).get());
            String ref=UUID.randomUUID().toString();
            orderToSave.setReference(ref);
            orderToSave.setCreatedAt(new Date());
            orderToSave.setStatus(Order.OrderStatusEnum.CREATED);
            orderToSave.setId(null);
            AtomicReference<Double> totalPrice= new AtomicReference<>( 0d);
            //TODO set total price
            Order finalOrderToSave = orderToSave;
            Order finalOrderToSave1 = orderToSave;
            orderDTO.getItems().forEach(item->{
                    //creating nrw OrderItem from orderItemDto
                   OrderItem orderItem=new OrderItem();
                   //orderItem.setOrder(finalOrderToSave);
                   Optional<Product> optionalProduct=productRepository.findById(item.getProductId());
                   double itemPrice=optionalProduct.get().getInitialPrice()*item.getQuantity();
                   orderItem.setTotalPrice(itemPrice);
                   orderItem.setProduct(optionalProduct.get());
                   orderItem.setQuantity(item.getQuantity());
                   totalPrice.updateAndGet(v -> v + itemPrice);
                   optionalProduct.get().setQuantity(optionalProduct.get().getQuantity()-item.getQuantity());
                   productRepository.save(optionalProduct.get());
                   finalOrderToSave1.getOrderItems().add(orderItem);
                   //orderItemRepository.save(orderItem);
            });
            orderToSave.setTotalPrice(totalPrice.get());
            orderToSave=orderRepository.save(orderToSave);
            Order finalOrderToSave2 = orderToSave;
            orderToSave.getOrderItems().forEach(item->{
                item.setOrder(finalOrderToSave2);
                orderItemRepository.save(item);
            });
            return orderToSave;
    }

    @Override
    public Order confirmOrder(Order order) {
        return null;
    }

    @Override
    public Order updateOrder(OrderDTO orderDTO) throws BusinessException{
        List<String> errors=new ArrayList<>();
        if(!isOrderDTOValide(orderDTO,errors,OperationENum.ADD))
            throw new BusinessException("invalid token",errors);

        Order orderToUpdate=orderRepository.findById(orderDTO.getId()).get();
        orderToUpdate.setClient(clientRepository.findById(orderDTO.getClientId()).get());
        orderToUpdate.setTotalPrice(orderDTO.getTotalPrice()!=-1?orderDTO.getTotalPrice():orderToUpdate.getTotalPrice());
        orderToUpdate.setCreatedAt(orderDTO.getCreatedAt()!=null?orderDTO.getCreatedAt():orderToUpdate.getCreatedAt());
        orderToUpdate.setStatus(Order.OrderStatusEnum.CREATED);
        orderToUpdate=orderRepository.save(orderToUpdate);

        return orderToUpdate;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderItem> getOrderItemsPerOrder(Long orderId) throws BusinessException{
        if(!orderRepository.existsById(orderId))
            throw new BusinessException("invalide order id", Arrays.asList("Unknown order id "));
        return orderRepository.findById(orderId).get().getOrderItems();
    }

    @Override
    public List<Order> getOrdersByStatus(Order.OrderStatusEnum status) {
        return orderRepository.findOrderByStatus(status);
    }

    @Override
    public Page<Order> findAll(Pageable of, String orderRef) {
        Page<Order> page=null;
        if(!orderRef.isEmpty() ){
            page= orderRepository.findAll(hasOrderReference(orderRef),of);
        }
        else page= orderRepository.findAll(of);
        //return null;
        return page;
    }

    @Override
    public boolean updateStatus(String reference, Order.OrderStatusEnum newStatus) throws BusinessException {
        Optional<Order> optionalOrder=orderRepository.findOrderByReference(reference);
        if(optionalOrder.isEmpty())
            throw new BusinessException("invalid order reference",null);
        if (optionalOrder.get().getStatus().equals(newStatus))
            return true;
        optionalOrder.get().setStatus(newStatus);
        orderRepository.save(optionalOrder.get());
        return true;
    }

    private Specification<Order> hasOrderReference(String orderReference){
        return (order,criteriaQuery,criteriaBuilder)->
            criteriaBuilder.equal(order.get("reference"),orderReference);
    }
    boolean isOrderDTOValide(OrderDTO orderDTO, List<String> errors,OperationENum operation) throws BusinessException{
        boolean valide=true;

        if(operation.equals(OperationENum.UPDATE) && (orderDTO.getClientId()==null || !clientRepository.existsById(orderDTO.getId()))) {
            errors.add("invalid client id!");
            valide = false;
        }
        if(operation.equals(OperationENum.UPDATE) && (orderDTO.getId()==null || !orderRepository.existsById(orderDTO.getId()))) {
            errors.add("invalid order id!");
            valide = false;
        }

        if(orderDTO.getItems()==null || orderDTO.getItems().size()==0){
            valide=false;
            errors.add("can't add an empty order");
        }
        if(orderDTO.getItems()!=null){
            for (int i = 0; i < orderDTO.getItems().size(); i++) {
                OrderItemDTO item=orderDTO.getItems().get(i);
                Optional<Product> p=productRepository.findById(item.getProductId());
                if(!p.isPresent()){
                    valide=false;
                    errors.add("invalide product id ");
                    break;
                }
                if (p.get().getQuantity()<item.getQuantity()){
                    valide=false;
                    errors.add("invalide quantity not available for '"+p.get().getTitle()+"'");
                    break;
                }
                if(item.getQuantity()==0){
                    valide=false;
                    errors.add("invalide quantity 0 for product '"+p.get().getTitle()+"'");
                    break;
                }
            }
        }


       //TODO continue validation

        return valide;
    }
    enum OperationENum{
        ADD,
        UPDATE
    }

}
