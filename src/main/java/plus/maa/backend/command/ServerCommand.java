package plus.maa.backend.command;

import org.springframework.shell.ExitRequest;
import org.springframework.shell.command.annotation.Command;
import plus.maa.backend.MainApplication;

@Command(group = "Server Commands")
public class ServerCommand {
    @Command(command = "restart", description = "Restart the server.")
    public void restart() {
        MainApplication.restart();
    }

    @Command(command = "exit", alias = {"stop", "quit"}, description = "Exit the server.")
    public void exit() {
        throw new ExitRequest();
    }
}
