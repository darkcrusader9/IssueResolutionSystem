package search;

import model.Issue;

public interface SearchSpecification {
    boolean isSatisfiedBy(Issue issue);
}
