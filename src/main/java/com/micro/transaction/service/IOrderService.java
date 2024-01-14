package com.micro.transaction.service;

import java.util.concurrent.ExecutionException;

import com.commons.beans.beans.OrderRequest;
import com.commons.beans.beans.OrderResponse;

public interface IOrderService {

	public OrderResponse orderProduct(OrderRequest rq) throws InterruptedException, ExecutionException;
	
}
