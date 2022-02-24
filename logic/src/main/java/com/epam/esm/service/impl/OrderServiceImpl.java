package com.epam.esm.service.impl;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.Certificate;
import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.model.Order;
import com.epam.esm.repository.api.CertificateRepository;
import com.epam.esm.repository.api.OrderRepository;
import com.epam.esm.service.api.OrderService;
import com.epam.esm.service.api.UserService;
import com.epam.esm.validator.impl.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserService userService;
    private final OrderValidator orderValidator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CertificateRepository certificateRepository,
                            UserService userService,
                            OrderValidator orderValidator
    ) {
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.userService = userService;
        this.orderValidator = orderValidator;
    }

    @Override
    public List<Order> findAll(int page, int size) {
        return orderRepository.findAllWithPagination(page, size);
    }

    @Override
    @Transactional
    public List<Order> findAllByUserId(long userId, int page, int size) throws NotFoundEntityException {
        return orderRepository.findAllByUserId(userId, page, size);
    }

    @Transactional(rollbackFor = NotFoundEntityException.class)
    public Order create(CreateOrderDto createOrderDto) throws NotFoundEntityException {
        orderValidator.isValid(createOrderDto);
        Order order = new Order();
        Certificate certificate = certificateRepository.findById(createOrderDto.getCertificateId()).orElseThrow(() -> new NotFoundEntityException("Not found Certificate"));
        order.setCost(certificate.getPrice());
        order.setUser(createOrderDto.getUserId());
        order.setCertificate(createOrderDto.getCertificateId());
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        //todo spend no spent!
        userService.addSpentMoney(createOrderDto.getUserId(), order.getCost());
        return orderRepository.create(order);
    }

    @Transactional
    public Order findByUserId(long userId, long orderId) throws NotFoundEntityException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundEntityException("Not found order"));
        if (order.getUser() != userId) {
            throw new NotFoundEntityException("This order belongs to another user");
        }
        return order;
    }

}
