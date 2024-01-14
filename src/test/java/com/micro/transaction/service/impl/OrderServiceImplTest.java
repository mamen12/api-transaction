package com.micro.transaction.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.commons.beans.beans.OrderRequest;
import com.commons.beans.beans.OrderResponse;
import com.commons.beans.constant.AppConstants;
import com.micro.transaction.service.IOrderService;

@SpringBootTest
class OrderServiceImplTest {

	@Autowired
	private IOrderService orderService;
	
	@Test
	void testOrderProduct() throws InterruptedException, ExecutionException {
		OrderRequest ordRq = new OrderRequest();
		ordRq.setEmailUser("bastian03@email.com");
		ordRq.setIdProduct("bbf3241c-ee9c-43e1-9852-4db451151876");
		ordRq.setQty(10);
		OrderResponse rs =  orderService.orderProduct(ordRq);
		assertEquals(rs.getStatusOrder(), AppConstants.STATUS_ORDER_SAVED);
	}

}
