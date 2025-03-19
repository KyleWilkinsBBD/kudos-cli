package com.bbdgrads.kudos_cli.shell;

import com.bbdgrads.kudos_cli.config.AuthState;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserShell {

    private final AuthState authState;

    public UserShell(AuthState authState){

        this.authState = authState;
    }

    @ShellMethod(key="token")
    public String displayJwtToken(){
        return authState.getAPI_KEY();
    }
}
