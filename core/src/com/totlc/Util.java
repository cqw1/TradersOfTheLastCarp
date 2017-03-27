package com.totlc;

public class Util {

    public static String parseLevelString(String tmxFileName) {
        // tmxFileName comes in the format of "tmx/level_01.tmx"
        // Want to convert to "Level 01"
        if (tmxFileName.equals("")) {
            return "NO NAME";
        }

        String[] splitString = tmxFileName.split("[\\/\\.]"); // Split based on "/" and "." (e.g. ["tmx", "level_01", "tmx"])
        String parsed = splitString[splitString.length - 2]; // Get the second to last element. (e.g. "level_01")
        parsed = parsed.replaceAll("_", " "); // (e.g. "level 01") TODO I think our font displays lowercase in uppercase?

        return parsed;
    }
}
