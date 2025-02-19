package baekjoon.BronzeSilver;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class B2606 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    // 1번 컴퓨터가 바이러스에 걸린 것은 포함하지 않으므로 -1로 시작함
    private static int counter = -1;

    private static final HashMap<Integer, Computer> computers = new HashMap<>();

    public static void main(String[] args) throws IOException {
        init();

        computers.get(1).infect();

        out.write(counter+"");
        out.close();
    }

    private static void init() throws IOException {
        int computerAmount = Integer.parseInt(in.readLine());
        int loop = Integer.parseInt(in.readLine());

        for (int i = 1; i <= computerAmount; i++) {
            computers.put(i, new Computer(i));
        }

        for (int i = 0; i < loop; i++) {
            String[] input = in.readLine().split(" ");
            int id1 = Integer.parseInt(input[0]);
            int id2 = Integer.parseInt(input[1]);

            Computer computer1 = computers.get(id1);
            Computer computer2 = computers.get(id2);
            computer1.setLink(computer2);
            computer2.setLink(computer1);
        }
    }

    static class Computer {

        int id;
        boolean isInfected;
        Set<Computer> linkedComputers;

        public Computer(int id) {
            this.id = id;
            isInfected = false;
            linkedComputers = new HashSet<>();
        }

        public void setLink(Computer computer) {
            this.linkedComputers.add(computer);
        }

        public void infect() {
            // 이미 감염되어 있다면 무시함
            if (!this.isInfected) {
                this.isInfected = true;
                counter++;
                for (Computer linkedComputer : linkedComputers) {
                    linkedComputer.infect();
                }
            }
        }
    }
}
