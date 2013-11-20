package com.drtshock.willie.util;

import org.pircbotx.Colors;

public class StringTools {

    /**
     * Inserts a blank character inside the String to prevent pinging people
     *
     * @param name The username to silence
     * @return The silenced username
     */
    public static String noPing(String name) {
        if (name == null || name.length() < 3) {
            return name;
        } else {
            return name.substring(0, 1) + (char) 0x200b + name.substring(1, name.length());
        }
    }

    /**
     * Return a colorized String containing nbChars c characters, with left percent of
     * them colored with leftColor and right percent of them colored with rightColor.
     *
     * @param left           the percentage on the left to be colored with leftColor
     * @param leftColor      the left color
     * @param right          the percentage on the right to be colored with right color
     * @param rightColor     the right color
     * @param nbChars        the number of characters
     * @param barCharacter   the character to use
     * @param separator      the seperator
     * @param separatorColor the seperator color
     * @return the colored bar
     */
    public static String asciiBar(double left, String leftColor, double right, String rightColor, int nbChars, char barCharacter, char separator, String separatorColor) {
        double total = left + right;
        int nbLeftChars = (int) Math.round(left * nbChars / total);
        int nbRightChars = nbChars - nbLeftChars;
        final StringBuilder builder = new StringBuilder();
        builder.append(leftColor);
        for (int i = 0; i < nbLeftChars; i++) {
            builder.append(barCharacter);
        }
        builder.append(separatorColor);
        builder.append(separator);
        builder.append(rightColor);
        for (int i = 0; i < nbRightChars; i++) {
            builder.append(barCharacter);
        }
        builder.append(Colors.NORMAL);
        return builder.toString();
    }
}
