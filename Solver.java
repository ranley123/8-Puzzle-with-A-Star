import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {
    SearchNode goal = null; // the goal board
    LinkedStack<Board> solution = new LinkedStack<>(); // the solution stack
    int moves = 0; // the minimum moves

    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("board is null");
        }
        MinPQ<SearchNode> PQ = new MinPQ<SearchNode>();
        SearchNode node = new SearchNode(board, 0, null);
        PQ.insert(node);

        while (!PQ.isEmpty()) {
            node = PQ.delMin();
            if (node.board.isGoal()) {
                moves = node.moves;
                goal = node;
                break;
            }
            for (Board b :node.board.neighbors()) {
                if (node.previous == null || !b.equals(node.previous.board)) {
                    PQ.insert(new SearchNode(b, node.moves + 1,  node));
                }
            }
        }
    }

    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        return goal == null? -1: goal.moves;
    }

    // Returns a sequence of boards in a shortest solution of the initial board.
    public Iterable<Board> solution() {
        if (goal == null)  {
            return null;
        }
        SearchNode cur = goal;
        while (cur != null) {
            solution.push(cur.board);
            cur = cur.previous;
        }
        return solution;
    }

    // A data type that represents a search node in the grame tree. Each node includes a
    // reference to a board, the number of moves to the node from the initial node, and a
    // reference to the previous node.
    private class SearchNode implements Comparable<SearchNode> {
        private int moves;                 // this step and pointed to the previous search node
        private Board board;
        private SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        //   Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(SearchNode other) {
            int a = this.board.manhattan + this.moves;
            int b = other.board.manhattan + other.moves;
            if (a > b) {
                return 1;
            }
            if (a < b) {
                return -1;
            }
            return 0;
        }
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
        Board initial = new Board(tiles);

        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            throw new IllegalArgumentException("Unsolvable puzzle");
        }
    }
}
