
import stdlib.In;
import stdlib.StdOut;
import java.util.Arrays;
import dsa.LinkedQueue;

// A data type to represent a board in the 8-puzzle game or its generalizations.

public class Board {
    int[][] tiles; // the board
    int n; // the size
    int hamming = 0; // the hamming distance
    int manhattan = 0; // the manhattan distance
    int blankPos = 0; // the blank position

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

        int num = 0;
        for (int i = 0; i < tiles.length; i++) {
            boolean flag = false;
            if (flag) {
                break;
            }
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == 0) {
                    blankPos = num + 1;
                    flag = true;
                    break;
                } else {
                    num++;
                }
            }
        }
        hamming = hamming();
        manhattan = manhattan();
    }
    // Returns the size of this board.
    public int size() {
        return n;
    }


    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        int sum = 0;

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] != size() * i + j + 1 && tiles[i][j] != 0) {
                    sum++;
                }
            }
        }
        return sum;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] != size() * i + j + 1 && tiles[i][j] != 0) {
                    int goal = tiles[i][j];
                    int targetRow = (goal - 1) / size();
                    int targetCol = (goal - 1) % size();
                    sum += Math.abs(targetCol - j) + Math.abs(targetRow - i);
                }
            }
        }
        return sum;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (i == size() - 1 && j == size() - 1) {
                    continue;
                }
                if (tiles[i][j] == 0 || tiles[i][j] != size() * i + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int inversions = 0;

        // find the inversions
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                for (int k = i; k < size(); k++) {
                    for (int m = j + 1; m < size(); m++) {
                        if (tiles[k][m] != 0 && tiles[k][m] <  tiles[i][j]) {
                            inversions++;
                        }
                    }
                }
            }
        }

        // if the size is odd and the number of inversions is odd
        if (size() % 2 == 1 && inversions % 2 == 1) {
            return false;
        }

        // if the sum is even and size is even
        int row = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] == 0) {
                    row = i;
                    break;
                }
            }
        }


        if (size() % 2 == 0 && (inversions + row) % 2 == 0) {
            return false;
        }

        return true;
    }


    // swap two elements
    private void swap(int[][] a, int i, int j, int targeti, int targetj) {
        int temp = a[i][j];
        a[i][j] = a[targeti][targetj];
        a[targeti][targetj] = temp;
    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        int ipos = 0;
        int jpos = 0;// record the position of empty block
        boolean found = false;
        LinkedQueue<Board> q = new LinkedQueue<Board>();

        // find the blank tile's position and record
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] == 0) {
                    ipos = i;
                    jpos = j;
                    found = true;
                    break;
                }
            }
        }

        if (!found)  {
            return null;
        }

        // if not the first row, swap with the upper element
        if (ipos > 0) {
            int[][] clone = cloneTiles();
            swap(clone, ipos, jpos, ipos - 1, jpos);  // exchange with upper block
            q.enqueue(new Board(clone));
        }

        // if not the last row, swap with the lower element
        if (ipos < size() - 1) {
            int[][] clone = cloneTiles();
            swap(clone, ipos, jpos, ipos + 1, jpos);
            q.enqueue(new Board(clone));
        }

        // if not the first column, swap with the left element
        if (jpos > 0) {
            int[][] clone = cloneTiles();
            swap(clone, ipos, jpos, ipos, jpos - 1);
            q.enqueue(new Board(clone));
        }

        // if not the last column, swap with the right element
        if (jpos < size() - 1) {
            int[][] clone = cloneTiles();
            swap(clone, ipos, jpos, ipos, jpos + 1);
            q.enqueue(new Board(clone));
        }

        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) other;
        return Arrays.equals(this.tiles, that.tiles);
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[size()][size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
