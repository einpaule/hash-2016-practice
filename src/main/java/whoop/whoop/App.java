package whoop.whoop;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class App {
  public static void main(String[] args) {
    if (args.length == 0) {
      args = new String[] { "logo.in", "learn_and_teach.in", "right_angle.in" };
    }
    for (String filename : args) {
      String fileContents = getFileContents(filename);

      boolean[][] canvas = CanvasStringParser.parse(fileContents);
      List<String> commands = new LineSolver().solve(canvas);

      System.out.println("File: " + filename);
      System.out.println("Commands: " + commands.size());
      System.out.print("ruby ./build_image.rb " + canvas.length + " " + canvas[0].length + " '");
      String divider = "";
      for (String command : commands) {
        System.out.print(divider);
        System.out.print(command);
        divider = ";";
      }
      System.out.println("'");
    }
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
