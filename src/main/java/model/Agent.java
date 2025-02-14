package model;

import enums.IssueStatus;
import enums.IssueType;

import java.util.*;

public class Agent {
    private String agentEmail;
    private String name;
    private Set<IssueType> expertise;
    private Issue currentIssue;
    private List<Issue> workHistory;

    public Agent(String agentEmail, String name, List<IssueType> expertise) {
        this.agentEmail = agentEmail;
        this.name = name;
        this.expertise = new HashSet<>(expertise);
        this.workHistory = new ArrayList<>();
    }

    public boolean isAvailable() {
        return currentIssue == null;
    }

    public boolean canHandle(IssueType issueType) {
        return expertise.contains(issueType);
    }

    public void assignIssue(Issue issue) {
        this.currentIssue = issue;
    }

    public void resolveIssue(String resolution) {
        if (currentIssue != null) {
            workHistory.add(currentIssue);
            currentIssue = null;
        }
    }
    public String getName() { return name;}
    public List<Issue> getWorkHistory() {
        return workHistory;
    }
    public String getAgentEmail() {
        return this.agentEmail;
    }
}