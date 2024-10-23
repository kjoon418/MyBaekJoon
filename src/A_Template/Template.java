package A_Template;

import java.io.*;

public class Template {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {

    }

    private static void controller() throws IOException {

    }

    private static void printer() throws IOException {


        out.close();
    }


}
