import java.util.LinkedList;
import java.util.Queue;

public class KnightTourBFS {
    private static final int SIZE = 8; // Size of the chessboard
    private static final int[] dx = { -2, -1, 1, 2, 2, 1, -1, -2 };
    private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    private static class Cell {
        int x, y, dist;

        Cell(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public static void printKnightTour(int startX, int startY) {
        int[][] board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = -1; // Initialize all cells as unvisited
            }
        }

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(startX, startY, 0));
        board[startX][startY] = 0;

        while (!queue.isEmpty()) {
            Cell currCell = queue.poll();

            for (int i = 0; i < 8; i++) {
                int newX = currCell.x + dx[i];
                int newY = currCell.y + dy[i];

                if (isValid(newX, newY) && board[newX][newY] == -1) {
                    board[newX][newY] = currCell.dist + 1;
                    queue.offer(new Cell(newX, newY, currCell.dist + 1));
                }
            }
        }

        // Print the knight's tour
        System.out.println("Knight's Tour (starting from " + startX + ", " + startY + "):");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%2d ", board[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int startX = 0;
        int startY = 0;

        // Starting position of the knight
        System.out.println("Starting position: " + startX + ", " + startY);

        // Find and print the Knight's Tour
        printKnightTour(startX, startY);
    }
}
