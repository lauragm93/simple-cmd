package cmd.commands.rename;

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

class RenameCommandTest extends AbstractCommandTest {

    public static final String FILE_NACH_RENAMING = "hallo.nachher";
    public static final String FILE_VOR_RENAMING = "hallo.vorher";
    public static final String BEREITS_EXISTIERENDES_FILE = "weiteres.file";
    public static final long LAST_MODIFIED = 0L;

    @Test
    void testRenaming(@TempDir Path tempDir) throws IOException {
        // given
        prepareTestFolder(tempDir);
        SimpleCmd.setCurrentLocation(tempDir.toFile());
        String[] args = {FILE_VOR_RENAMING,FILE_NACH_RENAMING};
        RenameCommand renameCommand = CommandLine.populateCommand(new RenameCommand(), args);
        // when
        renameCommand.run();
        // then
        assertTrue(SimpleCmd.getCurrentLocation().toPath().resolve(FILE_NACH_RENAMING).toFile().isFile());
        assertFalse(SimpleCmd.getCurrentLocation().toPath().resolve(FILE_VOR_RENAMING).toFile().exists());
    }


    @Test
    void testRenamingFileDasUnbenanntWerdenSollExistiertNichtSollKeinNeuesFIleEntstehen(@TempDir Path tempDir) throws IOException {
        // given
        //prepareTestFolder(tempDir);
        SimpleCmd.setCurrentLocation(tempDir.toFile());
        String[] args = {FILE_VOR_RENAMING,FILE_NACH_RENAMING};
        RenameCommand renameCommand = CommandLine.populateCommand(new RenameCommand(), args);
        // when
        renameCommand.run();
        // then
        assertFalse(SimpleCmd.getCurrentLocation().toPath().resolve(FILE_NACH_RENAMING).toFile().exists());
        assertFalse(SimpleCmd.getCurrentLocation().toPath().resolve(FILE_VOR_RENAMING).toFile().exists());
    }

    @Test
    void nameDenDasNeueFileHabenSollExistiertBereitsAlsoKeineUmbenennung(@TempDir Path tempDir) throws IOException {
        // given
        prepareTestFolder(tempDir);
        SimpleCmd.setCurrentLocation(tempDir.toFile());
        String[] args = {FILE_VOR_RENAMING, BEREITS_EXISTIERENDES_FILE};
        RenameCommand renameCommand = CommandLine.populateCommand(new RenameCommand(), args);
        // when
        renameCommand.run();
        // then
        assertFalse(SimpleCmd.getCurrentLocation().toPath().resolve(FILE_NACH_RENAMING).toFile().exists());
        assertTrue(SimpleCmd.getCurrentLocation().toPath().resolve(FILE_VOR_RENAMING).toFile().exists(),FILE_VOR_RENAMING + "sollte existieren");
        assertTrue(SimpleCmd.getCurrentLocation().toPath().resolve(BEREITS_EXISTIERENDES_FILE).toFile().exists());
        assertEquals(LAST_MODIFIED, SimpleCmd.getCurrentLocation().toPath().resolve(BEREITS_EXISTIERENDES_FILE).toFile().lastModified());
    }

    private void prepareTestFolder(@TempDir Path tempDir) throws IOException {
        Path myFile = tempDir.resolve(FILE_VOR_RENAMING);
        Files.write(myFile, Collections.singletonList(""));

        Path weiteresFile = tempDir.resolve(BEREITS_EXISTIERENDES_FILE);
        Files.write(weiteresFile, Collections.singletonList("A"));
        weiteresFile.toFile().setLastModified(LAST_MODIFIED);
    }
}