package me.dnablue2112;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.*;

public class VotesLoader {

    List<Vote> votes = new ArrayList<>();
    HashMap<String, List<Vote>> currentVoteDistribution = new HashMap<>();
    List<String> headingsEliminated = new ArrayList<>();
    Integer currentDistributionsCompleted = 0;

    public VotesLoader(File csv) {
        try {
            loadPreferences(csv);
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred loading preferences");
            e.printStackTrace();
            System.exit(1);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            distributeVotes();
            displayVotes();
            System.out.println("Distribute Again? y/N");
            String input = null;
            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!input.equalsIgnoreCase("y")) {
                break;
            }
            currentDistributionsCompleted++;
        }
    }

    private void distributeVotes() {
        if (currentVoteDistribution.isEmpty()) {
            //distribute the first preferences
            for (Vote v : votes) {
                //Get the first preference and add it to the current distribution
                String firstPreference = v.preferences.get(1);
                applyVote(firstPreference, v);
                v.preferences.remove(1);
            }
        } else {
            //Eliminate the heading with the least votes and redistribute the votes
            String leastVotedHeading = "";
            int leastVotes = Integer.MAX_VALUE;
            for (String heading : currentVoteDistribution.keySet()) {
                if (leastVotes > currentVoteDistribution.get(heading).size()) {
                    leastVotes = currentVoteDistribution.get(heading).size();
                    leastVotedHeading = heading;
                }
            }
            List<Vote> votesToRedistribute = new ArrayList<>(currentVoteDistribution.get(leastVotedHeading));
            for (Vote vote : votesToRedistribute) {
                //Find next lowest preference value
                int nextPreference = Integer.MAX_VALUE;
                for (int i : vote.preferences.keySet()) {
                    //Check if this is the next preference, also check if it has been eliminated
                    if (i < nextPreference && !headingsEliminated.contains(vote.preferences.get(i))) {
                        nextPreference = i;
                    }
                }
                applyVote(vote.preferences.get(nextPreference), vote);
                //remove that preference
                vote.preferences.remove(nextPreference);
            }
            currentVoteDistribution.remove(leastVotedHeading);
            headingsEliminated.add(leastVotedHeading);
        }
    }

    /*
    Utility Methods
     */

    /**
     * Prints the current vote tally to the console
     */
    private void displayVotes() {
        HashMap<String, List<Vote>> workingDistribution = new HashMap<>();
        HashMap<String, Integer> piChartData = new HashMap<>();
        //copy the current vote distribution so we can edit it without breaking anything
        workingDistribution.putAll(currentVoteDistribution);
        while (!workingDistribution.isEmpty()) {
            int highestVotes = -1;
            String highestVotesHeading = "";
            for (String heading : workingDistribution.keySet()) {
                if (workingDistribution.get(heading).size() > highestVotes) {
                    highestVotes = workingDistribution.get(heading).size();
                    highestVotesHeading = heading;
                }
            }
            double percentage = highestVotes / Double.parseDouble(String.valueOf(votes.size())) * 100;
            System.out.println(highestVotesHeading + " Votes: " + highestVotes + " %: " + percentage);
            workingDistribution.remove(highestVotesHeading);
            piChartData.put(highestVotesHeading, highestVotes);
        }
        new VotePieChart(piChartData, currentDistributionsCompleted);
    }

    /**
     * Adds the given vote ID to the current vote distribution under the correct heading
     *
     * @param heading The heading that the vote should be added to
     * @param vote    The vote being added
     */
    private void applyVote(String heading, Vote vote) {
        List<Vote> currentVotes;
        if (currentVoteDistribution.containsKey(heading)) {
            currentVotes = currentVoteDistribution.get(heading);
        } else {
            currentVotes = new ArrayList<>();
        }
        currentVotes.add(vote);
        currentVoteDistribution.put(heading, currentVotes);
    }

    private void loadPreferences(File csv) throws IOException, CsvValidationException {
        BufferedReader reader = Files.newBufferedReader(csv.toPath());
        CSVReader csvReader = new CSVReader(reader);
        //The names of the items that have been voted on
        List<String> headings = new ArrayList<>();
        //get the first row from the CSV containing headings and add them to the headings list
        Collections.addAll(headings, csvReader.readNext());
        //remove the timestamp heading from the headings
        headings.remove(0);
        //Process the preferences for these headings
        String[] arr = csvReader.readNext();
        while (arr != null) {
            Vote vote = new Vote();
            for (int i = 0; i < arr.length; i++) {
                if (i == 0)
                    continue;
                //The value assigned to this vote
                int preferenceValue = Integer.parseInt(arr[i]);
                //The name of the heading being voted for
                String voteHeading = headings.get(i - 1);
                if (!vote.addPreference(preferenceValue, voteHeading)) {
                    System.out.println("PREFERENCE ERROR!" + "\n" + Arrays.toString(arr));
                    System.exit(1);
                }
            }
            arr = csvReader.readNext();
            votes.add(vote);
        }
    }

}
