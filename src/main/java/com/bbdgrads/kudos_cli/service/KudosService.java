package com.bbdgrads.kudos_cli.service;

import com.bbdgrads.kudos_cli.model.UserSession;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class KudosService {

    private final RequestService requestService;
    private final UserSession userSession;

    public KudosService(RequestService requestService, UserSession userSession) {
        this.requestService = requestService;
        this.userSession = userSession;
    }

    public String fetchAllKudos(String user){

        Optional<List> kudos = Optional.empty();
        if(user.equals("")){
            kudos = requestService.getRequest("/kudos/user-kudos", List.class);
        } else {
            kudos = requestService.getRequest("/kudos/getKudoByUsername/" + user, List.class);
        }

        if(kudos.isPresent()){
           return formatKudos(kudos.get());
        } else{
            return "No kudos here, sad...";
        }
    }

    public Optional<Object> sendKudo(String targetUsername, String message){
        Map<String, String> params = Map.of("targetUsername", targetUsername, "message", message);
        return requestService.postRequestWithParams("/kudos/create", Object.class, params);
    }

    private static String formatKudos(List<Map<String, Object>> kudosList) {
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> kudo : kudosList) {
            sb.append("\n\tKudo ID: ").append(kudo.get("kudoId")).append("\n")
                    .append("\tMessage: ").append(formatMessage((String) kudo.get("message"))).append("\n")
                    .append("\tFrom User ID: ").append(kudo.get("sendingUserId"))
                    .append(" -> To User ID: ").append(kudo.get("targetUserId")).append("\n")
                    .append("\tSent At: ").append(kudo.get("created_at")).append("\n")
                    .append("\tFlagged: ").append((boolean) kudo.get("flagged") ? "Yes" : "No")
                    .append(" | Read: ").append((boolean) kudo.get("read") ? "Yes" : "No").append("\n")
                    .append("------------------------------------\n");
        }

        return sb.toString();
    }

    private static String formatMessage(String message) {
        return message.replace(",", " ");  // Replaces commas with spaces for better readability
    }
}
