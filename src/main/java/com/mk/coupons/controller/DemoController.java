package com.mk.coupons.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mk.coupons.entity.Cart;
import com.mk.coupons.entity.Coupon;
import com.mk.coupons.service.CouponService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/coupon")
public class DemoController {

	@Autowired
	private CouponService couponService;

	@GetMapping
	public String demoMethod() {
		return "Hello every One";
	}

	@PostMapping("/save")
	public Coupon saveCoupon(@RequestBody Coupon request) {
		return couponService.saveCoupon(request);
	}

	@GetMapping("/allCoupons")
	public List<Coupon> getAllCoupons() {
		return couponService.getAllCoupons();
	}

	@GetMapping("/{id}")
	public Coupon getCouponById(@PathVariable Long id) {
		return couponService.getCouponById(id);
	}

	@PutMapping("/{id}")
	public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) throws ObjectNotFoundException {
		return couponService.updateCoupon(id, coupon);
	}

	@DeleteMapping("/{id}")
	public String deleteCoupon(@PathVariable Long id) {
		return couponService.deleteCoupon(id);

	}

	@PostMapping("/apply-coupon/{id}")
	public Map<String, Object> applyCoupon(@PathVariable Long id, @RequestBody Cart cart) throws Exception {
		return couponService.applyCoupon(id, cart);
	}

}
