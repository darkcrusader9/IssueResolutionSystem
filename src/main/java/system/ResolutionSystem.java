package system;

import enums.IssueStatus;
import enums.IssueType;
import factory.IssueFactory;
import model.Agent;
import model.Issue;
import search.SearchService;
import search.SearchSpecification;
import strategy.AssignmentStrategy;

import java.util.*;

public class ResolutionSystem {
    private static volatile ResolutionSystem INSTANCE;
    private Map<String, Issue> issues;
    private Map<String, Agent> agents;
    private Map<String, String> issueAgentMap;
    private Queue<Issue> backlog;
    private AssignmentStrategy assignmentStrategy;
    private SearchService searchService;

    private ResolutionSystem(AssignmentStrategy strategy) {
        this.issues = new HashMap<>();
        this.agents = new HashMap<>();
        this.issueAgentMap = new HashMap<>();
        this.backlog = new LinkedList<>();
        this.assignmentStrategy = strategy;
        this.searchService = new SearchService();
    }

    public static ResolutionSystem getInstance(AssignmentStrategy strategy) {
        if (INSTANCE == null) {
            synchronized (ResolutionSystem.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ResolutionSystem(strategy);
                }
            }
        }
        return INSTANCE;
    }

    public void createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {
        Issue issue = IssueFactory.createIssue(transactionId, issueType, subject, description, email);
        issues.put(issue.getIssueId(), issue);
        String agentId = assignIssue(issue.getIssueId());
        System.out.println("Issue " + issue.getIssueId() + " created against transaction " + transactionId + (agentId != null ? " and assigned to " + agentId + "." : " and added to backlog."));
    }

    public void addAgent(String agentEmail, String name, List<IssueType> expertise) {
        Agent agent = new Agent(agentEmail, name, expertise);
        agents.put(agentEmail, agent);
        System.out.println("Agent " + name + " added");
        reassignBacklog();
    }

    public String assignIssue(String issueId) {
        Issue issue = issues.get(issueId);
        if (issue != null) {
            String agentId = assignmentStrategy.assignIssue(issue, new ArrayList<>(agents.values()), backlog);
            if (agentId != null) {
                issueAgentMap.put(issueId, agentId);
                System.out.println("Issue " + issueId + " assigned to agent " + agentId + ".");
            } else {
                System.out.println("Issue " + issueId + " added to backlog.");
            }
            return agentId;
        }
        return null;
    }

    public void updateIssue(String issueId, String status, String resolution) {
        Issue issue = issues.get(issueId);
        if (issue != null) {
            issue.setStatus(IssueStatus.valueOf(status));
            issue.setResolution(resolution);
            System.out.println(issueId + " status updated to " + status + " resolution updated to " + resolution);
        }
    }

    public void resolveIssue(String issueId, String resolution) {
        String agentEmail = issueAgentMap.get(issueId);
        if (agentEmail != null && agents.containsKey(agentEmail)) {
            Agent agent = agents.get(agentEmail);
            updateIssue(issueId, "RESOLVED", resolution);
            agent.resolveIssue(resolution);
            System.out.println("Issue " + issueId + " resolved. " + "by " + agent.getName());
            issueAgentMap.remove(issueId);
            reassignBacklog();
        } else {
            System.out.println("No agent found for issue " + issueId);
        }
    }

    private void reassignBacklog() {
        Iterator<Issue> iterator = backlog.iterator();

        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            String agentId = assignIssue(issue.getIssueId());

            if (agentId != null) {
                iterator.remove(); // Safe removal while iterating
            }
        }
    }


    public List<Issue> getIssues(List<SearchSpecification> specifications) {
        return searchService.search(new ArrayList<>(issues.values()), specifications);
    }


    public void viewAgentsWorkHistory() {
        for (Map.Entry<String, Agent> entry : agents.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().getWorkHistory());
        }
    }
}