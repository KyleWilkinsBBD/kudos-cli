package com.bbdgrads.kudos_cli.service;

import com.bbdgrads.kudos_cli.model.UserSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LogService {
    private final RequestService requestService;
    private final UserSession userSession;

    public LogService(RequestService requestService, UserSession userSession) {
        this.requestService = requestService;
        this.userSession = userSession;
    }

    public String getLogs(String userId) {
        Optional<List> logs = Optional.empty();
        if (userId.equals("")) { // if getting logs for self
            logs = requestService.getRequest("/logs/acting-user/" + userSession.getUserId(), List.class);
        } else {
            logs = requestService.getRequest("/logs/target-user/" + userId, List.class);
        }
        return logs.map(this::formatLogs).orElse("No logs...");
    }

    public String getLogsByType(String type) {
        Optional<List> logs = Optional.empty();

        logs = requestService.getRequest("/logs/type/" + type, List.class);

        return logs.map(this::formatLogs).orElse("No logs...");
    }

    private String formatLogs(List<Map<String, Object>> logsList) {
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> log : logsList) {
            sb.append("Log ID: ").append(log.get("logId")).append("\n")
                    .append("\tActing User: ").append(log.get("actingUser")).append("\n")
                    .append("\tTarget User: ").append(log.get("targetUser")).append("\n")
                    .append("\tKudo ID: ").append(log.get("kudoId") != null ? log.get("kudoId") : "N/A").append("\n")
                    .append("\tTeam: ").append(log.get("team") != null ? log.get("team") : "N/A").append("\n")
                    .append("\tEvent ID: ").append(log.get("eventId")).append("\n")
                    .append("\tVerbose Log: ").append(log.get("verboseLog")).append("\n")
                    .append("\tLog Time: ").append(log.get("logTime")).append("\n")
                    .append("------------------------------------\n");
        }

        return sb.toString();
    }
}
