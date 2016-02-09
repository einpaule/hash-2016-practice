package whoop.whoop;

import java.util.ArrayList;
import java.util.List;

public class LineSolver implements Solver {

  @Override
  public List<String> solve(final boolean[][] canvas) {
    List<Command> commands = new ArrayList<>();
    boolean[][] visited = new boolean[canvas.length][canvas[0].length];

    int canvasHeight = canvas.length;
    int canvasWidth = canvas[0].length;

    for (int distance = 0; distance < canvas.length + canvas[0].length - 1; distance++) {
      int startingRowForDistance = Math.max(0, distance - canvasWidth);
      int startingColumnForDistance = Math.min(distance - 1, canvasWidth - 1);

      for (int row = startingRowForDistance, col = startingColumnForDistance; stillOnDiagonalAndInCanvas(canvasHeight, canvasWidth, row, col); col--, row++) {
        if (isColored(canvas, row, col) && !visited[row][col]) {
          if (isColored(canvas, row, col + 1)) {
            commands.add(markAllToRightwhile(canvas, visited, col, row));
          } else if (isColored(canvas, row + 1, col)) {
            commands.add(markAllBelow(canvas, visited, col, row));
          } else {
            commands.add(markAllBelow(canvas, visited, col, row));
          }
        }
        visited[row][col] = true;
      }
    }

    List<String> commandStrings = new ArrayList<>();
    for (Command command : commands) {
      commandStrings.add(command.toString());
    }
    return commandStrings;
  }

  private boolean stillOnDiagonalAndInCanvas(int canvasHeight, int canvasWidth, int row, int column) {
    boolean valuesArePositive = column >= 0 && row >= 0;
    boolean valuesAreWithinMax = column < canvasWidth && row < canvasHeight;

    return valuesArePositive && valuesAreWithinMax;
  }

  private Command markAllBelow(final boolean[][] canvas, boolean[][] visited, int column, int row) {
    for (int currRow = row; currRow < canvas.length; currRow++) {
      visited[currRow][column] = true;
      if (!isColored(canvas, currRow + 1, column)) {
        return new PaintLine(row, column, currRow, column);
      }
    }
    throw new RuntimeException("Should never happen, dude!");
  }

  private Command markAllToRightwhile(final boolean[][] canvas, boolean[][] visited, int column, int row) {
    for (int currColumn = column; currColumn < canvas[0].length; currColumn++) {
      visited[row][currColumn] = true;
      if (!isColored(canvas, row, currColumn + 1) || visited[row][currColumn + 1]) {
        return new PaintLine(row, column, row, currColumn);
      } else {
        visited[row][currColumn] = true;
      }
    }
    throw new RuntimeException("Should never happen, dude!");
  }

  private boolean isColored(boolean[][] canvas, int row, int column) {
    if (canvas.length <= row)
      return false;
    if (canvas[0].length <= column)
      return false;

    return canvas[row][column];
  }
}
