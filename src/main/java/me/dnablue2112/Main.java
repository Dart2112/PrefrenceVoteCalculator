package me.dnablue2112;

import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, CsvException {
        new VotesLoader(new File(args[0]));
    }

}
