package cmd.commands.cd;

import cmd.SimpleCmd;
import cmd.commands.AbstractCommandTest;
import cmd.commands.dir.DirCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CdCommandTest extends AbstractCommandTest {

    @Test
    void testCdWithAbsolutePath(@TempDir Path tempDir) throws IOException {
        // given
        prepareTestFolder(tempDir);
        SimpleCmd.setCurrentLocation(tempDir.toFile());
        String[] args = {"C:\\"};
        CdCommand CdCommand = CommandLine.populateCommand(new CdCommand(), args);
        // when
        CdCommand.run();
        // then
        String expected = "C:\\";
        String actual = SimpleCmd.getCurrentLocation().toString();
        assertTrue(actual.equals(expected), "Expected : " + expected + " But was: " + actual);
    }

    @Test
    void testCdWithRelativePath(@TempDir Path tempDir) throws IOException {
        // given
        prepareTestFolder(tempDir);
        SimpleCmd.setCurrentLocation(tempDir.toFile());
        String[] args = {".."};
        CdCommand CdCommand = CommandLine.populateCommand(new CdCommand(), args);
        // when
        CdCommand.run();
        // then
        String expected = tempDir.toString().concat("\\..");
        String actual = SimpleCmd.getCurrentLocation().toString();
        assertTrue(actual.equals(expected), "Expected : " + expected + " But was: " + actual);
    }

    private void prepareTestFolder(@TempDir Path tempDir) throws IOException {
        // for other possible usages of @TempDir see https://www.baeldung.com/junit-5-temporary-directory
        Path myFile = tempDir.resolve("myFile.txt");
        Files.write(myFile, Collections.singletonList(""));

        Path folder1 = tempDir.resolve("folder1");
        Path directory = Files.createDirectory(folder1, noAttributes);

        Path myFile2 = directory.resolve("myFile2.txt");
        Files.write(myFile2, Collections.singletonList(""));
    }
}