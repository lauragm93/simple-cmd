package cmd.commands.move;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * "Move" command class
 * <p/>
 * Executing the command moves file or directory to another location.
 */
@Command(
        name = "move",
        description = "Moves file or directory from one location to another",
        mixinStandardHelpOptions = true)
public class MoveCommand implements Runnable {

    @Parameters(index = "0", description = "path of the file to move")
    private File source;

    @Parameters(index = "1", description = "path to move file to")
    private File target;

    @Override
    public void run() {
        try {
            Files.copy(source.toPath(), target.toPath().resolve(source.getName()), StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(source.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
