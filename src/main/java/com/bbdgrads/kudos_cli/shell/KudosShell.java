package com.bbdgrads.kudos_cli.shell;

import com.bbdgrads.kudos_cli.service.KudosService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Optional;

@ShellComponent
public class KudosShell {

    private final KudosService kudosService;

    public KudosShell(KudosService kudosService) {
        this.kudosService = kudosService;
    }

    @ShellMethod(key="hihi")
    public String sayHello(){
        return "HiHi!";
    }

    @ShellMethod(key = "kudos")
    public String viewKudos(@ShellOption(value = "-u", defaultValue = "") String user){

        return kudosService.fetchAllKudos(user);
    }

    @ShellMethod(key = "send")
    public String sendKudo(@ShellOption(value = "-u") String targetUser,
                           @ShellOption(value = "-m") String message){
       Optional<Object> newKudo =  kudosService.sendKudo(targetUser, message);
       if(newKudo.isPresent()){
           return "Sending Kudo: '" + message + "' to " + targetUser;
       } else{
           return "Couldn't send Kudo, is the target username correct?";
       }
    }



//    @ShellMethod(key="kudos")
//    public String seeKudos(){
//
//    }
}
