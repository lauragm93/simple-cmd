package cmd.commands.rename;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(
        name = "rename",
        description = "For renaming files",
        mixinStandardHelpOptions = true)
public class RenameCommand implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(RenameCommand.class);
    @CommandLine.Parameters(index = "0", description = "file to rename")
    private File fileToRename;
    @CommandLine.Parameters(index = "1", description = "new File name")
    private File newFile;


    @Override
    public void run() {
        if (checkProvidedFiles()){
            boolean isNewFileCreated = SimpleCmd.getCurrentLocation().toPath().resolve(fileToRename.toPath()).toFile()
                    .renameTo(SimpleCmd.getCurrentLocation().toPath().resolve(newFile.toPath()).toFile());
            generateLogAboutCreation(isNewFileCreated);
        }
    }

    private void generateLogAboutCreation(boolean isNewFileCreated) {
        if (isNewFileCreated){
            LOG.info("Renaming war erfolgreich zu {} unter dem Pfad {}", newFile.getName(), newFile.getAbsolutePath());
        }else{
            LOG.info("Renaming war nicht erfolgreich");
        }
    }

    private boolean checkProvidedFiles() {
        if (!SimpleCmd.getCurrentLocation().toPath().resolve(fileToRename.toPath()).toFile().exists()){
            LOG.info("{} existiert nicht",fileToRename.getName());
            return false;
        }
        if (newFile.exists()){
            LOG.info("{} kann nicht in bereits existierende Datei/Verzeichnis ''{}'' umbenannt werden",fileToRename.getName(), newFile.getName());
            return false;
        }
        return true;
    }

}
