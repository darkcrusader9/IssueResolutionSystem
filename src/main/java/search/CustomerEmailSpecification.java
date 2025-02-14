package search;

import model.Issue;

public class CustomerEmailSpecification implements SearchSpecification {
    private final String email;

    public CustomerEmailSpecification(String email) {
        this.email = email;
    }

    @Override
    public boolean isSatisfiedBy(Issue issue) {
        return issue.getCustomerEmail().equalsIgnoreCase(email);
    }
}
