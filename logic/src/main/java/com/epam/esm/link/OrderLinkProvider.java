package com.epam.esm.link;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderLinkProvider extends AbstractLinkProvider<Order> {

    private static final Class<OrderController> CONTROLLER_CLASS = OrderController.class;

    public void provideLinks(Order order) {
        long id = order.getId();
        provideIdLinks(CONTROLLER_CLASS, order, id, SELF_LINK);
    }

}
