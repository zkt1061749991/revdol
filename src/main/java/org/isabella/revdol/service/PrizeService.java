package org.isabella.revdol.service;

import org.isabella.revdol.domin.Order;
import org.isabella.revdol.domin.Prize;

import java.util.List;

public interface PrizeService {
    List<Prize> getPrizeList();
    Prize getPrize(String id);
    void insertOrder(Order order);
    void updatePrize(Prize prize);
}
