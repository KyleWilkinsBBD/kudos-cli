package com.bbdgrads.kudos_cli.shell;

import com.bbdgrads.kudos_cli.service.TeamService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TeamShell {

    private final TeamService teamService;

    public TeamShell(TeamService teamService) {
        this.teamService = teamService;
    }

    @ShellMethod(key = "teams")
    public String viewTeams(){
        return teamService.getAllTeams();
    }

}
