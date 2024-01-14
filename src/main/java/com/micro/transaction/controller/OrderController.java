package com.micro.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.commons.beans.beans.OrderRequest;
import com.commons.beans.beans.OrderResponse;
import com.commons.beans.beans.Request;
import com.commons.beans.beans.Response;
import com.commons.beans.constant.ApiResponse;
import com.micro.transaction.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value = "/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<OrderResponse> updateProductInventory(@RequestBody Request<OrderRequest> rq){
    	Response<OrderResponse> rs = new Response<OrderResponse>();
    	try {
    		OrderResponse resp = orderService.orderProduct(rq.getRequestPayload());
    		rs.setData(resp);
    		rs.setStatusResponse(ApiResponse.SUCCESS);
		} catch (Exception e) {
			rs.setStatusResponse(ApiResponse.FAILED);
		}
    	return rs;
    }
}
