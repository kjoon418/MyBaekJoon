package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * 백준 21939번 문제 - 문제 추천 시스템 Version 1(골드 4)
 */
public class B21939 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Recommender recommender = new Recommender();

        int problemCount = Integer.parseInt(in.readLine());
        for (int i = 0; i < problemCount; i++) {
            StringTokenizer problemInput = new StringTokenizer(in.readLine(), " ");
            int problemId = Integer.parseInt(problemInput.nextToken());
            int difficulty = Integer.parseInt(problemInput.nextToken());
            recommender.add(problemId, difficulty);
        }

        int commandCount = Integer.parseInt(in.readLine());
        for (int i = 0; i < commandCount; i++) {
            StringTokenizer commandInput = new StringTokenizer(in.readLine(), " ");
            String command = commandInput.nextToken();
            switch (command) {
                case "recommend" -> handleRecommend(commandInput, recommender);
                case "add" -> handleAdd(commandInput, recommender);
                case "solved" -> handleSolved(commandInput, recommender);
            }
        }

        out.close();
    }

    private static void handleRecommend(StringTokenizer input, Recommender recommender) throws IOException {
        boolean needEasiestProblem = input.nextElement().equals("-1");

        Problem recommendedProblem = recommender.recommend(needEasiestProblem);

        out.write(recommendedProblem.id + System.lineSeparator());
    }

    private static void handleAdd(StringTokenizer input, Recommender recommender) {
        int problemId = Integer.parseInt(input.nextToken());
        int difficulty = Integer.parseInt(input.nextToken());

        recommender.add(problemId, difficulty);
    }

    private static void handleSolved(StringTokenizer input, Recommender recommender) {
        int problemId = Integer.parseInt(input.nextToken());

        recommender.solved(problemId);
    }

    private static class Recommender {
        private final TreeMap<Integer, Problems> problemsByDifficulty = new TreeMap<>();
        private final Map<Integer, Problem> problemsById = new HashMap<>();

        public Problem recommend(boolean returnEasiestProblem) {
            if (returnEasiestProblem) {
                Integer easiestDifficulty = problemsByDifficulty.firstKey();
                Problems problems = problemsByDifficulty.get(easiestDifficulty);

                return problems.findLowestIdProblem();
            } else {
                Integer easiestDifficulty = problemsByDifficulty.lastKey();
                Problems problems = problemsByDifficulty.get(easiestDifficulty);

                return problems.findBiggestIdProblem();
            }
        }

        public void add(int problemId, int difficulty) {
            if (problemsById.containsKey(problemId)) {
                Problem problem = problemsById.get(problemId);
                problem.addDifficulty(difficulty);
                registerProblemByDifficulty(problem, difficulty);
            } else {
                Problem problem = new Problem(problemId, difficulty);
                problemsById.put(problemId, problem);
                registerProblemByDifficulty(problem, difficulty);
            }
        }

        public void solved(int problemId) {
            Problem removedProblem = problemsById.remove(problemId);
            for (Integer difficulty : removedProblem.difficulties) {
                Problems problems = problemsByDifficulty.get(difficulty);
                problems.removeById(problemId);

                if (problems.isEmpty()) {
                    problemsByDifficulty.remove(difficulty);
                }
            }
        }

        private void registerProblemByDifficulty(Problem problem, int difficulty) {
            if (problemsByDifficulty.containsKey(difficulty)) {
                Problems problems = problemsByDifficulty.get(difficulty);
                problems.add(problem);
            } else {
                Problems problems = new Problems(problem);
                problemsByDifficulty.put(difficulty, problems);
            }
        }
    }

    private static class Problems {
        private final TreeMap<Integer, Problem> problemsById = new TreeMap<>();

        public Problems(Problem problem) {
            add(problem);
        }

        public void add(Problem problem) {
            this.problemsById.put(problem.id, problem);
        }

        public void removeById(int problemId) {
            problemsById.remove(problemId);
        }

        public Problem findBiggestIdProblem() {
            return problemsById.lastEntry().getValue();
        }

        public Problem findLowestIdProblem() {
            return problemsById.firstEntry().getValue();
        }

        public boolean isEmpty() {
            return problemsById.isEmpty();
        }
    }

    private static class Problem implements Comparable<Problem> {
        private final int id;
        private final Set<Integer> difficulties = new HashSet<>();

        public Problem(int id, int difficulty) {
            this.id = id;
            addDifficulty(difficulty);
        }

        public void addDifficulty(int difficulty) {
            this.difficulties.add(difficulty);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Problem problem = (Problem) o;
            return id == problem.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public int compareTo(Problem o) {
            return this.id - o.id;
        }
    }
}
