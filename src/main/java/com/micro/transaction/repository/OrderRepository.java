package com.micro.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.transaction.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

}
