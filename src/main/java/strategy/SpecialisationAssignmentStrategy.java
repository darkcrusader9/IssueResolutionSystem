package strategy;

import model.Agent;
import model.Issue;

import java.util.List;
import java.util.Queue;

public class SpecialisationAssignmentStrategy implements AssignmentStrategy {
    @Override
    public String assignIssue(Issue issue, List<Agent> agents, Queue<Issue> backlog) {
        for (Agent agent : agents) {
            if (agent.canHandle(issue.getIssueType()) && agent.isAvailable()) {
                agent.assignIssue(issue);
                return agent.getAgentEmail();
            }
        }
        backlog.offer(issue);
        return null;
    }
}
