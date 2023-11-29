import java.util.Arrays;

public class NQueensCSP {
    private int N; // Number of queens
    private int[] positions; // Positions of queens in each column

    public NQueensCSP(int N) {
        this.N = N;
        positions = new int[N];
        Arrays.fill(positions, -1);
    }

    public void solve() {
        if (backtrack(0)) {
            System.out.println("Solution found:");
            printBoard();
        } else {
            System.out.println("No solution exists.");
        }
    }

    private boolean backtrack(int col) {
        if (col == N) {
            return true; // All queens are placed successfully
        }

        for (int row = 0; row < N; row++) {
            if (isSafe(col, row)) {
                positions[col] = row;

                if (backtrack(col + 1)) {
                    return true;
                }

                positions[col] = -1; // Backtrack
            }
        }

        return false; // Couldn't place queen in this column
    }

    private boolean isSafe(int col, int row) {
        for (int i = 0; i < col; i++) {
            if (positions[i] == row || Math.abs(i - col) == Math.abs(positions[i] - row)) {
                return false; // Same row or same diagonal
            }
        }
        return true;
    }

    private void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (positions[j] == i) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int N = 8; // Number of queens
        NQueensCSP nQueensCSP = new NQueensCSP(N);
        nQueensCSP.solve();
    }
}
