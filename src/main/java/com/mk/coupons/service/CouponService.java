package com.mk.coupons.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.coupons.entity.Cart;
import com.mk.coupons.entity.CartItem;
import com.mk.coupons.entity.Coupon;
import com.mk.coupons.repo.CouponRepo;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CouponService {

	@Autowired
	private CouponRepo repo;
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	public Coupon saveCoupon(Coupon request) {
		return repo.save(request);
	}

	public List<Coupon> getAllCoupons() {
		return repo.findAll();
	}

	public Coupon getCouponById(Long id) {
		return repo.findById(id).get();
	}

	public Coupon updateCoupon(Long id, Coupon coupon) throws ObjectNotFoundException {
		Coupon coupon1 = getCouponById(id);
		if (coupon1 != null) {
			coupon1.setType(coupon.getType());
			coupon1.setDetails(coupon.getDetails());
			return repo.save(coupon1);
		}
		throw new ObjectNotFoundException("coupon is not found with given id");
	}

	public String deleteCoupon(Long id) {
		try {
			repo.deleteById(id);
			return "given coupon deleted";
		} catch (EmptyResultDataAccessException e) {
			return "coupon is not found with given id";

		}

	}

	private double calculateCartTotal(Cart cart) {
		return cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
	}

	private double calculateProductDiscount(Cart cart, Long productId, double discount) {
		return cart.getItems().stream().filter(item -> item.getProductId().equals(productId))
				.mapToDouble(item -> (item.getPrice() * item.getQuantity() * discount) / 100).sum();
	};

	private double calculateBxGyDiscount(Cart cart, JsonNode details) {

		Long productId = details.get("product_id").asLong();
		int buyX = details.get("buy_x").asInt();
		int getY = details.get("get_y").asInt();

		CartItem targetItem = cart.getItems().stream().filter(item -> item.getProductId().equals(productId)).findFirst()
				.orElse(null);

		if (targetItem == null) {
			return 0.0;
		}

		int totalQuantity = targetItem.getQuantity();
		int eligibleFreeItems = (totalQuantity / (buyX + getY)) * getY;

		double discount = eligibleFreeItems * targetItem.getPrice();

		return discount;
	}

	public Map<String, Object> applyCoupon(Long id, Cart cart) throws Exception {
		Coupon coupon = getCouponById(id);
		if (coupon == null) {
			throw new Exception("Coupon not found");
		}

		Map<String, Object> response = new HashMap<>();
		double totalDiscount = 0.0;
		JsonNode details = objectMapper.readTree(coupon.getDetails());

		switch (coupon.getType()) {
		case "cart-wise":
			double threshold = details.get("threshold").asDouble();
			double discount = details.get("discount").asDouble();
			double cartTotal = calculateCartTotal(cart);

			if (cartTotal > threshold) {
				totalDiscount = (cartTotal * discount) / 100;
			}
			break;

		case "product-wise":
			Long productId = details.get("product_id").asLong();
			double productDiscount = details.get("discount").asDouble();

			totalDiscount = calculateProductDiscount(cart, productId, productDiscount);
			break;

		case "bxgy":
			totalDiscount = calculateBxGyDiscount(cart, details);
			break;

		default:
			throw new Exception("Unsupported coupon type");
		}

		response.put("total_discount", totalDiscount);
		return response;
	}

}
