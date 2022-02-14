package com.epam.esm.controller;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.link.OrderLinkProvider;
import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.model.Order;
import com.epam.esm.service.api.OrderService;
import com.epam.esm.validator.impl.RequestParametersValidator;
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

import static com.epam.esm.util.RequestParammetr.DEFAULT_PAGE;
import static com.epam.esm.util.RequestParammetr.DEFAULT_SIZE;
import static com.epam.esm.util.RequestParammetr.PAGE;
import static com.epam.esm.util.RequestParammetr.SIZE;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderLinkProvider orderLinkProvider;
    private final RequestParametersValidator requestParametersValidator;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderLinkProvider orderLinkProvider,
                           RequestParametersValidator requestParametersValidator) {
        this.orderService = orderService;
        this.orderLinkProvider = orderLinkProvider;
        this.requestParametersValidator = requestParametersValidator;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAll(@RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size) {
        requestParametersValidator.paginationParamValid(page, size);
        List<Order> orders = orderService.findAll(page, size);
        orders.forEach(orderLinkProvider::provideLinks);
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