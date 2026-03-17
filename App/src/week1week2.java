import java.util.*;

public class week1week2 {

    // username -> userId
    private Map<String, Integer> usernames = new HashMap<>();

    // username -> attempt frequency
    private Map<String, Integer> attemptCount = new HashMap<>();

    // Register username
    public boolean register(String username, int userId) {
        if (usernames.containsKey(username)) {
            return false;
        }
        usernames.put(username, userId);
        return true;
    }

    // Check username availability
    public boolean checkAvailability(String username) {
        attemptCount.put(username, attemptCount.getOrDefault(username, 0) + 1);
        return !usernames.containsKey(username);
    }

    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int count = 1;

        while (suggestions.size() < 3) {
            String newName = username + count;
            if (!usernames.containsKey(newName)) {
                suggestions.add(newName);
            }
            count++;
        }

        String alt = username.replace("_", ".");
        if (!usernames.containsKey(alt) && suggestions.size() < 3) {
            suggestions.add(alt);
        }

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {
        String result = null;
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptCount.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                result = entry.getKey();
            }
        }

        if (result == null) return null;
        return result + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        week1week2 checker = new week1week2();

        checker.register("john_doe", 1);
        checker.register("admin", 2);

        System.out.println(checker.checkAvailability("john_doe"));   // false
        System.out.println(checker.checkAvailability("jane_smith")); // true

        System.out.println(checker.suggestAlternatives("john_doe"));

        System.out.println(checker.getMostAttempted());
    }
}