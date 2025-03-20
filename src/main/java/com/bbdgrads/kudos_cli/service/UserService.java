package com.bbdgrads.kudos_cli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RequestService requestService;

    public String createUser(String username, String googleId) {
        Map<String, String> params = Map.of("name", username, "googleId", googleId);

        Optional<String> response = requestService.postRequestWithParams("/user/create", String.class, params);
        if (response.isPresent()) {
            return "User Created!";
        } else {
            return "Failed to create user";
        }
    }

    public String getTeamOfUser(String username) {
        Optional<String> response = requestService.getRequest("/user/getTeam/" + username, String.class);
        if (response.isPresent()) {
            return response.get();
        } else {
            return "No team";
        }
    }

    public String getAllUsersInATeam(String team_name) {
        Optional<List> response = requestService.getRequest("/user/findAllByTeamName/" + team_name, List.class);
        if (response.isPresent()) {
            return formatUsers(response.get());
        } else {
            return "No users in team";
        }
    }

    public String getAllOtherUsersInATeam(String team_name, String requestingUser) {
        Optional<List> response = requestService.getRequest("/user/findAllByTeamName/" + team_name, List.class);
        if (response.isPresent()) {
            // Cast with the specific type parameters needed for formatUsers
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> users = (List<Map<String, Object>>) response.get();

            // Remove the requesting user by comparing username values
            users.removeIf(user -> requestingUser.equals(user.get("username")));

            return formatUsers(users);
        } else {
            return "No users in team";
        }
    }

    public String updateToAdmin(String username) {
        Map<String, String> params = Map.of("username", username);
        Optional<String> response = requestService.patchRequest("/user/setUserToAdmin/" + username, String.class,
                params);
        if (response.isPresent()) {
            return "Success! Admin permissions granted!";
        } else {
            return "Failure.";
        }
    }

    public String addUserToTeam(String username, String team_name) {
        Map<String, String> params = Map.of("username", username);
        Optional<String> response = requestService.patchRequest("/user/addUserToTeam/" + username + "/" + team_name,
                String.class, params);
        if (response.isPresent()) {
            return "Success! Team assigned";
        } else {
            return "Failure.";
        }
    }

    private static String formatUsers(List<Map<String, Object>> userList) {
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> user : userList) {
            sb.append("\n\tUser ID: ").append(user.get("userId")).append("\n")
                    .append("\tUsername: ")
                    .append(formatMessage((String) user.get("username")))
                    .append("\n");
            // Extract the team object
            Map<String, Object> team = (Map<String, Object>) user.get("team");
            if (team != null) {
                sb.append("\tTeam Name: ").append(team.get("name")).append("\n");
            } else {
                sb.append("\tTeam Name: None\n");
            }

            sb.append("------------------------------------\n");
        }
        return sb.toString();
    }

    private static String formatMessage(String message) {
        return message.replace(",", " "); // Replaces commas with spaces for better readability
    }

}
