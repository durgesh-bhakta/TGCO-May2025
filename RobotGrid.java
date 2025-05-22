import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
 
/**
 * RobotGrid simulates a robot navigating a bounded 2D grid from -999 to +999.
 * The robot can only move to "safe" squares, where the sum of the digits
 * of the product of the coordinates is less than 19.
 */
public class RobotGrid {
 
    private static final int MIN = -999;
    private static final int MAX = 999;
 
    /**
     * Checks if a square at coordinates (x, y) is safe.
     * A square is safe if the sum of the digits of the product x * y is less than 19.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return true if the square is safe, false otherwise
     */
    public boolean isSafe(int x, int y) {
        if (x < MIN || x > MAX || y < MIN || y > MAX) return false;
 
        int product = x * y;
        int sum = 0;
 
        for (char c : String.valueOf(Math.abs(product)).toCharArray()) {
            sum += c - '0';
        }
 
        return sum < 19;
    }
 
    /**
     * Calculates the total number of safe squares reachable from the origin (0, 0).
     * Uses Breadth-First Search (BFS) to explore all reachable safe squares.
     *
     * @return The number of safe squares reachable from (0, 0)
     */
    public int totalSafeSquares() {
        Set<String> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
 
        queue.add(new int[]{0, 0});
        visited.add("0,0");
 
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
 
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
 
            for (int[] dir : directions) {
                int nx = current[0] + dir[0];
                int ny = current[1] + dir[1];
                String key = nx + "," + ny;
 
                if (!visited.contains(key) && isSafe(nx, ny)) {
                    visited.add(key);
                    queue.add(new int[]{nx, ny});
                }
            }
        }
 
        return visited.size();
    }
 
    /**
     * Finds the shortest safe path from (a, b) to (x, y) using BFS.
     *
     * @param a Starting x-coordinate
     * @param b Starting y-coordinate
     * @param x Target x-coordinate
     * @param y Target y-coordinate
     * @return The length of the shortest safe path, or -1 if unreachable
     */
    public int shortestSafeJourney(int a, int b, int x, int y) {
        if (!isSafe(a, b) || !isSafe(x, y)) return -1;
 
        Set<String> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
 
        queue.add(new int[]{a, b, 0});
        visited.add(a + "," + b);
 
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
 
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
 
            if (current[0] == x && current[1] == y) return current[2];
 
            for (int[] dir : directions) {
                int nx = current[0] + dir[0];
                int ny = current[1] + dir[1];
                String key = nx + "," + ny;
 
                if (!visited.contains(key) && isSafe(nx, ny)) {
                    visited.add(key);
                    queue.add(new int[]{nx, ny, current[2] + 1});
                }
            }
        }
 
        return -1;
    }
 
    /**
     * Main method to manually test the RobotGrid functionality.
     */
    public static void main(String[] args) {
        RobotGrid robot = new RobotGrid();
 
        // Test isSafe
        System.out.println("Is (3, 4) safe? " + robot.isSafe(3, 4)); // Expected: true
        System.out.println("Is (123, 456) safe? " + robot.isSafe(123, 456)); // Expected: false
 
        // Test totalSafeSquares
        int totalSafe = robot.totalSafeSquares();
        System.out.println("Total safe squares reachable from (0,0): " + totalSafe);
 
        // Test shortestSafeJourney
        int journey1 = robot.shortestSafeJourney(0, 0, 2, 2);
        System.out.println("Shortest safe journey from (0,0) to (2,2): " + journey1);
 
        int journey2 = robot.shortestSafeJourney(0, 0, 1000, 1000);
        System.out.println("Shortest safe journey from (0,0) to (1000,1000): " + journey2); // Expected: -1
 
        int journey3 = robot.shortestSafeJourney(0, 0, 0, 0);
        System.out.println("Shortest safe journey from (0,0) to (0,0): " + journey3); // Expected: 0
    }
}
 
