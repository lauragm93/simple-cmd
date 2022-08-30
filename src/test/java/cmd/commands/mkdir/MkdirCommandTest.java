package cmd.commands.mkdir;

import cmd.SimpleCmd;
import cmd.commands.AbstractCommandTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MkdirCommandTest extends AbstractCommandTest {


    @Test
    void testMkdirForDirectory(@TempDir Path tempDir) throws IOException {
        // given
        prepareTestFolder(tempDir);
        SimpleCmd.setCurrentLocation(tempDir.toFile());
        String[] args = {"hallo"};
        MkdirCommand mkdirCommand = CommandLine.populateCommand(new MkdirCommand(), args);
        // when
        mkdirCommand.run();
        // then
        assertTrue(SimpleCmd.getCurrentLocation().toPath().resolve("hallo").toFile().isDirectory());
    }

    @Test
    void testMkdirForFile(@TempDir Path tempDir) throws IOException {
        // given
        prepareTestFolder(tempDir);
        SimpleCmd.setCurrentLocation(tempDir.toFile());
        String[] args = {"hallo","-f"};
        MkdirCommand mkdirCommand = CommandLine.populateCommand(new MkdirCommand(), args);
        // when
        mkdirCommand.run();
        // then
        assertTrue(SimpleCmd.getCurrentLocation().toPath().resolve("hallo").toFile().isFile());
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