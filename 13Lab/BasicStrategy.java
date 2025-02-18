import java.util.Set;

public class BasicStrategy implements SubjectProcessor {
    @Override
    public String processSubjects(Set<String> subjects) {
        StringBuilder result = new StringBuilder();
        for (String subject : subjects) {
            result.append(subject).append("\n");
        }
        return result.toString();
    }
}
