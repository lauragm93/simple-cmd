package cmd.commands.file;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * "List Directory" command class
 * <p/>
 * Executing the command lists all the files and folders in the current working directory.
 *
 * @see SimpleCmd#getCurrentLocation()
 * @see SimpleCmd#setCurrentLocation(File)
 */
@CommandLine.Command(
        name = "file",
        description = "Shows content of file",
        mixinStandardHelpOptions = true)
public class FileCommand implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(FileCommand.class);
    @CommandLine.Parameters(index = "0", description = "File to list content of")
    private static File file;

    @Override

    public void run() {
        File fileToPrint = SimpleCmd.getCurrentLocation().toPath().resolve(FileCommand.file.toPath()).toFile();
        if(validateFileToPrint(fileToPrint)){
            readAndPrintFile(fileToPrint);
        }
    }

    @SuppressWarnings({"java:S2629","java:S112"})
    private void readAndPrintFile(File fileToPrint) {
        try(Scanner input = new Scanner(fileToPrint)) {
            while (input.hasNextLine()) {
                LOG.info("{}\n",input.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateFileToPrint(File fileToPrint) {
        if (!fileToPrint.exists()){
            LOG.info("File existiert nicht");
            return false;
        } else if (!fileToPrint.isFile()) {
            LOG.info("{} ist kein file",fileToPrint.getName());
            return false;
        } else if (!fileToPrint.canRead()) {
            LOG.info("{} kann nicht gelesen werden. Evtl fehlen Rechte?",fileToPrint.getName());
            return false;
        }
        return true;
    }
}
