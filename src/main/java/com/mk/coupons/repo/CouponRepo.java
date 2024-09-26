package com.mk.coupons.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mk.coupons.entity.Coupon;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long> {

}
