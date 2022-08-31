package cmd.commands.mkdir;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CommandLine.Command(
        name = "mkdir",
        description = "Creates directories or files",
        mixinStandardHelpOptions = true)
public class MkdirCommand implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(MkdirCommand.class);
    @CommandLine.Parameters(index = "0", description = "path of file/directory to create")
    private String pathToFileOrDirectory;
    @CommandLine.Option(names = {"-f", "--file"}, description = "create file")
    private boolean isFile;


    @Override
    @SuppressWarnings("java:S112")
    public void run() {
        Path toCreate = SimpleCmd.getCurrentLocation().toPath().resolve(Paths.get(pathToFileOrDirectory));
        if (toCreate.toFile().exists()){
            LOG.info(toCreate.toAbsolutePath() + " existiert schon.");
            return;
        }
        try {
                if (!isFile) {
                    Files.createDirectories(toCreate);
                }else{
                  Files.createFile(toCreate);
                }
                LOG.debug("{} wurde erstellt.",toCreate);
            } catch (IOException e) {
                LOG.error("{} konnte nicht erstellt werden",toCreate);
                throw new RuntimeException(e);
            }
    }

}
