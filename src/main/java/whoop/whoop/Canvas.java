package whoop.whoop;

import java.util.Arrays;
import java.util.List;

public class Canvas {
  public final boolean[][] pixels;

  public static Canvas createFromString(String stringRepresentation) {
    List<String> rows = Arrays.asList(stringRepresentation.split("\n"));
    String countInformationLine = rows.get(0);

    int rowAmount = getRowAmount(countInformationLine);
    int columnAmount = getColumnAmount(countInformationLine);

    List<String> paintInformationLines = rows.subList(1, rows.size());

    boolean[][] pixels = new boolean[columnAmount][rowAmount];

    for (int row = 0; row < paintInformationLines.size(); row++) {
      char[] currentRow = paintInformationLines.get(row).toCharArray();
      for (int column = 0; column < currentRow.length; column++) {
        pixels[row][column] = currentRow[column] == '#';
      }
    }

    return new Canvas(pixels);
  }

  private static int getColumnAmount(String firstRow) {
    String columnAmountString = firstRow.split(" ")[0];
    int columnAmount = Integer.parseInt(columnAmountString);
    return columnAmount;
  }

  private static int getRowAmount(String firstRow) {
    String rowAmountString = firstRow.split(" ")[1];
    int rowAmount = Integer.parseInt(rowAmountString);
    return rowAmount;
  }

  private Canvas(boolean[][] pixels) {
    this.pixels = pixels;
  }

  @Override
  public String toString() {
    String representation = "Canvas [pixels=";

    for (int i = 0; i < pixels.length; i++) {
      representation += Arrays.toString(pixels[i]);
    }
    representation += "]";

    return representation;
  }

}
