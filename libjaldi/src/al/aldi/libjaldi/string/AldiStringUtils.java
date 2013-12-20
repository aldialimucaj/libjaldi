package al.aldi.libjaldi.string;

import java.util.Random;

public class AldiStringUtils {

    /**
     * Joins the array into a single string.
     *
     * @param strArray
     * @return joined array
     */
    public static String arrayToString(String[] strArray) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            result.append(strArray[i]);
            if (i != strArray.length - 1) {
            }
        }
        String concatString = result.toString();
        return concatString;

    }


    /**
     * Joins the array with a suffix
     *
     * @param strArray  the array to be joined
     * @param separator - the suffix to be added after the token. It is not added in the last token.
     * @return joined array
     */
    public static String arrayToString(String[] strArray, String separator) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            result.append(strArray[i]);
            if (i != strArray.length - 1) {
                result.append(separator);
            }
        }
        String concatString = result.toString();
        return concatString;

    }

    /**
     * Joins the array with a prefix and suffix
     *
     * @param strArray  the array to be joined
     * @param prefix    - the prefix to be added before every array token is joined
     * @param separator - the suffix to be added after the token. It is not added in the last token.
     * @return joined array
     */
    public static String arrayToString(String[] strArray, String prefix, String separator) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            result.append(prefix);
            result.append(strArray[i]);
            if (i != strArray.length - 1) {
                result.append(separator);
            }
        }
        String concatString = result.toString();
        return concatString;

    }

    /**
     * Checks if the string starts with HTTP or HTTPS
     * <p/>
     * Like http:// or https://
     *
     * @param string
     * @return
     */
    public static boolean startsWithHttpS(String string) {
        return string.startsWith("http://") || string.startsWith("https://");
    }

    /**
     * Tests whether the object is null or empty.
     *
     * @param str String object to be tested if null or empty
     * @return true if null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        if (null != str) {
            return str.equals("");
        }
        return true;
    }

    /**
     * Adding rounded brackets to string and returning it
     *
     * @param str String to be put between brackets
     * @return concatenated string with brackets
     * @code AldiStringUtils.addRoundBrackets("name"); // => (name)
     */
    public static String addRoundBrackets(String str) {
        return "(" + str + ")";
    }

    /**
     * Adding rounded brackets to string and returning it
     *
     * @param str String to be put between brackets
     * @return concatenated string with brackets
     * @code AldiStringUtils.addSquareBrackets("name"); // => [name]
     */
    public static String addSquareBrackets(String str) {
        return "[" + str + "]";
    }

    /**
     * Adding curly brackets to string and returning it
     *
     * @param str String to be put between brackets
     * @return concatenated string with brackets
     * @code AldiStringUtils.addCurlyBrackets("name"); // => {name}
     */
    public static String addCurlyBrackets(String str) {
        return "{" + str + "}";
    }

    /**
     * Trails the message if it exeeds the length given in length and add 3 dots
     * without passing the length limit
     *
     * @param str String to read from
     * @param length length not to be exeeded
     */
    public static String trailWithThreeDots(final String str, int length) {
        String suffix = "...";
        if(length <= 0) {
            throw new IllegalArgumentException("Length to trail cannot bo negative or zero!");
        }
        if (str.length() <= length || suffix.length() >= length ) {
            return str;
        } else if (str.length() + suffix.length() <= length) {
            return str;
        }

        return str.substring(0, length - suffix.length()) + suffix;
    }

    /* ********************************************************************** */

    public static String random(int length) {
        return new RandomString(length).nextString();
    }

    static class RandomString {

        private final char[] symbols = new char[62];
        private final Random random  = new Random();
        private final char[] buf;

        public RandomString(int length) {
            for (int idx = 0; idx < 10; ++idx)
                symbols[idx] = (char) ('0' + idx);
            for (int idx = 10; idx < 36; ++idx)
                symbols[idx] = (char) ('a' + idx - 10);
            for (int idx = 36; idx < 62; ++idx)
                symbols[idx] = (char) ('A' + idx - 36);

            if (length < 1)
                throw new IllegalArgumentException("str length < 1: " + length);
            buf = new char[length];
        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

    }
}
