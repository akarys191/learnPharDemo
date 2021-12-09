package com.pharm.demo.order.data.service;

import com.pharm.demo.order.data.model.Order;
import com.pharm.demo.order.data.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order gerOrder(Long id){
        return orderRepository.getOne(id);
    }

    public List<Order> getOrdersByUserId(Long userID){
       return orderRepository.findOrderByUserId(userID);
    }

    public Order saveOrder (Order order){
       return orderRepository.save(order);
    }

    public List<Order> orderList(){
        return orderRepository.findAll();
    }

}
