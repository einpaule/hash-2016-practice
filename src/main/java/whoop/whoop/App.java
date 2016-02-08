package whoop.whoop;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class App {
  public static void main(String[] args) {
    String filename = args[0];
    String fileContents = getFileContents(filename);

    System.out.println(fileContents);

    boolean[][] canvas = CanvasStringParser.parse(fileContents);
    List<String> commands = new NaiveSolver().solve(canvas);

    System.out.println(commands);
  }

  private static String getFileContents(String filename) {
    PathHandler fileHandler = new PathHandler(Paths.get("."));
    try {
      return fileHandler.getContentFor(filename);
    } catch (IOException e) {
      throw new RuntimeException("fatal error, Could not find File", e);
    }
  }
}
