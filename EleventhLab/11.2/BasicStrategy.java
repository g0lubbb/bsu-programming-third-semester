import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BasicStrategy implements SubjectProcessor {
    @Override
    public String processSubjects(Set<String> subjects) {
        List<String> sortedList = new ArrayList<>(subjects);
        Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
        return String.join("\n", sortedList);
    }
}
