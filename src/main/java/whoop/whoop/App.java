package whoop.whoop;

import java.io.IOException;
import java.nio.file.Paths;

public class App {
  public static void main(String[] args) {
    String filename = args[0];
    String fileContents = getFileContents(filename);

    Canvas canvas = Canvas.createFromString(fileContents);
    System.out.println(canvas);
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
