package com.micro.transaction.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mt_order", schema = "transaction")
public class Order implements Serializable{
  
	/**
	 * syahrul added 12-01-2024
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_order", length = 36)
	private String idOrder;
	
	@Column(name = "id_user", length = 36)
	private String idUser;
	
	@Column(name = "id_product", length = 36)
	private String idProduct;
	
	@Column(name = "name_product")
	private String nameProduct;
	
	private Integer qty;
	
	@Column(name ="total_price")
	private BigDecimal priceTotal;
	
	@Column(name = "trx_status")
	private String statusTransaction;
	
	@Column(name = "trx_date")
	private Date transactionDate;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updateAt;

	public String getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getStatusTransaction() {
		return statusTransaction;
	}

	public void setStatusTransaction(String statusTransaction) {
		this.statusTransaction = statusTransaction;
	}
	
	
}
