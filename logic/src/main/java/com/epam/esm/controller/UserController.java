package com.epam.esm.controller;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.link.OrderLinkProvider;
import com.epam.esm.link.UserLinkProvider;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.service.api.OrderService;
import com.epam.esm.service.api.UserService;
import com.epam.esm.validator.impl.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    public static final String SIZE = "size";
    public static final String PAGE = "page";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";


    private final UserService userService;
    private final OrderService orderService;
    private final UserLinkProvider userLinkProvider;
    private final OrderLinkProvider orderLinkProvider;
    private final RequestParametersValidator requestParametersValidator;

    @Autowired
    public UserController(UserService userService,
                          OrderService orderService, UserLinkProvider userLinkProvider, OrderLinkProvider orderLinkProvider,
                          RequestParametersValidator requestParametersValidator) {
        this.userService = userService;
        this.orderService = orderService;
        this.userLinkProvider = userLinkProvider;
        this.orderLinkProvider = orderLinkProvider;
        this.requestParametersValidator = requestParametersValidator;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size)
            throws InvalidParameterException {
        requestParametersValidator.paginationParamValid(page, size);
        List<User> users = userService.findAll(page, size);

        for (User user : users) {
            userLinkProvider.provideLinks(user);
        }
        return users;
    }


    @GetMapping("/most-cost")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findByMostCost(@RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(value = SIZE, required = false,
                                             defaultValue = DEFAULT_SIZE) int size) {

        requestParametersValidator.paginationParamValid(page, size);

        List<User> users = userService.findByMostCost(page, size);
        for (User user : users) {
            userLinkProvider.provideLinks(user);
        }
        return users;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable long id) throws NotFoundEntityException {

        requestParametersValidator.idParamValid(id);

        User user = userService.findById(id);
        userLinkProvider.provideLinks(user);
        return user;
    }


    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order findOrderByUserId(
            @PathVariable(value = "id") long id,
            @PathVariable(value = "orderId") long orderId) throws NotFoundEntityException {
        requestParametersValidator.idParamValid(id);
        requestParametersValidator.idParamValid(orderId);
        Order orderDto = orderService.findByUserId(id, orderId);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }


    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByUserId(
            @PathVariable long id,
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size
    ) throws InvalidParameterException, NotFoundEntityException {
        requestParametersValidator.paginationParamValid(page, size);
        List<Order> orders = orderService.findAllByUserId(id, page, size);
        for (Order order : orders) {
            orderLinkProvider.provideLinks(order);
        }
        return orders;
    }
}
