package com.example.api.service

import com.example.api.producer.CouponCreateProducer
import com.example.api.repository.AppliedUserRepository
import com.example.api.repository.CouponCountRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    private val couponCountRepository: CouponCountRepository,
    private val couponCreateProducer: CouponCreateProducer,
    private val appliedUserRepository: AppliedUserRepository
) {
    fun apply(userId: Long) {
        val apply = appliedUserRepository.add(userId) ?: 0L
        if (apply != 1L) {
            return
        }

        val count = couponCountRepository.increment() ?: 100L
        if (count > 100) {
            return
        }

        couponCreateProducer.create(userId)
    }
}