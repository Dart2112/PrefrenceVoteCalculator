package me.dnablue2112;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class VotesLoader {

    List<Vote> votes = new ArrayList<>();

    public VotesLoader(File csv) throws IOException, CsvException {
        BufferedReader reader = Files.newBufferedReader(csv.toPath());
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = csvReader.readAll();

        for (String[] arr : list) {
            System.out.println("\n");
            for (String s : arr) {
                System.out.println(s);
            }
        }
    }

}
