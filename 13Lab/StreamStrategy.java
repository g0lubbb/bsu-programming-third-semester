import java.util.Set;
import java.util.stream.Collectors;

public class StreamStrategy implements SubjectProcessor {
    @Override
    public String processSubjects(Set<String> subjects) {
        return subjects.stream()
                       .sorted()
                       .collect(Collectors.joining("\n"));
    }
}
