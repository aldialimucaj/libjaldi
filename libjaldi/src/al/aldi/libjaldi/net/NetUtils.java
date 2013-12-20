package al.aldi.libjaldi.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: Aldi Alimucaj
 * Date: 17.11.13
 * Time: 19:32
 * <p/>
 * <p/>
 * Helper class for common network functions
 */
public class NetUtils {
    public static final int IS_UP_TIMEOUT = 1000;
    public static final int THREAD_NUMBER = 256;

    public static final String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    /**
     * Checks if remote server is listening to port.
     *
     * @param address Address being it IP or domain
     * @param port    port to connect to
     * @return true if
     */
    public static boolean isUp(String address, final int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(address, port), IS_UP_TIMEOUT);
            return true;
        } catch (IOException e) {
            System.err.println(address + ":" + port + " " + e.getMessage());
        }
        return false;
    }

    /**
     * Asynchronous method for checking if port is open if a set of servers.
     *
     * @param ip      ip version 4 to start checking
     * @param netMask mask cann be /24 or /16 the more doesn' t make sense
     * @param port    port to connect
     * @return map of servers which were listening to the port
     */
    public static HashMap<String, Integer> getListeningServers(String ip, IpRange netMask, int port) throws IllegalArgumentException {
        if (!ip.matches(IPADDRESS_PATTERN)) {
            throw new IllegalArgumentException(ip + " - doesnt look like an IPv4 to me!");
        }
        HashMap<String, Integer> map = new HashMap<>();
        String ipStub = ip;
        int range = 0;
        switch (netMask) {
            case MASK_24:
                ipStub = ip.substring(0, ip.lastIndexOf('.') + 1);
                range = 255;
                break;
            case MASK_16:
                ipStub = ip.substring(0, ip.lastIndexOf('.'));
                ipStub = ipStub.substring(0, ipStub.lastIndexOf('.'));
                range = (255 * 255) - 1;
                break;
        }

        // creating a pool executor
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
        List<Callable<String>> callableList = new ArrayList();
        for (int i = 0; i < range; i++) {
            final String t_ip = ipStub + i;
            final int t_port = port;
            Callable<String> callMe = new Callable<String>() {
                @Override
                public String call() throws Exception {

                    return isUp(t_ip, t_port) ? t_ip : null;
                }
            };
            callableList.add(callMe);
        }

        try {
            List<Future<String>> results = executor.invokeAll(callableList);
            for (Future<String> future : results) {
                String futureIp = future.get();
                if (null != futureIp) {
                    map.put(futureIp, port);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        return map;
    }

    /**
     * Sends a Magic Packet which would be turning the host on if properly configured.
     *
     * @param mac
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static void wol(String mac) throws IOException, IllegalArgumentException {
        final String HEX = "0123456789ABCDEF";
        byte bMAC[] = new byte[6];
        int m = 0; // string index
        int i = 0; // mac byte array index
        int h = 0; // last (high) hex digit
        mac = mac.toUpperCase();
        while (m < mac.length() && i < 2 * 6) {
            int n = HEX.indexOf(mac.charAt(m));
            if (n >= 0) {
                if (i % 2 == 0) {
                    h = n;
                } else {
                    bMAC[i / 2] = (byte) (h * 16 + n);
                }
                i++;
            }
            m++;
        }
        if (m < mac.length()) {
            throw new IllegalArgumentException(
                    "mac Address must be 12 Hex digits exactly");
        }

        wol(bMAC);
    }

    /**
     * Wake on LAN with byte MAC
     * @param MAC
     * @throws IOException
     */
    public static void wol(byte[] MAC) throws IOException {
        if (MAC == null || MAC.length != 6) {
            throw new IllegalArgumentException(
                    "MAC array must be present and 6 bytes long");
        }

        // Assemble Magic Packet
        int packetLength = 102;
        byte packetData[] = new byte[packetLength];
        int m = 0;

        // Start off with six 0xFF values
        packetData[m++] = (byte) 255;
        packetData[m++] = (byte) 255;
        packetData[m++] = (byte) 255;
        packetData[m++] = (byte) 255;
        packetData[m++] = (byte) 255;
        packetData[m++] = (byte) 255;

        // Append sixteen copies of MAC address
        for (int i = 0; i < 16 * 6; i++) {
            packetData[m] = MAC[m % 6];
            m++;
        }

        DatagramSocket socket = new DatagramSocket(2304);
        try {
            InetSocketAddress address = new InetSocketAddress("255.255.255.255", 2304);
            DatagramPacket datagram = new DatagramPacket(packetData, packetLength, address);
            socket.setBroadcast(true);
            socket.send(datagram);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    /**
     * Converts string to byte array.
     *
     * @param macStr mac in string form which can be separated by :, - or | for example 00:1D:7D:D0:EF:2C
     * @return byte array of mac address
     * @throws IllegalArgumentException
     */
    public static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }

    public enum IpRange {
        MASK_24,
        MASK_16
    }
}
