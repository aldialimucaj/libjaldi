package al.aldi.libjaldi.net;

import java.io.IOException;
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
    public static final int THREAD_NUMBER = 100;

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
     * @param ip ip to start checking
     * @param netMask mask cann be /24 or /16 the more doesn' t make sense
     * @param port port to connect
     * @return map of servers which were listening to the port
     */
    public static HashMap<String, Integer> getListeningServers(String ip, IpRange netMask, int port) {
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

    public enum IpRange {
        MASK_24,
        MASK_16
    }
}
