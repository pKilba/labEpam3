package com.epam.esm.controller;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.link.OrderLinkProvider;
import com.epam.esm.model.CreateOrderDto;
import com.epam.esm.model.Order;
import com.epam.esm.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    //todo const to interface
    public static final String SIZE = "size";
    public static final String PAGE = "page";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";

    private final OrderService orderService;
    private final OrderLinkProvider orderLinkProvider;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderLinkProvider orderLinkProvider) {
        this.orderService = orderService;
        this.orderLinkProvider = orderLinkProvider;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAll(@RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size) {
        List<Order> orders = orderService.findAll(page, size);

        for (Order order : orders) {
            orderLinkProvider.provideLinks(order);
        }

        return orders;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderDto createOrderDto
    ) throws NotFoundEntityException {

        Order orderDto = orderService.create(createOrderDto);
        return orderDto;
    }


}