package al.aldi.libjaldi.string;

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
     * @param strArray the array to be joined
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
     * @param strArray the array to be joined
     * @param prefix - the prefix to be added before every array token is joined
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
     * 
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
        if(null != str) {
            return str.equals("");
        }
        return true;
    }

    /**
     * Adding rounded brackets to string and returning it
     * @code AldiStringUtils.addRoundBrackets("name"); // => (name)
     * @param str String to be put between brackets
     * @return concatenated string with brackets
     */
    public static String addRoundBrackets(String str) {
        return "(" + str + ")";
    }

    /**
     * Adding rounded brackets to string and returning it
     * @code AldiStringUtils.addSquareBrackets("name"); // => [name]
     * @param str String to be put between brackets
     * @return concatenated string with brackets
     */
    public static String addSquareBrackets(String str) {
        return "[" + str + "]";
    }
}
