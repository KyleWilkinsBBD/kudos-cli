package com.bbdgrads.kudos_cli.model;

public class Team {
    private Long team_id;
    private String team_name;

    public Team(Long team_id, String team_name) {
        this.team_id = team_id;
        this.team_name = team_name;
    }

    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "team_id=" + team_id +
                ", team_name='" + team_name + '\'' +
                '}';
    }
}
