package com.bbdgrads.kudos_cli.shell;

import com.bbdgrads.kudos_cli.service.LogService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class LogShell {

    private final LogService logService;

    public LogShell(LogService logService) {
        this.logService = logService;
    }

    @ShellMethod(key = "logs")
    public String viewLogs(@ShellOption(value = "-u", defaultValue = "") String user){
        return logService.getLogs(user);
    }
}
