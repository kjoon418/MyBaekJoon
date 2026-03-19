package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * 백준 21608번 문제 - 상어 초등학교(골드)
 */
public class B21608 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Map<Point, Point> emptyPoints = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Map<Integer, Student> students = readStudents();
        List<Student> sortedStudents = students.values()
                .stream()
                .sorted(Comparator.comparingInt(Student::getFixedOrder))
                .collect(Collectors.toList());

        for (Student student : sortedStudents) {
            Point suitablePoint = findSuitablePoint(student);
            student.point = suitablePoint;
            emptyPoints.remove(suitablePoint);
            emptyPoints.values().forEach(Point::clear);
        }

        long satisfactionTotal = students.values()
                .stream()
                .mapToLong(Student::getSatisfactionTotal)
                .sum();

        out.write(satisfactionTotal + "");
        out.close();
    }

    private static Map<Integer, Student> readStudents() throws IOException {
        int classroomSize = Integer.parseInt(in.readLine());
        int studentCount = classroomSize * classroomSize;
        initEmptyPoints(classroomSize);

        Map<Integer, Student> students = new HashMap<>();
        for (int studentId = 1; studentId <= studentCount; studentId++) {
            students.put(studentId, new Student(studentId));
        }

        for (int i = 0; i < studentCount; i++) {
            StringTokenizer studentInput = new StringTokenizer(in.readLine(), " ");
            int studentId = Integer.parseInt(studentInput.nextToken());
            Student student = students.get(studentId);
            student.fixedOrder = i;
            while (studentInput.hasMoreElements()) {
                int likeStudentId = Integer.parseInt(studentInput.nextToken());
                Student likeStudent = students.get(likeStudentId);
                student.addLikeStudent(likeStudent);
            }
        }

        return students;
    }

    private static void initEmptyPoints(int classroomSize) {
        for (int y = 0; y < classroomSize; y++) {
            for (int x = 0; x < classroomSize; x++) {
                Point point = new Point(x, y);
                emptyPoints.put(point, point);
            }
        }
    }

    private static Point findSuitablePoint(Student student) {
        Map<Point, Point> likeStudentCandidatePoints = new HashMap<>();

        for (Student likeStudent : student.likeStudents) {
            if (!likeStudent.isPositionFixed()) {
                continue;
            }

            Point upperPoint = likeStudent.getUpperPoint();
            setCandidate(upperPoint, likeStudentCandidatePoints);

            Point lowerPoint = likeStudent.getLowerPoint();
            setCandidate(lowerPoint, likeStudentCandidatePoints);

            Point leftPoint = likeStudent.getLeftPoint();
            setCandidate(leftPoint, likeStudentCandidatePoints);

            Point rightPoint = likeStudent.getRightPoint();
            setCandidate(rightPoint, likeStudentCandidatePoints);
        }

        if (!likeStudentCandidatePoints.isEmpty()) {
            int maxAdjacentLikeStudentCount = likeStudentCandidatePoints.keySet()
                    .stream()
                    .mapToInt(Point::getAdjacentLikeStudentCount)
                    .max()
                    .orElseThrow();
            List<Point> adjacentLikeStudentPoints = likeStudentCandidatePoints.keySet()
                    .stream()
                    .filter(point -> point.adjacentLikeStudentCount == maxAdjacentLikeStudentCount)
                    .collect(Collectors.toList());
            if (adjacentLikeStudentPoints.size() == 1) {
                return adjacentLikeStudentPoints.get(0);
            } else {
                return findSuitablePointByEmpty(adjacentLikeStudentPoints);
            }
        }

        return findSuitablePointByEmpty(emptyPoints.values());
    }

    private static Point findSuitablePointByEmpty(Collection<Point> candidatePoints) {
        for (Point adjacentEmptyCandidate : candidatePoints) {
            if (emptyPoints.containsKey(adjacentEmptyCandidate.getUpperPoint())) {
                adjacentEmptyCandidate.adjacentEmptyPointCount++;
            }
            if (emptyPoints.containsKey(adjacentEmptyCandidate.getLowerPoint())) {
                adjacentEmptyCandidate.adjacentEmptyPointCount++;
            }
            if (emptyPoints.containsKey(adjacentEmptyCandidate.getLeftPoint())) {
                adjacentEmptyCandidate.adjacentEmptyPointCount++;
            }
            if (emptyPoints.containsKey(adjacentEmptyCandidate.getRightPoint())) {
                adjacentEmptyCandidate.adjacentEmptyPointCount++;
            }
        }
        int maxEmptyPointCount = candidatePoints.stream()
                .mapToInt(Point::getAdjacentEmptyPointCount)
                .max()
                .orElseThrow();
        List<Point> adjacentEmptyPoints = candidatePoints.stream()
                .filter(point -> point.adjacentEmptyPointCount == maxEmptyPointCount)
                .collect(Collectors.toList());

        return adjacentEmptyPoints.stream()
                .sorted()
                .findFirst()
                .orElseThrow();
    }

    private static void setCandidate(Point newPoint, Map<Point, Point> candidatePoints) {
        if (emptyPoints.containsKey(newPoint)) {
            if (candidatePoints.containsKey(newPoint)) {
                Point existPoint = candidatePoints.get(newPoint);
                existPoint.adjacentLikeStudentCount++;
            } else {
                newPoint.adjacentLikeStudentCount++;
                candidatePoints.put(newPoint, newPoint);
            }
        }
    }

    private static class Student {
        private final int id;
        private int fixedOrder;

        private Point point = null;

        private final Set<Student> likeStudents = new HashSet<>();

        public Student(int id) {
            this.id = id;
        }

        public void addLikeStudent(Student student) {
            likeStudents.add(student);
        }

        public int getFixedOrder() {
            return fixedOrder;
        }

        public boolean isPositionFixed() {
            return point != null;
        }

        public Point getUpperPoint() {
            return point.getUpperPoint();
        }

        public Point getLowerPoint() {
            return point.getLowerPoint();
        }

        public Point getLeftPoint() {
            return point.getLeftPoint();
        }

        public Point getRightPoint() {
            return point.getRightPoint();
        }

        public int getSatisfactionTotal() {
            int adjacentCount = 0;
            for (Student likeStudent : likeStudents) {
                if (isAdjacent(likeStudent)) {
                    adjacentCount++;
                }
            }

            return switch (adjacentCount) {
                case 0 -> 0;
                case 1 -> 1;
                case 2 -> 10;
                case 3 -> 100;
                case 4 -> 1000;
                default -> throw new IllegalArgumentException();
            };
        }

        private boolean isAdjacent(Student student) {
            return point.equals(student.getUpperPoint())
                    || point.equals(student.getLowerPoint())
                    || point.equals(student.getLeftPoint())
                    || point.equals(student.getRightPoint());
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Student student = (Student) o;
            return id == student.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    private static class Point implements Comparable<Point> {
        final int x;
        final int y;

        int adjacentLikeStudentCount = 0;
        int adjacentEmptyPointCount = 0;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point getUpperPoint() {
            return new Point(x, y - 1);
        }

        public Point getLowerPoint() {
            return new Point(x, y + 1);
        }

        public Point getLeftPoint() {
            return new Point(x - 1, y);
        }

        public Point getRightPoint() {
            return new Point(x + 1, y);
        }

        public int getAdjacentLikeStudentCount() {
            return adjacentLikeStudentCount;
        }

        public int getAdjacentEmptyPointCount() {
            return adjacentEmptyPointCount;
        }

        public void clear() {
            this.adjacentLikeStudentCount = 0;
            this.adjacentEmptyPointCount = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Point o) {
            if (this.y != o.y) {
                return y - o.y;
            }

            return x - o.x;
        }
    }
}
