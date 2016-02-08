package whoop.whoop;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NaiveSolverTest {
  @Test
  public void canCallSolve() {
    NaiveSolver solver = new NaiveSolver();

    solver.solve(CanvasStringParser.parse("3 3\n...\n###\n..."));
  }

  @Test
  public void createsCommands() {
    NaiveSolver solver = new NaiveSolver();

    List<String> commands = solver.solve(CanvasStringParser.parse("3 3\n...\n###\n..."));
    Assert.assertTrue(commands.size() <= 3, "Should have as maximum the amount of colored pixels");
  }
}
