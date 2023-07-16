package com.example.api.service

import com.example.api.producer.CouponCreateProducer
import com.example.api.repository.CouponCountRepository
import com.example.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository,
    private val couponCreateProducer: CouponCreateProducer
) {
    fun apply(userId: Long) {
        val count = couponCountRepository.increment() ?: 100L

        if (count > 100) {
            return
        }

        couponCreateProducer.create(userId)
    }
}