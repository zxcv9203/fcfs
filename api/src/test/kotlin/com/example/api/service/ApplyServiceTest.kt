package com.example.api.service

import com.example.api.repository.CouponRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
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

    @Test
    fun `여러명 응모`() = runTest{
        val threadCount = 1000
        val want = 100L
        coroutineScope {
            for (userId in 1L..threadCount) {
                launch(Dispatchers.Default) {
                    applyService.apply(userId)
                }
            }
        }
        // 전달된 토픽이 전부 처리될때까지 대기하기 위해 10초 슬립
        Thread.sleep(10000)

        val got = couponRepository.count()
        assertThat(got).isEqualTo(want)
    }

    @Test
    fun `한명당 한개의 쿠폰만 발급`() = runTest{
        val threadCount = 1000
        coroutineScope {
            for (i in 1L..threadCount) {
                launch(Dispatchers.Default) {
                    applyService.apply(1)
                }
            }
        }
        // 전달된 토픽이 전부 처리될때까지 대기하기 위해 10초 슬립
        Thread.sleep(10000)

        val got = couponRepository.count()
        assertThat(got).isEqualTo(1)
    }
}