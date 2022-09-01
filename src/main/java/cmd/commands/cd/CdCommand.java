package cmd.commands.cd;

import cmd.SimpleCmd;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;

/**
 * "Change Directory" command class
 * <p/>
 * Executing the command changes the current directory.
 */
@Command(
        name = "cd",
        description = "Changes the directory",
        mixinStandardHelpOptions = true)
public class CdCommand implements Runnable {

    @Parameters(index = "0", description = "absolute path of the directory to change to")
    private File path;

    @Override
    public void run() {
        if(path.exists() && path.isDirectory() && path.isAbsolute())
            SimpleCmd.setCurrentLocation(path);
        else if(path.exists() && path.isDirectory())
            SimpleCmd.setCurrentLocation(SimpleCmd.getCurrentLocation().toPath().resolve(path.toPath()).toFile());
    }
}