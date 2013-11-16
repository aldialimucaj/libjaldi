package al.aldi.libjaldi.string;

import org.junit.Test;

import static org.junit.Assert.*;

public class AldiStringUtilsTest {

    String[] strArray                          = new String[]{"a", "b", "c"};
    String   strArrayJoined                    = "abc";
    String   strArrayJoinedWithSpaces          = "a b c";
    String   strArrayJoinedWithSpacesAndDollar = "$a $b $c";
    String   separator                         = " ";
    String   prefix                            = "$";

    /* *************************************************************************** */

    String linkWithHttp     = "http://asdfa.com";
    String linkWithHttp2    = "http://";
    String linkWithoutHttp  = "ahttp://";
    String linkWithoutHttp2 = "http:/";

    String linkWithHttps     = "https://asdfa.com";
    String linkWithHttps2    = "https://";
    String linkWithoutHttps  = "ahttp://";
    String linkWithoutHttps2 = "http:/";

    /* *************************************************************************** */

    String nullStr   = null;
    String emptyStr  = "";
    String emptyStr2 = new String();


    @Test
    public void testArrayToStringStringArray() {
        assertEquals(AldiStringUtils.arrayToString(strArray), strArrayJoined);
    }

    @Test
    public void testArrayToStringStringArrayString() {
        assertEquals(AldiStringUtils.arrayToString(strArray, separator), strArrayJoinedWithSpaces);
    }

    @Test
    public void testArrayToStringStringArrayStringString() {
        assertEquals(AldiStringUtils.arrayToString(strArray, prefix, separator), strArrayJoinedWithSpacesAndDollar);
    }

    @Test
    public void testStartsWithHttpS() {
        assertTrue(AldiStringUtils.startsWithHttpS(linkWithHttp));
        assertTrue(AldiStringUtils.startsWithHttpS(linkWithHttp2));
        assertFalse(AldiStringUtils.startsWithHttpS(linkWithoutHttp));
        assertFalse(AldiStringUtils.startsWithHttpS(linkWithoutHttp2));

        assertTrue(AldiStringUtils.startsWithHttpS(linkWithHttps));
        assertTrue(AldiStringUtils.startsWithHttpS(linkWithHttps2));
        assertFalse(AldiStringUtils.startsWithHttpS(linkWithoutHttps));
        assertFalse(AldiStringUtils.startsWithHttpS(linkWithoutHttps2));
    }

    @Test
    public void testIsNullOrEmty() {
        assertTrue(AldiStringUtils.isNullOrEmpty(nullStr));
        assertTrue(AldiStringUtils.isNullOrEmpty(emptyStr));
        assertTrue(AldiStringUtils.isNullOrEmpty(emptyStr2));

        assertFalse(AldiStringUtils.isNullOrEmpty(prefix));
        assertFalse(AldiStringUtils.isNullOrEmpty(separator));
        assertFalse(AldiStringUtils.isNullOrEmpty(linkWithHttps));
    }

    @Test
    public void testRoundedBrackets() {
        assertEquals(AldiStringUtils.addRoundBrackets(strArrayJoined), "("+strArrayJoined+")");
        assertEquals(AldiStringUtils.addRoundBrackets(strArrayJoinedWithSpaces), "("+strArrayJoinedWithSpaces+")");
        assertEquals(AldiStringUtils.addRoundBrackets(strArrayJoinedWithSpacesAndDollar), "("+strArrayJoinedWithSpacesAndDollar+")");

        assertNotSame(AldiStringUtils.addRoundBrackets(strArrayJoined), ""+strArrayJoined+")");
        assertNotSame(AldiStringUtils.addRoundBrackets(strArrayJoinedWithSpaces), "("+strArrayJoinedWithSpaces+"");
        assertNotSame(AldiStringUtils.addRoundBrackets(strArrayJoinedWithSpacesAndDollar), "(("+strArrayJoinedWithSpacesAndDollar+")");
    }

    @Test
    public void testTrailWithThreeDots() throws Exception {
        int lenght = 10;
        String retValue = AldiStringUtils.trailWithThreeDots(linkWithHttp, lenght);
        assertEquals(retValue,"http://...");
        assertEquals(retValue.length(), lenght);

        lenght = 15;
        retValue = AldiStringUtils.trailWithThreeDots(linkWithHttp, lenght);
        assertEquals(retValue,"http://asdfa...");
        assertEquals(retValue.length(), lenght);

        lenght = 4;
        retValue = AldiStringUtils.trailWithThreeDots("abcefefasefssfeas", lenght);
        assertEquals(retValue,"a...");
        assertEquals(retValue.length(), lenght);

        lenght = 2;
        retValue = AldiStringUtils.trailWithThreeDots("abc", lenght);
        assertEquals(retValue,"abc");

        lenght = 5;
        retValue = AldiStringUtils.trailWithThreeDots("abcde", lenght);
        assertEquals(retValue,"abcde");

        lenght = -1;
        boolean gotToCatch = false;
        try {
            AldiStringUtils.trailWithThreeDots("abcefsfeas", lenght);
        } catch (Exception e) {
            gotToCatch = true;
        } finally {
            assertTrue(gotToCatch);
        }


        lenght = -1;
        gotToCatch = false;
        try {
            AldiStringUtils.trailWithThreeDots("", lenght);
        } catch (Exception e) {
            gotToCatch = true;
        } finally {
            assertTrue(gotToCatch);
        }

        lenght = 0;
        gotToCatch = false;
        try {
            AldiStringUtils.trailWithThreeDots("", lenght);
        } catch (Exception e) {
            gotToCatch = true;
        } finally {
            assertTrue(gotToCatch);
        }
    }
}
