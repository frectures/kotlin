package advent

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdventOfCodeTest {
    @Test
    fun `test 2021 day 1 part 1`() {
        val measurements = listOf(
            199,
            200,
            208,
            210,
            200,
            207,
            240,
            269,
            260,
            263,
        )
        assertThat(`2021 day 1 part 1`(measurements)).isEqualTo(7)
    }

    @Test
    fun `test 2022 day 1 part 1`() {
        val backpacks = listOf(
            listOf(1000, 2000, 3000),
            listOf(4000),
            listOf(5000, 6000),
            listOf(7000, 8000, 9000),
            listOf(10000),
        )
        assertThat(`2022 day 1 part 1`(backpacks)).isEqualTo(7000 + 8000 + 9000)
    }

    @Nested
    @DisplayName("test 2020 day 1 part 1")
    inner class Test2020 {
        @Test
        fun `leading 1010`() {
            val entries = listOf(
                1010,
                1721,
                979,
                366,
                675,
                1456,
                299,
            )
            assertThat(`2020 day 1 part 1`(entries)).isEqualTo(1721 * 299)
        }

        @Test
        fun `trailing 1010`() {
            val entries = listOf(
                1721,
                979,
                366,
                675,
                1456,
                299,
                1010,
            )
            assertThat(`2020 day 1 part 1`(entries)).isEqualTo(1721 * 299)
        }

        @Test
        fun `different 1010s`() {
            val entries = listOf(
                1010,
                979,
                366,
                675,
                1456,
                1010,
            )
            assertThat(`2020 day 1 part 1`(entries)).isEqualTo(1010 * 1010)
        }
    }

    @Test
    fun `test 2024 day 1 part 1`() {
        val pairs = listOf(
            Pair(3, 4),
            Pair(4, 3),
            Pair(2, 5),
            Pair(1, 3),
            Pair(3, 9),
            Pair(3, 3),
        )
        assertThat(`2024 day 1 part 1`(pairs)).isEqualTo(11)
    }
}
