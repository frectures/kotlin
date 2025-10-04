package generators

data class Position(val col: Char, val row: Int) {
    override fun toString() = "$col$row"
}

fun generateChessboardPositions(): Sequence<Position> = sequence {
    for (col in 'A'..'H') {
        for (row in 1..8) {
            yield(Position(col, row))
        }
    }
}

fun main() {
    for (position in generateChessboardPositions()) {
        println(position)
    }
}
