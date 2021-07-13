package me.dnablue2112;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        new VotesLoader(new File(args[0]));
    }

}
