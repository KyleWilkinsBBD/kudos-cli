package com.bbdgrads.kudos_cli.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TeamService {

    private final RequestService requestService;

    public TeamService(RequestService requestService) {
        this.requestService = requestService;
    }

    public String getAllTeams(){
        Optional<List> teams = requestService.getRequest("/teams/allTeams", List.class);
        if (teams.isPresent()){
            return formatTeams(teams.get()) ;
        } else{
            return "No teams...";
        }
    }

    private String formatTeams(List<Map<String, Object>> teamsList) {
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> team : teamsList) {
            sb.append("\tTeam ID: ").append(team.get("teamId")).append("\n")
                    .append("\tTeam Name: ").append(team.get("name")).append("\n")
                    .append("------------------------------------\n");
        }

        return sb.toString();
    }
}
