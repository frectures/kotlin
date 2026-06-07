import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntegerLimitsTest {
    @Test
    fun overflow() {
        // Jupiter: links das Ergebnis, rechts die Rechnung
        assertEquals(-2147483648, 2147483647 + 1)

        // AssertJ: links die Rechnung, rechts das Ergebnis
        assertThat(2147483647 + 1).isEqualTo(-2147483648)
    }

    @Test
    fun underflow() {
        // Jupiter
        assertEquals(2147483647, -2147483648 - 1)

        // AssertJ
        assertThat(-2147483648 - 1).isEqualTo(2147483647)
    }
}
