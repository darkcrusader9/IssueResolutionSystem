package model;

import enums.IssueStatus;
import enums.IssueType;

public class Issue {
    private String issueId;
    private String transactionId;
    private IssueType issueType;
    private String subject;
    private String description;
    private String customerEmail;
    private IssueStatus status;
    private String resolution;

    public Issue(String issueId, String transactionId, IssueType issueType, String subject, String description, String customerEmail) {
        this.issueId = issueId;
        this.transactionId = transactionId;
        this.issueType = issueType;
        this.subject = subject;
        this.description = description;
        this.customerEmail = customerEmail;
        this.status = IssueStatus.WAITING;
    }

    public String getIssueId() { return issueId; }
    public IssueType getIssueType() { return issueType; }
    public IssueStatus getStatus() { return status; }
    public String getCustomerEmail() {
        return customerEmail;
    }
    public String getResolution() {
        return resolution;
    }
    public void setStatus(IssueStatus status) { this.status = status; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    @Override
    public String toString() {
        return "Issue{" +
                "issueId='" + issueId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", issueType=" + issueType +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", status=" + status +
                ", resolution='" + resolution + '\'' +
                '}';
    }
}
