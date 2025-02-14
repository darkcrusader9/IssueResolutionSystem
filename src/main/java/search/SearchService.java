package search;

import model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class SearchService {
    public List<Issue> search(List<Issue> issues, List<SearchSpecification> specifications) {
        List<Issue> result = new ArrayList<>();

        for (Issue issue : issues) {
            if (matchesAllSpecifications(issue, specifications)) {
                result.add(issue);
            }
        }

        return result;
    }

    private boolean matchesAllSpecifications(Issue issue, List<SearchSpecification> specifications) {
        for (SearchSpecification spec : specifications) {
            if (!spec.isSatisfiedBy(issue)) {
                return false;
            }
        }
        return true;
    }
}