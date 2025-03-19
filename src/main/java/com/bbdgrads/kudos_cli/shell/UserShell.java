package com.bbdgrads.kudos_cli.shell;

import com.bbdgrads.kudos_cli.config.AuthState;
import com.bbdgrads.kudos_cli.model.UserSession;
import com.bbdgrads.kudos_cli.service.UserService;
import org.apache.catalina.User;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserShell {

    private final AuthState authState;
    private final UserService userService;
    private final UserSession userSession;

    public UserShell(AuthState authState, UserService userService, UserSession userSession){
        this.userService = userService;
        this.authState = authState;
        this.userSession = userSession;
    }

    @ShellMethod(key="token")
    public String displayJwtToken(){
        return authState.getAPI_KEY();
    }

    @ShellMethod(key="create_user",value = "This allows an admin to create a user")
    public String createUser(
            @ShellOption(help = "Username of the new user") String username,
            @ShellOption(help = "Google ID of the new user") String googleId)
    {
        return userService.createUser(username, googleId);
    }

    @ShellMethod(key="my_team")
    public String getTeam(){
        return userService.getTeamOfUser(userSession.getUsername());
    }

    @ShellMethod(key="users_in_team")
    public String getUsersInTeam(
            @ShellOption(help = "Name of the team") String team_name
    ){
        return userService.getAllUsersInATeam(team_name);
    }

    @ShellMethod(key="upgrade_to_admin")
    public String updateToAdmin(
            @ShellOption(help = "Username") String username
    ){
        return userService.updateToAdmin(username);
    }

    @ShellMethod(key="find_users_team")
    public String findUsersTeam(
            @ShellOption(help = "Username") String username
    ){
        return userService.getTeamOfUser(username);
    }

    @ShellMethod(key="add_user_to_team")
    public String addUserToTeam(
            @ShellOption(help = "Username") String username,
            @ShellOption(help = "Team name") String team_name
    ){
        return userService.addUserToTeam(username, team_name);
    }




}
