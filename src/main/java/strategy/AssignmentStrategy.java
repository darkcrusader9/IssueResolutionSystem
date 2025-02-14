package strategy;

import model.Agent;
import model.Issue;

import java.util.List;
import java.util.Queue;

public interface AssignmentStrategy {
    public String assignIssue(Issue issue, List<Agent> agents, Queue<Issue> backlog);
}
