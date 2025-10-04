package generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ChessboardPositionsGenerator {
    public record Position(char col, int row) {
        @Override
        public String toString() {
            return col + "" + row;
        }
    }

    public static Iterable<Position> generateChessboardPositions() {
        return new Iterable<Position>() {
            public Iterator<Position> iterator() {
                return new Iterator<Position>() {
                    private char col = 'A';
                    private int row = 1;

                    public boolean hasNext() {
                        return col <= 'H';
                    }

                    public Position next() {
                        if (!hasNext()) throw new NoSuchElementException();

                        Position result = new Position(col, row);
                        ++row;
                        if (row > 8) {
                            row = 1;
                            ++col;
                        }
                        return result;
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        for (var position : generateChessboardPositions()) {
            System.out.println(position);
        }
    }
}
