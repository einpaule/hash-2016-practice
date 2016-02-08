package whoop.whoop;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathHandler {
    private final Path basePath;

    public PathHandler(Path basePath) {
        this.basePath = basePath;
    }

    public boolean fileExists(String path) {
        File file = new File(basePath.toString(), path);
        return file.exists();
    }

    public String getContentFor(String path) throws IOException {
        File file = new File(basePath.toString(), path);

        if (file.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            for (File fileInDirectory : file.listFiles()) {
                sb.append(fileInDirectory.getName());
                sb.append("\n");
            }
            return sb.toString();
        } else {
            return readFile(file.toPath());
        }
    }

    // slightly modified from http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
    static String readFile(Path path)
            throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
