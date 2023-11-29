public class MazeSolverDFS {
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right

    private int rows;
    private int cols;
    private char[][] maze;
    private boolean[][] visited;

    public MazeSolverDFS(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.visited = new boolean[rows][cols];
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] != 'W' && !visited[x][y];
    }

    private boolean solveMazeDFS(int x, int y, int endX, int endY) {
        if (x == endX && y == endY) {
            visited[x][y] = true;
            return true; // Reached the exit
        }

        visited[x][y] = true;

        for (int[] dir : DIRECTIONS) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (isValidMove(newX, newY)) {
                if (solveMazeDFS(newX, newY, endX, endY)) {
                    return true;
                }
            }
        }

        return false; // No valid moves, backtrack
    }

    public void solveMaze(int startX, int startY, int endX, int endY) {
        if (!isValidMove(startX, startY) || !isValidMove(endX, endY)) {
            System.out.println("Invalid starting or ending point.");
            return;
        }

        if (solveMazeDFS(startX, startY, endX, endY)) {
            System.out.println("Path found! Maze solution:");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (i == endX && j == endY) {
                        System.out.print("E ");
                    } else {
                        System.out.print(visited[i][j] ? "P " : maze[i][j] + " ");
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("No path found.");
        }
    }

    public static void main(String[] args) {
        char[][] maze = {
                { 'S', '.', '.', '.', '.', '.' },
                { 'W', 'W', 'W', 'W', '.', 'W' },
                { '.', '.', '.', '.', '.', '.' },
                { '.', '.', 'W', '.', 'W', '.' },
                { 'E', 'W', 'W', 'W', 'W', '.' }
        };

        MazeSolverDFS mazeSolver = new MazeSolverDFS(maze);
        mazeSolver.solveMaze(0, 0, 4, 0); // Starting point: (0,0), Ending point: (0,5)
    }
}
