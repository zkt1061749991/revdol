package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.Order;
import org.isabella.revdol.domin.Prize;
import org.isabella.revdol.persistence.OrderMapper;
import org.isabella.revdol.persistence.PointMapper;
import org.isabella.revdol.persistence.PrizeMapper;
import org.isabella.revdol.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrizeServiceImpl implements PrizeService{
    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Prize> getPrizeList() {
        return prizeMapper.getPrizeList();
    }

    @Override
    public Prize getPrize(String id) {
        return prizeMapper.getPrize(id);
    }

    @Override
    public void insertOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    @Override
    public void updatePrize(Prize prize) {
        prizeMapper.updatePrize(prize);
    }
}
