package com.wf2311.commons.persist.mongo.lang;

import com.wf2311.commons.persist.lang.Order;
import com.wf2311.commons.persist.lang.OrderType;

public class MongoOrder implements Order {

	private OrderType orderType;
	
	private String propertyName;

	@Override
	public String getProperty() {
		return propertyName;
	}

	public MongoOrder(String propertyName) {
		this.propertyName = propertyName;
		this.orderType = OrderType.ASC;
	}
	public MongoOrder(String propertyName, OrderType orderType) {
		this.propertyName = propertyName;
		this.orderType = orderType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

}
