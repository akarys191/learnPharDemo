package com.pharm.demo.order.data.controller;


import com.pharm.demo.order.data.exceptions.OrderNotFoundException;
import com.pharm.demo.order.data.model.Order;
import com.pharm.demo.order.data.service.OrderService;
import com.pharm.demo.order.data.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private UserService userService;
    private OrderService orderService;

    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") Long id) throws OrderNotFoundException {
        return Optional.ofNullable( orderService.gerOrder(id)).orElseThrow( () ->  new OrderNotFoundException ("We cannot find order with  %s id",id));
    }

    @PostMapping
    public Order newOrder(@RequestBody @Valid Order order ){
        return orderService.saveOrder(order);
    }

    @GetMapping
    public List<Order> orderList(){
        return orderService.orderList();
    }





}
