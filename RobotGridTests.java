import org.junit.Assert;
import org.junit.Test;
 
/**
 * Unit tests for the RobotGrid class.
 * These tests validate the correctness of isSafe, totalSafeSquares, and shortestSafeJourney methods.
 */
public class RobotGridTests {
 
    RobotGrid robotGrid = new RobotGrid();
 
    @Test
    public void testIsSafe() {
        // Origin (0,0) is always safe since 0 * 0 = 0, sum = 0
        Assert.assertTrue(robotGrid.isSafe(0, 0));
 
        // Safe square: 3 * 4 = 12, digit sum = 1 + 2 = 3 < 19
        Assert.assertTrue(robotGrid.isSafe(3, 4));
 
        // Safe square: 99 * 99 = 9801, digit sum = 9 + 8 + 0 + 1 = 18 < 19
        Assert.assertTrue(robotGrid.isSafe(99, 99));
 
        // Safe square: 100 * 100 = 10000, digit sum = 1 + 0 + 0 + 0 + 0 = 1 < 19
        Assert.assertTrue(robotGrid.isSafe(100, 100));
 
        // Unsafe square: 123 * 456 = 56088, digit sum = 5 + 6 + 0 + 8 + 8 = 27 > 19
        Assert.assertFalse(robotGrid.isSafe(123, 456));
 
        // Negative coordinates: -3 * 6 = -18, digit sum = 1 + 8 = 9 < 19
        Assert.assertTrue(robotGrid.isSafe(-3, 6));
 
        // Edge case: 0 * 999 = 0, digit sum = 0 < 19
        Assert.assertTrue(robotGrid.isSafe(0, 999));
 
        // Out-of-bounds: x = 1000 is outside the grid limit
        Assert.assertFalse(robotGrid.isSafe(1000, 0));
 
        // Out-of-bounds: y = -1000 is outside the grid limit
        Assert.assertFalse(robotGrid.isSafe(0, -1000));
    }
 
    @Test
    public void testTotalSafeSquares() {
        // Calculate total number of safe squares reachable from (0,0)
        int total = robotGrid.totalSafeSquares();
        System.out.println("Total safe squares from (0,0): " + total);
 
        // There should be at least one safe square (the origin)
        Assert.assertTrue(total > 0);
 
        // There should be more than just the origin (some neighbors must be safe)
        Assert.assertTrue(total > 4);
    }
 
    @Test
    public void testShortestSafeJourney() {
        // Direct neighbor: (0,0) to (1,0) should take 1 step
        Assert.assertEquals(1, robotGrid.shortestSafeJourney(0, 0, 1, 0));
 
        // Diagonal move: (0,0) to (2,2) should be reachable in a few steps
        int steps = robotGrid.shortestSafeJourney(0, 0, 2, 2);
        Assert.assertTrue(steps > 0);
 
        // Out-of-bounds destination: should return -1
        Assert.assertEquals(-1, robotGrid.shortestSafeJourney(0, 0, 1000, 1000));
 
        // Unsafe start point: should return -1
        Assert.assertEquals(-1, robotGrid.shortestSafeJourney(999, 999, 0, 0));
 
        // Same start and end point: should return 0
        Assert.assertEquals(0, robotGrid.shortestSafeJourney(0, 0, 0, 0));
 
        // Destination is unsafe: should return -1
        int unsafeX = 123, unsafeY = 456;
        if (!robotGrid.isSafe(unsafeX, unsafeY)) {
            Assert.assertEquals(-1, robotGrid.shortestSafeJourney(0, 0, unsafeX, unsafeY));
        }
 
        // Nearby safe point with multiple paths: Manhattan distance is 6
        int pathLength = robotGrid.shortestSafeJourney(0, 0, 3, 3);
        Assert.assertTrue(pathLength > 0 && pathLength <= 6);
 
        // Longest possible safe journey within bounds (only if both ends are safe)
        if (robotGrid.isSafe(-999, -999) && robotGrid.isSafe(999, 999)) {
            int longPath = robotGrid.shortestSafeJourney(-999, -999, 999, 999);
            System.out.println("Path from (-999,-999) to (999,999): " + longPath);
            Assert.assertTrue(longPath > 0);
        }
    }
}
