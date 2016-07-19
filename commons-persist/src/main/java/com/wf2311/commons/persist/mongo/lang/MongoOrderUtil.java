package com.wf2311.commons.persist.mongo.lang;

import com.wf2311.commons.persist.lang.Order;
import com.wf2311.commons.persist.lang.OrderType;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class MongoOrderUtil {
    public static Sort.Order getMongoOrder(Order order) {
        MongoOrder mo = (MongoOrder) order;
        return new Sort.Order(
                mo.getOrderType().equals(OrderType.ASC) ? Direction.ASC : Direction.DESC
                , order.getProperty());
    }

}
