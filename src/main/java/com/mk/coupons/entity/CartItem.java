package com.mk.coupons.entity;

import lombok.Data;

@Data
public class CartItem {


    private Long productId;
    private int quantity;
    private double price;



}
