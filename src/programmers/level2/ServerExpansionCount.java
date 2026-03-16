package programmers.level2;

import java.util.HashSet;
import java.util.Set;

/**
 * 서버 증설 횟수
 */
class ServerExpansionCount {
    /**
     players = 시간대별 게임 이용자의 수
     m = 서버 한 대로 감당할 수 있는 이용자의 수
     k = 서버 한 대가 운영 가능한 시간
     */
    public int solution(int[] players, int m, int k) {
        calculateExpandCount(players, m, k);

        return expandCount;
    }

    private int expandCount = 0;
    private Set<Server> operatingServers = new HashSet<>();

    private void calculateExpandCount(
            int[] players,
            int serverVolume,
            int operatingDuration
    ) {
        for (int currentTime = 0; currentTime < players.length; currentTime++) {
            dropServers(currentTime);

            int playerCount = players[currentTime];
            expandServer(
                    currentTime,
                    operatingDuration,
                    playerCount,
                    serverVolume
            );
        }
    }

    private void dropServers(int currentTime) {
        Set<Server> shouldDropServers = new HashSet<>();

        for (Server server : operatingServers) {
            if (server.shouldDrop(currentTime)) {
                shouldDropServers.add(server);
            }
        }

        for (Server server : shouldDropServers) {
            operatingServers.remove(server);
        }
    }

    private void expandServer(
            int currentTime,
            int operatingDuration,
            int playerCount,
            int serverVolume
    ) {
        int requiredServerCount = playerCount / serverVolume;

        while (operatingServers.size() < requiredServerCount) {
            operatingServers.add(new Server(currentTime, operatingDuration));
            expandCount++;
        }
    }

    private static class Server {
        final int expandedAt;
        final int operationDuration;

        public Server(int expandedAt, int operationDuration) {
            this.expandedAt = expandedAt;
            this.operationDuration = operationDuration;
        }

        public boolean shouldDrop(int currentTime) {
            int shouldDropAt = expandedAt + operationDuration;

            return currentTime >= shouldDropAt;
        }
    }
}
