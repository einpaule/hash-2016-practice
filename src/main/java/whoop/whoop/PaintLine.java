package whoop.whoop;

public class PaintLine extends Command {
  private int column1;
  private int row1;
  private int row2;
  private int column2;

  public PaintLine(int row1, int column1, int row2, int column2) {
    this.row1 = row1;
    this.column1 = column1;
    this.row2 = row2;
    this.column2 = column2;
  }

  public String toString() {
    return "PAINT_LINE " + row1 + " " + column1 + " " + row2 + " " + column2;
  }
}
