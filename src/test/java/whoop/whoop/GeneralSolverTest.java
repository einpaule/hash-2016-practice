package whoop.whoop;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public abstract class GeneralSolverTest {
  public abstract Solver getSolver();

  @Test
  public void canCallSolve() {
    getSolver().solve(CanvasStringParser.parse("3 3\n...\n###\n..."));
  }

  @Test
  public void createsCommands() {
    List<String> commands = getSolver().solve(CanvasStringParser.parse("3 3\n...\n###\n..."));
    Assert.assertTrue(commands.size() <= 3, "Should have as maximum the amount of colored pixels");
    Assert.assertTrue(commands.size() >= 1, "Should have as at least one command");
  }
}
