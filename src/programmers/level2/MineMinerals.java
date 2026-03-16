package programmers.level2;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * 광물 캐기
 */
class MineMinerals {
    
    public int solution(int[] picks, String[] minerals) {
        List<State> states = new ArrayList<>();
        State firstState = initState(picks);
        states.add(firstState);
        
        for (int i = 0; i < minerals.length; i += 5) {
            List<State> nextStates = new ArrayList<>();
            for (State state : states) {
                if (state.diamondPickCounts > 0) {
                    nextStates.add(calculateFatigue(minerals, i, Pick.DIAMOND, state));
                }
                if (state.ironPickCounts > 0) {
                    nextStates.add(calculateFatigue(minerals, i, Pick.IRON, state));
                }
                if (state.stonePickCounts > 0) {
                    nextStates.add(calculateFatigue(minerals, i, Pick.STONE, state));
                }
            }
            if (nextStates.isEmpty()) {
                break;
            }
            
            states = nextStates;
        }
        
        return states.stream()
            .mapToInt(state -> state.totalFatigue)
            .min()
            .orElseThrow();
    }
    
    private State initState(int[] picks) {
        return new State(
            picks[0],
            picks[1],
            picks[2],
            0
        );
    }
    
    private State calculateFatigue(
        String[] minerals,
        int startsAt,
        Pick pick,
        State prevState
    ) {
        int fatigueSum = 0;
        for (int i = startsAt; i < minerals.length && i < startsAt + 5; i++) {
            Mineral mineral = Mineral.from(minerals[i]);
            fatigueSum += mineral.fatigue(pick);
        }
        
        return prevState.usePick(pick, fatigueSum);
    }
    
    private static class State {
        final int diamondPickCounts;
        final int ironPickCounts;
        final int stonePickCounts;
        
        final int totalFatigue;
        
        public State(
            int diamondPickCounts,
            int ironPickCounts,
            int stonePickCounts,
            int totalFatigue
        ) {
            this.diamondPickCounts = diamondPickCounts;
            this.ironPickCounts = ironPickCounts;
            this.stonePickCounts = stonePickCounts;
            this.totalFatigue = totalFatigue;
        }
        
        public State usePick(Pick pick, int addFatigue) {
            int nextFatigue = totalFatigue + addFatigue;
            if (pick == Pick.DIAMOND) {
                return new State(
                    diamondPickCounts - 1,
                    ironPickCounts,
                    stonePickCounts,
                    nextFatigue
                );
            }
            if (pick == Pick.IRON) {
                return new State(
                    diamondPickCounts,
                    ironPickCounts - 1,
                    stonePickCounts,
                    nextFatigue
                );
            }
            return new State(
                diamondPickCounts,
                ironPickCounts,
                stonePickCounts - 1,
                nextFatigue
            );
        }
    }
    
    private enum Pick {
        DIAMOND, IRON, STONE
    }
    
    private enum Mineral {
        DIAMOND("diamond", 1, 5, 25),
        IRON("iron", 1, 1, 5),
        STONE("stone", 1, 1, 1);
        
        final String label;
        final int fatigueFromDiamondPick;
        final int fatigueFromIronPick;
        final int fatigueFromStonePick;
        
        Mineral(
            String label,
            int fatigueFromDiamondPick,
            int fatigueFromIronPick,
            int fatigueFromStonePick
        ) {
            this.label = label;
            this.fatigueFromDiamondPick = fatigueFromDiamondPick;
            this.fatigueFromIronPick = fatigueFromIronPick;
            this.fatigueFromStonePick = fatigueFromStonePick;
        }
        
        public static Mineral from(String input) {
            return Arrays.stream(values())
                .filter(mineral -> mineral.label.equals(input))
                .findAny()
                .orElseThrow();
        }
        
        public int fatigue(Pick pick) {
            return switch(pick) {
                    case DIAMOND -> fatigueFromDiamondPick;
                    case IRON -> fatigueFromIronPick;
                    case STONE -> fatigueFromStonePick;
            };
        }
    }
}
