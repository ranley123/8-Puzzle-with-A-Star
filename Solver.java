import dsa.LinkedQueue;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {
    Node goal;
    
    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board board) {
        MinPQ<Node> PQ = new MinPQ<Node>();
        Node Node = new Node(board, 0, null);
        PQ.insert(Node);
        // get the best choice
        Node min = PQ.delMin();

        while(!min.board.isGoal()) {
            for (Board b : min.board.neighbors()) {
                // a critical optimization to prevent duplication of boards
                if (min.prev == null || !b.equals(min.prev.board)) {
                    Node n = new Node(b, 0, null);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    PQ.insert(n);
                }
            }
            min = PQ.delMin();
        }

        // if the goal is achieved
        if (min.board.isGoal())
            goal = min;
        else
            goal = null;
    }

    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        if (goal == null)
            return -1;
        else
            return goal.moves;
    }

    // Returns a sequence of boards in a shortest solution of the initial board.
    public Iterable<Board> solution() {
        if (goal == null)  return null;
        LinkedQueue<Board> res = new LinkedQueue<>();
        for (Node n = goal; n != null; n = n.prev)
            res.enqueue(n.board);
        return res;
    }

    // A data type that represents a search node in the grame tree. Each node includes a
    // reference to a board, the number of moves to the node from the initial node, and a
    // reference to the previous node.
    private class Node implements Comparable<Node> {
        private int moves;                 // this step and pointed to the previous search node
        private Board board;
        private Node prev;

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.prev = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        //   Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(Node other) {
            int pa = this.board.manhattan() + this.moves;
            int pb = other.board.manhattan() + other.moves;
            if (pa > pb)   return 1;
            if (pa < pb)   return -1;
            else              return 0;
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
            StdOut.println("Unsolvable puzzle");
        }
    }
}
