package whoop.whoop;

import java.util.ArrayList;
import java.util.List;

public class NaiveSolver implements Solver {

  @Override
  public List<String> solve(boolean[][] canvas) {
    List<String> commands = new ArrayList<>();
    for (int row = 0; row < canvas.length; row++) {
      for (int col = 0; col < canvas[row].length; col++) {
        if (canvas[row][col]) {
          commands.add("PAINT_SQUARE " + row + " " + col + " 0");
        }
      }
    }

    return commands;
  }
}
