import enums.IssueType;
import model.Issue;
import search.CustomerEmailSpecification;
import search.IssueIdSpecification;
import search.SearchService;
import search.SearchSpecification;
import strategy.AssignmentStrategy;
import strategy.SpecialisationAssignmentStrategy;
import system.ResolutionSystem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AssignmentStrategy strategy = new SpecialisationAssignmentStrategy();
        ResolutionSystem system = ResolutionSystem.getInstance(strategy);

        system.createIssue("T1", IssueType.PAYMENT_RELATED, "Payment Failed", "My payment failed but money is debited", "testUser1@test.com");
        system.createIssue("T2", IssueType.MUTUAL_FUND_RELATED, "Purchase Failed", "Unable to purchase Mutual Fund", "testUser2@test.com");
        system.createIssue("T3", IssueType.PAYMENT_RELATED, "Payment Failed", "My payment failed but money is debited", "testUser2@test.com");

        system.addAgent("agent1@test.com", "Agent 1", Arrays.asList(IssueType.PAYMENT_RELATED, IssueType.GOLD_RELATED));
        system.addAgent("agent2@test.com", "Agent 2", Arrays.asList(IssueType.MUTUAL_FUND_RELATED));

        system.assignIssue("I1");
        system.assignIssue("I2");
        system.assignIssue("I3");

        List<SearchSpecification> searchByEmail = Collections.singletonList(new CustomerEmailSpecification("testUser2@test.com"));
        System.out.println(system.getIssues(searchByEmail));

        List<SearchSpecification> searchByType = Collections.singletonList(new IssueIdSpecification("I1"));
        System.out.println(system.getIssues(searchByType));

        system.updateIssue("I3", "OPEN", "Waiting for payment confirmation");

        system.resolveIssue("I1", "PaymentFailed debited amount will get reversed");
        system.resolveIssue("I2", "Transient issue");
        //I3 is getting resolved by Agent 1 as soon as he resolves issue 1
        system.resolveIssue("I3", "Transient issue");

        List<SearchSpecification> searchByEmail2 = Collections.singletonList(new CustomerEmailSpecification("testUser2@test.com"));
        System.out.println(system.getIssues(searchByEmail2));

        system.viewAgentsWorkHistory();
    }
}
