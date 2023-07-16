package com.example.api.service

import com.example.api.repository.CouponRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplyServiceTest(
    @Autowired
    private val applyService: ApplyService,

    @Autowired
    private val couponRepository: CouponRepository
) {
    @Test
    fun `한번만 응모`() {
        val userId = 1L
        val want = 1L

        applyService.apply(userId)

        val got = couponRepository.count()

        assertThat(got).isEqualTo(want)
    }
}