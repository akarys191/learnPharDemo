package com.pharm.demo.order.data.bootstrap;

import com.pharm.demo.order.data.model.Order;
import com.pharm.demo.order.data.model.User;
import com.pharm.demo.order.data.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

//    private UserService userService;
    @Autowired
    private OrderService orderService;

//    public DataLoader(UserService userService, OrderService orderService) {
//        this.userService = userService;
//        this.orderService = orderService;
//    }


    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("test","test","87777777777","test@test.kz",new HashSet<>());

        Order order = new Order(user1,"test name",10, LocalDate.of(2021,12,16),LocalDate.now());
        user1.getOrderSet().add(order);
        orderService.saveOrder(order);
    }
}
