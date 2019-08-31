package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Order;

public interface OrderMapper {
    void insertOrder(Order order);
}
