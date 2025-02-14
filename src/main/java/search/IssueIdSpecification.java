package search;

import model.Issue;

public class IssueIdSpecification implements SearchSpecification {
    private final String issueId;

    public IssueIdSpecification(String issueId) {
        this.issueId = issueId;
    }

    @Override
    public boolean isSatisfiedBy(Issue issue) {
        return issue.getIssueId().equals(issueId);
    }
}