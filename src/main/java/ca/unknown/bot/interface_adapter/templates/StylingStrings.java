package ca.unknown.bot.interface_adapter.templates;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * This template is used for styling strings. Currently, its methods are suitable for converting
 * CamelCase and lowercase phrases to phrases starting with an uppercase letter and natural spacing.
 */

public class StylingStrings {

    public static String startWithUpperCase(String input) {
        if (isCamelCase(input)) {
            return camelCaseToWords(input);
        }
        if (isLowercasePhrase(input)) {
            return capitalizeEachWord(input);
        }
        return input;
    }

    private static boolean isCamelCase(String input) {
        Pattern camelCasePattern = Pattern.compile("^[a-z]+[A-Z][a-z]+$");
        return camelCasePattern.matcher(input).find();
    }

    private static boolean isLowercasePhrase(String input) {
        Pattern lowercaseWordPattern = Pattern.compile("^[a-z-]+(\\s[a-z]+)*$");
        return lowercaseWordPattern.matcher(input).find();
    }

    private static String camelCaseToWords(String input) {
        StringBuilder result = new StringBuilder();
        Matcher matcher = Pattern.compile("[A-Z]?[a-z]+").matcher(input);
        while (matcher.find()) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(matcher.group());
        }
        String output = capitalizeEachWord(result.toString());
        return output.trim();
    }

    private static String capitalizeEachWord(String input) {
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }
        return result.toString();
    }
}
