//import stdlib.In;
//import stdlib.StdOut;
//import java.util.Arrays;
//import dsa.LinkedQueue;
//
//// A data type to represent a board in the 8-puzzle game or its generalizations.
//
//public class Board {
//    int N;
//    int[] board;
//
//    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
//    // denoting the blank tile.
//    public Board(int[][] tiles) {
//        N = tiles.length;
//        board = new int[N * N];
//        for(int i = 0; i < N; i++){
//            for(int j = 0; j < N; j++)
//                board[i * N + j] = tiles[i][j];
//        }
//
////        solver = new Solver(this);
//    }
//
//    private Board(int[] board) {            // private constructor useful in twin()
//        N = (int) Math.sqrt(board.length);
//        this.board = new int[board.length];
//        for (int i = 0; i < board.length; i++)
//            this.board[i] = board[i];
//
//    }
//
//    // Returns the size of this board.
//    public int size() {
//        return N * N;
//    }
//
//    // Returns the tile at row i and column j of this board.
//    public int tileAt(int i, int j) {
//        return board[i * N + j];
//    }
//
//    // Returns Hamming distance between this board and the goal board.
//    public int hamming() {
//        int sum = 0;
//        for (int i = 0; i < N * N; i++)      // compare board[1] through board[N^2-1] with goal
//            if (board[i] != i + 1 && board[i] != 0)                  // count for blocks in wrong place
//                sum++;
//        return sum;
//    }
//
//    // Returns the Manhattan distance between this board and the goal board.
//    public int manhattan() {
//        int sum = 0;
//        for(int i = 0; i < N * N; i++){
//            if(board[i] != i + 1 && board[i] != 0)
//                sum += manhattan(board[i], i);
//        }
//        return sum;
//    }
//
//    public int manhattan(int goal, int current){
//        int row = 0;
//        int col = 0;
//        row = Math.abs((goal - 1) / N - current/N);
//        col = Math.abs((goal - 1) / N - current/N);
//        return row + col;
//    }
//
//    // Returns true if this board is the goal board, and false otherwise.
//    public boolean isGoal() {
//        for (int i = 0; i < N * N - 1; i++)
//            if (board[i] != i + 1)
//                return false;
//        return true;
//    }
//
//    // Returns true if this board is solvable, and false otherwise.
//    public boolean isSolvable() {
//        return true;
//    }
//
//    private Board exchange(Board a, int i, int j) { // exchange two elements in the array
//        int temp = a.board[i];
//        a.board[i] = a.board[j];
//        a.board[j] = temp;
//        return a;
//    }
//
//    // Returns an iterable object containing the neighboring boards of this board.
//    public Iterable<Board> neighbors() {
//        int index = 0;                               // record the position of empty block
//        boolean found = false;                       // if empty block is found
//        Board neighbor;
//        LinkedQueue<Board> q = new LinkedQueue<Board>();
//
//        for (int i = 0; i < board.length; i++)    // search for empty block
//            if (board[i] == 0) {
//                index = i;
//                found = true;
//                break;
//            }
//        if (!found)  return null;
//
//        if (index / N != 0) {                      // if not first row
//            neighbor = new Board(board);
//            exchange(neighbor, index, index - N);  // exchange with upper block
//            q.enqueue(neighbor);
//        }
//
//        if (index / N != (N - 1)) {               // if not last row
//            neighbor = new Board(board);
//            exchange(neighbor, index, index + N);  // exchange with lower block
//            q.enqueue(neighbor);
//        }
//
//        if ((index % N) != 0) {                        // if not leftmost column
//            neighbor = new Board(board);
//            exchange(neighbor, index, index - 1);  // exchange with left block
//            q.enqueue(neighbor);
//        }
//
//        if ((index % N) != N - 1) {                          // if not rightmost column
//            neighbor = new Board(board);
//            exchange(neighbor, index, index + 1);  // exchange with left block
//            q.enqueue(neighbor);
//        }
//
//        return q;
//    }
//
//    // Returns true if this board is the same as other, and false otherwise.
//    public boolean equals(Object other) {
//        if (other == null) {
//            return false;
//        }
//        if (other == this) {
//            return true;
//        }
//        if (other.getClass() != this.getClass()) {
//            return false;
//        }
//
//        Board that = (Board) other;
//        return Arrays.equals(this.board, that.board);
//    }
//
//    // Returns a string representation of this board.
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (int i = 0; i < board.length; i++) {
//            s.append(board[i] + " ");
//            if (((i + 1) % N) == 0)
//                s.append("\n");
//        }
//        return s.toString();
//    }
//
//    // Returns a defensive copy of tiles[][].
//    private int[] cloneTiles() {
//        int[] clone = new int[N * N];
//        for (int i = 0; i < board.length; i++) {
//            clone[i] = board[i];
//        }
//        return clone;
//    }
//
//    // Unit tests the data type. [DO NOT EDIT]
//    public static void main(String[] args) {
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                tiles[i][j] = in.readInt();
//            }
//        }
//        Board board = new Board(tiles);
//        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
//        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
//        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
//        StdOut.println("Neighboring boards:");
//        for (Board neighbor : board.neighbors()) {
//            StdOut.println(neighbor);
//            StdOut.println("----------");
//        }
//    }
//}
//
import stdlib.In;
import stdlib.StdOut;
import java.util.Arrays;
import dsa.LinkedQueue;

// A data type to represent a board in the 8-puzzle game or its generalizations.

public class Board {
    int[][] board;

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.board = new int[tiles.length][tiles.length];
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[0].length; j++)
                board[i][j] = tiles[i][j];
    }
    // Returns the size of this board.
    public int size() {
        return board.length;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return board[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        int sum = 0;

        for(int i = 0; i < size(); i++)
            for(int j = 0; j < size(); j++)
                if(board[i][j] != size() * i + j + 1 && board[i][j] != 0)
                    sum++;
        return sum;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        int sum = 0;
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++)
                if(board[i][j] != size() * i + j + 1 && board[i][j] != 0)
                    sum += manhattan(i, j, board[i][j]);
        }
        return sum;
    }

    public int manhattan(int i, int j, int goal){
        int targetRow = (goal - 1)/ size();
        int targetCol = (goal - 1) % size();
        return Math.abs(targetCol - j) + Math.abs(targetRow - i);
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++){
                if(i == size() - 1 && j == size() - 1)
                    continue;
                if (board[i][j] == 0 || board[i][j] != size() * i + j + 1)
                    return false;
            }
        }
        return true;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int inversions = 0;

        // find the inversions
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++){
                for(int k = i; k < size(); k++){
                    for(int m = j + 1; m < size(); m++){
                        if(board[k][m] != 0 && board[k][m] <  board[i][j])
                            inversions++;
                    }
                }
            }
        }

        // if the size is odd and the number of inversions is odd
        if(size() % 2 == 1 && inversions % 2 == 1)
            return false;

        // if the sum is even and size is even
        int row = 0;
        for(int i = 0; i < size(); i++)
            for(int j = 0; j < size(); j++)
                if(board[i][j] == 0){
                    row = i;
                    break;
                }


        if(size() % 2 == 0 && (inversions + row) % 2 == 0)
            return false;

        return true;
    }


    // swap two elements
    private Board swap(Board a, int i, int j, int targeti, int targetj) {
        int temp = a.board[i][j];
        a.board[i][j] = a.board[targeti][targetj];
        a.board[targeti][targetj] = temp;
        return a;
    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        int ipos = 0;
        int jpos = 0;// record the position of empty block
        boolean found = false;
        LinkedQueue<Board> q = new LinkedQueue<Board>();

        // find the blank tile's position and record
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++)
                if (board[i][j] == 0) {
                    ipos = i;
                    jpos = j;
                    found = true;
                    break;
                }
        }

        if (!found)  return null;

        // if not the first row, swap with the upper element
        if (ipos > 0) {
            Board neighbor = new Board(board);
            swap(neighbor, ipos, jpos, ipos - 1, jpos);  // exchange with upper block
            q.enqueue(neighbor);
        }

        // if not the last row, swap with the lower element
        if (ipos < size() - 1) {
            Board neighbor = new Board(board);
            swap(neighbor, ipos, jpos,ipos + 1, jpos);
            q.enqueue(neighbor);
        }

        // if not the first column, swap with the left element
        if (jpos > 0) {
            Board neighbor = new Board(board);
            swap(neighbor, ipos, jpos, ipos, jpos - 1);
            q.enqueue(neighbor);
        }

        // if not the last column, swap with the right element
        if (jpos < size() - 1) {
            Board neighbor = new Board(board);
            swap(neighbor, ipos, jpos, ipos, jpos + 1);
            q.enqueue(neighbor);
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
        return Arrays.equals(this.board, that.board);
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            for(int j = 0; j < size(); j++)
                s.append(board[i][j] + " ");
            s.append("\n");
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[size()][size()];
        for (int i = 0; i < size(); i++) {
            for(int j = 0; i < size(); j++)
                clone[i][j] = board[i][j];
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
