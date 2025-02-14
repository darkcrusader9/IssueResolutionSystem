package factory;

import enums.IssueType;
import model.Issue;

// Factory Pattern for Issue Creation
public class IssueFactory {
    private static int issueCounter = 1;

    public static Issue createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {
        return new Issue("I" + (issueCounter++), transactionId, issueType, subject, description, email);
    }
}