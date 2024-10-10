package generators;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class ChessboardPositionsStream {
    public record Position(char col, int row) {
        @Override
        public String toString() {
            return col + "" + row;
        }
    }

    public static void main(String[] args) {
        Stream<Position> positions = IntStream
                .rangeClosed('A', 'H')
                .boxed()
                .flatMap(col -> IntStream
                        .rangeClosed(1, 8)
                        .mapToObj(row -> new Position((char) +col, row)));

        positions.forEach(position -> {
            System.out.println(position);
        });
    }
}
