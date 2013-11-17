package al.aldi.libjaldi.net;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * User: Aldi Alimucaj
 * Date: 17.11.13
 * Time: 21:46
 * <p/>
 * [Add Description]
 */
public class NetUtilsTest {
    public static final String IP1     = "192.168.178.50";
    public static final String IP_F1   = "192.168.178.51";
    public static final String IP_F2   = "192.168.178.53";
    public static final int    PORT1   = 8503;
    public static final int    PORT_F1 = 8504;
    public static final int    PORT_F2 = 8502;

    @Test
    public void testIsIp() {
        boolean isUp = NetUtils.isUp(IP1, PORT1);
        assertTrue(isUp);
        assertFalse(NetUtils.isUp(IP_F1, PORT1));
        assertFalse(NetUtils.isUp(IP_F2, PORT1));
        assertFalse(NetUtils.isUp(IP1, PORT_F1));
        assertFalse(NetUtils.isUp(IP1, PORT_F2));
    }

    @Test
    public void testGetListeningServers() {
        HashMap<String, Integer> map = NetUtils.getListeningServers(IP1, NetUtils.IpRange.MASK_24, PORT1);
        assertEquals(map.size(), 1);
        assertEquals(map.keySet().iterator().next(), IP1);

    }

}

