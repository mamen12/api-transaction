package com.micro.transaction.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.commons.beans.beans.InventoryRequest;
import com.commons.beans.beans.InventoryResponse;
import com.commons.beans.beans.OrderRequest;
import com.commons.beans.beans.OrderResponse;
import com.commons.beans.beans.Request;
import com.commons.beans.beans.RequsetHeader;
import com.commons.beans.beans.Response;
import com.commons.beans.beans.UserRequest;
import com.commons.beans.beans.UserResponse;
import com.commons.beans.constant.AppConstants;
import com.micro.transaction.entity.Order;
import com.micro.transaction.repository.OrderRepository;
import com.micro.transaction.service.IOrderService;


@Service
public class OrderServiceImpl implements IOrderService{

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public OrderResponse orderProduct(OrderRequest rq) throws InterruptedException, ExecutionException {
		OrderResponse rs = null;
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Request<UserRequest> userRequest = new Request<UserRequest>();
		Request<InventoryRequest> invenRequest = new Request<InventoryRequest>();
		String uuidOrder = UUID.randomUUID().toString();
		
		// change response by API
		RequsetHeader rqHeader = new RequsetHeader();
		rqHeader.setChanel("API");
		userRequest.setRequestHeader(rqHeader);
		invenRequest.setRequestHeader(rqHeader);
		try {
			rs = new OrderResponse();
			Future<UserResponse> f1 = executor.submit(new Callable<UserResponse>() {
				@Override
				public UserResponse call() throws Exception {
					UserRequest rq = new UserRequest();
					rq.setEmail("bastian03@email.com");
					
					userRequest.setRequestPayload(rq);
					UserResponse ur = restTemplate.postForObject(AppConstants.URL_USER_DETAIL, userRequest, UserResponse.class);
					return ur;
				}
			});
			
			Future<InventoryResponse> f2 = executor.submit(new Callable<InventoryResponse>() {
				@Override
				public InventoryResponse call() throws Exception {
					InventoryRequest rq = new InventoryRequest();
					rq.setIdProduct("bbf3241c-ee9c-43e1-9852-4db451151876");
					
					invenRequest.setRequestPayload(rq);
					InventoryResponse ur = restTemplate.postForObject(AppConstants.URL_INVENTORY_PRODUCT_DETAIL, invenRequest, InventoryResponse.class);
					return ur;
				}
			});
			
			Future<Integer> f3 = executor.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					InventoryRequest rq = new InventoryRequest();
					rq.setIdProduct("bbf3241c-ee9c-43e1-9852-4db451151876");
					
					invenRequest.setRequestPayload(rq);
					InventoryResponse ur = restTemplate.postForObject(AppConstants.URL_CHECK_INVENTORY_PRODUCT, invenRequest, InventoryResponse.class);
					return ur.getQuantity();
				}
			});
			
			
			UserResponse user = f1.get();
			InventoryResponse inven = f2.get();
			Integer getQuantityProduct = f3.get();
			
			
			if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(inven) &&
					(!ObjectUtils.isEmpty(getQuantityProduct) || getQuantityProduct != 0)) {
				
				//Calculate product multiplication and price
				Integer multi =  rq.getQty() * inven.getPrice().intValue();
				
				
				if (rq.getQty() > getQuantityProduct) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AppConstants.PRODUCT_NOT_AVAILABLE);
				}else{
					Order order = new Order();
					order.setIdOrder(uuidOrder);
					order.setIdUser(user.getIdUser());
					order.setIdProduct(inven.getIdInvent());
					order.setCreatedAt(new Date());
					order.setTransactionDate(new Date());
					order.setStatusTransaction(AppConstants.STATUS_ORDER_SAVED);
					order.setQty(rq.getQty());
					order.setPriceTotal(new BigDecimal(multi));
					order.setNameProduct(inven.getName());
					
					orderRepo.save(order);
				}
			}
			
			if (orderRepo.existsById(uuidOrder)) {
				Order order = orderRepo.findById(uuidOrder).orElseThrow();
				rs.setStatusOrder(order.getStatusTransaction());
				
				// Reduction of stock in inventory
				invenRequest.getRequestPayload().setQuantity(getQuantityProduct - rq.getQty());
				Response<Object> responseUpdate =  restTemplate.postForObject(AppConstants.URL_INVENTORY_PRODUCT_UPDATE_IF_TRX_SAVED, invenRequest, Response.class);
				logger.info(responseUpdate.getMessage());
			}
			
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rs;
		
	}
	
	
	
}
