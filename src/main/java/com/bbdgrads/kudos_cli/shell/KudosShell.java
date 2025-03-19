package com.bbdgrads.kudos_cli.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class KudosShell {

    @ShellMethod(key="hihi")
    public String sayHello(){
        return "HiHi!";
    }

//    @ShellMethod(key="kudos")
//    public String seeKudos(){
//
//    }
}
