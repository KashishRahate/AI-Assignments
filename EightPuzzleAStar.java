import java.util.*;

class PuzzleNode {
    int[][] state;
    int cost;
    int heuristic;
    int totalCost;
    PuzzleNode parent;

    public PuzzleNode(int[][] state, int cost, int heuristic, PuzzleNode parent) {
        this.state = state;
        this.cost = cost;
        this.heuristic = heuristic;
        this.totalCost = cost + heuristic;
        this.parent = parent;
    }
}

public class EightPuzzleAStar {
    private static int[][] goalState = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

    private static int calculateHeuristic(int[][] state) {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] != goalState[i][j]) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    private static List<PuzzleNode> expandNode(PuzzleNode node) {
        List<PuzzleNode> successors = new ArrayList<>();
        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };

        int zeroX = -1, zeroY = -1;
        for (int i = 0; i < node.state.length; i++) {
            for (int j = 0; j < node.state[0].length; j++) {
                if (node.state[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int newX = zeroX + dx[i];
            int newY = zeroY + dy[i];

            if (newX >= 0 && newX < node.state.length && newY >= 0 && newY < node.state[0].length) {
                int[][] newState = new int[node.state.length][node.state[0].length];
                for (int j = 0; j < node.state.length; j++) {
                    newState[j] = Arrays.copyOf(node.state[j], node.state[j].length);
                }

                newState[zeroX][zeroY] = newState[newX][newY];
                newState[newX][newY] = 0;

                int cost = node.cost + 1;
                int heuristic = calculateHeuristic(newState);
                PuzzleNode successor = new PuzzleNode(newState, cost, heuristic, node);
                successors.add(successor);
            }
        }

        return successors;
    }

    private static List<int[][]> reconstructPath(PuzzleNode node) {
        List<int[][]> path = new ArrayList<>();
        while (node != null) {
            path.add(node.state);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public static List<int[][]> solvePuzzle(int[][] initial) {
        PriorityQueue<PuzzleNode> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.totalCost));
        Set<String> visited = new HashSet<>();
        PuzzleNode startNode = new PuzzleNode(initial, 0, calculateHeuristic(initial), null);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            PuzzleNode currentNode = openSet.poll();

            if (Arrays.deepEquals(currentNode.state, goalState)) {
                return reconstructPath(currentNode);
            }

            for (PuzzleNode successor : expandNode(currentNode)) {
                String stateString = Arrays.deepToString(successor.state);
                if (!visited.contains(stateString)) {
                    openSet.add(successor);
                    visited.add(stateString);
                }
            }
        }

        return null;
    }

    public static void printPuzzle(int[][] puzzle) {
        for (int[] row : puzzle) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("Initial State:");
        int[][] initialState = {
                { 1, 2, 3 },
                { 0, 5, 6 },
                { 4, 7, 8 }
        };

        List<int[][]> solution = solvePuzzle(initialState);

        if (solution != null) {
            for (int[][] state : solution) {
                printPuzzle(state);
                System.out.println("----");
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
