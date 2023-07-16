package com.example.api.service

import com.example.api.domain.Coupon
import com.example.api.repository.CouponCountRepository
import com.example.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository
) {
    fun apply(userId: Long) {
        val count = couponCountRepository.increment()?: 100L

        if (count > 100) {
            return
        }

        couponRepository.save(
            Coupon(userId = userId)
        )
    }
}