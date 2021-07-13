package me.dnablue2112;

import java.util.HashMap;

public class Vote {

    private static int numberOfVotes = 0;
    public int voteNumber;
    /**
     * int = preference value e.g. 1,2,3... String is the option voted for e.g. design 1, design 2...
     */
    HashMap<Integer, String> preferences = new HashMap<>();

    public Vote() {
        voteNumber = numberOfVotes;
        numberOfVotes++;
    }

    /**
     * Add a preference to the Vote
     *
     * @param preferenceValue The integer representing this headings preference
     * @param heading         The heading this preference should be applied to
     * @return Returns true if the preference was applied successfully, false if the preference was already applied
     */
    public boolean addPreference(Integer preferenceValue, String heading) {
        if (preferences.containsKey(preferenceValue))
            return false;
        else
            preferences.put(preferenceValue, heading);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vote ID: ").append(voteNumber).append("\n");
        for (int i : preferences.keySet()) {
            sb.append(preferences.get(i)).append(" as ").append(i).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
