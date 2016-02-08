package whoop.whoop;


import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PathHandlerTest {
    static final Path TEST_PATH = Paths.get("unitTestDirectory/");
    static final String BASE_PATH = "";

    @BeforeMethod
    @AfterMethod
    public void setUpTestDirectory() throws IOException {
        if (!Files.exists(TEST_PATH)) {
            Files.createDirectory(TEST_PATH);
        }
        FileUtils.cleanDirectory(TEST_PATH.toFile());
    }

    @AfterTest
    public void deleteTestDirectory() throws IOException {
        FileUtils.deleteDirectory(TEST_PATH.toFile());
    }

    @Test
    public void returnsEmptyStringForEmptyFolder() throws IOException {
        PathHandler handler = new PathHandler(TEST_PATH);

        String folderContents = handler.getContentFor(BASE_PATH);
        Assert.assertEquals(folderContents, "");
    }

    @Test(expectedExceptions = IOException.class)
    public void throwsExceptionForUnknownPath() throws IOException {
        PathHandler handler = new PathHandler(TEST_PATH);

        handler.getContentFor("/nonexisting_file.xml");
    }

    @Test
    public void getsFilenamesInFolder() throws IOException {
        PathHandler handler = new PathHandler(TEST_PATH);

        Files.createFile(Paths.get(TEST_PATH + "/newFile.txt"));

        Files.createFile(Paths.get(TEST_PATH + "/newFile2.txt"));

        String folderContents = handler.getContentFor("/");
        Assert.assertTrue(folderContents.contains("newFile.txt"), "Has newFile.txt file in list of files: " + folderContents);
        Assert.assertTrue(folderContents.contains("newFile2.txt"), "Has newFile2.txt file in list of files: " + folderContents);
    }

    @Test
    public void getsFolderNamesInFolder() throws IOException {
        PathHandler handler = new PathHandler(TEST_PATH);

        Path newDirectoryPath = Paths.get(TEST_PATH + "/newDirectory");
        Files.createDirectory(newDirectoryPath);

        String folderContents = handler.getContentFor("/");
        Assert.assertTrue(folderContents.contains("newDirectory"), "Has new newDirectory in list of files: " + folderContents);
    }

    @Test
    public void getsContentForFiles() throws IOException {
        PathHandler handler = new PathHandler(TEST_PATH);

        Path newFilePath = Paths.get(TEST_PATH + "/newFile.txt");
        Files.write(newFilePath, "my content".getBytes(Charsets.UTF_8), StandardOpenOption.CREATE);

        String folderContents = handler.getContentFor("/newFile.txt");
        Assert.assertEquals(folderContents, "my content");
    }
}
