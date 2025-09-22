package baekjoon.bronzesilver;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class B17219 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Map<String, String> sites = new HashMap<>();

    public static void main(String[] args) throws IOException {
        init();

        out.close();
    }

    private static void init() throws IOException {
        String[] input = in.readLine().split(" ");
        int saveLoop = Integer.parseInt(input[0]);
        int findLoop = Integer.parseInt(input[1]);
        for (int i = 0; i < saveLoop; i++) {
            String[] siteAndPassword = in.readLine().split(" ");
            sites.put(siteAndPassword[0], siteAndPassword[1]);
        }
        for (int i = 0; i < findLoop; i++) {
            String find = in.readLine();
            out.write(sites.get(find)+System.lineSeparator());
        }
    }
    
}
