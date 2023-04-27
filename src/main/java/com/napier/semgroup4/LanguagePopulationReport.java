package com.napier.semgroup4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LanguagePopulationReport {

    public static void main(App a) {


        // Print the Language Population Report
        App.LanguageStats languageStats = new App.LanguageStats();
        languageStats.getLanguageStats(a.con);
        languageStats.printLanguageStats();

    }

}