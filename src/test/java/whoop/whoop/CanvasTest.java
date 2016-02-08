package whoop.whoop;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CanvasTest {
  @Test
  public void canConstruct() {
    Canvas c = Canvas.createFromString("0 0 \n");
    Assert.assertNotNull(c);
  }

  @Test
  public void canCreate3x3Canvas() {
    String simpleCanvas = "3 3 \n...\n...\n...\n";

    Canvas c = Canvas.createFromString(simpleCanvas);
    Assert.assertEquals(c.pixels.length, 3);
    Assert.assertEquals(c.pixels[0].length, 3);
  }

  @Test
  public void canCreate2x5Canvas() {
    String simpleCanvas = "2 5\n.....\n.....\n";

    Canvas c = Canvas.createFromString(simpleCanvas);

    boolean[][] pixels = c.pixels;
    Assert.assertEquals(pixels.length, 2);
    Assert.assertEquals(pixels[0].length, 5);
  }

  @Test
  public void canCreate2x2Canvas() {
    String simpleCanvas = "2 2\n..\n..\n";

    Canvas c = Canvas.createFromString(simpleCanvas);

    boolean[][] pixels = c.pixels;
    Assert.assertEquals(pixels.length, 2);
    Assert.assertEquals(pixels[0].length, 2);
  }

  @Test
  public void canCreateAllWhiteCanvas() {
    String simpleCanvas = "2 2\n..\n..\n";

    Canvas c = Canvas.createFromString(simpleCanvas);

    boolean[][] pixels = c.pixels;
    for (boolean[] row : pixels) {
      for (boolean pixel : row) {
        Assert.assertFalse(pixel);
      }
    }
  }

  @Test
  public void canCreateAllColoredCanvas() {
    String simpleCanvas = "2 2\n##\n##\n";

    Canvas c = Canvas.createFromString(simpleCanvas);

    boolean[][] pixels = c.pixels;
    for (boolean[] row : pixels) {
      for (boolean pixel : row) {
        Assert.assertTrue(pixel);
      }
    }
  }

  @Test
  public void canCreateWithColoredColumn() {
    String simpleCanvas = "2 2\n#.\n#.\n";

    Canvas c = Canvas.createFromString(simpleCanvas);

    boolean[][] pixels = c.pixels;

    Assert.assertTrue(pixels[0][0]);
    Assert.assertFalse(pixels[0][1]);
    Assert.assertTrue(pixels[1][0]);
    Assert.assertFalse(pixels[1][1]);
  }
  
  @Test
  public void canCreateWithColoredRow() {
    String simpleCanvas = "2 2\n##\n..\n";

    Canvas c = Canvas.createFromString(simpleCanvas);

    boolean[][] pixels = c.pixels;

    Assert.assertTrue(pixels[0][0]);
    Assert.assertTrue(pixels[0][1]);
    Assert.assertFalse(pixels[1][0]);
    Assert.assertFalse(pixels[1][1]);
  }
}
