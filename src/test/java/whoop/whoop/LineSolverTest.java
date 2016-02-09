package whoop.whoop;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LineSolverTest extends GeneralSolverTest {
  public Solver getSolver() {
    return new LineSolver();
  }

  @Test
  public void findsTheOneLine() {
    Solver solver = getSolver();

    List<String> commands = solver.solve(CanvasStringParser.parse("3 3\n...\n###\n..."));

    Assert.assertEquals(commands.size(), 1);
    Assert.assertEquals(commands.get(0), "PAINT_LINE 1 0 1 2");
  }

  @Test
  public void findsTheOtherLine() {
    Solver solver = getSolver();

    List<String> commands = solver.solve(CanvasStringParser.parse("3 3\n..#\n..#\n..#"));

    Assert.assertEquals(commands.size(), 1);
    Assert.assertEquals(commands.get(0), "PAINT_LINE 0 2 2 2");
  }

  @Test
  public void paintsDiagonalLineWithThreeCommands() {
    Solver solver = getSolver();

    List<String> commands = solver.solve(CanvasStringParser.parse("3 3\n#..\n.#.\n..#"));

    Assert.assertEquals(commands.size(), 3);
    Assert.assertEquals(commands.get(0), "PAINT_LINE 0 0 0 0");
    Assert.assertEquals(commands.get(1), "PAINT_LINE 1 1 1 1");
    Assert.assertEquals(commands.get(2), "PAINT_LINE 2 2 2 2");
  }

  @Test
  public void canFindDiagonalLinesInWideImage() {
    Solver solver = getSolver();

    List<String> commands = solver.solve(CanvasStringParser.parse("3 10\n....#.....\n.....#....\n......#..."));

    Assert.assertEquals(commands.size(), 3);
    Assert.assertEquals(commands.get(0), "PAINT_LINE 0 4 0 4");
    Assert.assertEquals(commands.get(1), "PAINT_LINE 1 5 1 5");
    Assert.assertEquals(commands.get(2), "PAINT_LINE 2 6 2 6");
  }

  @Test
  public void findsTheGoogleLogoCommands() throws IOException {
    Solver solver = getSolver();
    PathHandler fileHandler = new PathHandler(Paths.get("."));

    String googleLogo = fileHandler.getContentFor("logo.in");
    List<String> commands = solver.solve(CanvasStringParser.parse(googleLogo));

    Assert.assertTrue(commands.size() > 10);
    System.out.println(commands);
  }
}
