package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Prize;

import java.util.List;

public interface PrizeMapper {
    List<Prize> getPrizeList();
    Prize getPrize(String id);
    void updatePrize(Prize prize);
}
