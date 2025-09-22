package baekjoon.gold;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.StringTokenizer;

public class B9466 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int testCount;
    private static Student[] students;
    private static final HashMap<Student, Integer> cycle = new HashMap<>();
    private static int result;
    private static final StringBuilder totalResult = new StringBuilder();

    public static void main(String[] args) throws IOException {
        init();

        controller();

        printer();
    }

    private static void init() throws IOException {
        testCount = Integer.parseInt(in.readLine());
    }

    private static void controller() throws IOException {
        for (int i = 0; i < testCount; i++) {
            beforeEach();

            for (int id = 1; id < students.length; id++) {
                if (students[id].checked) {
                    continue;
                }

                cycle.clear();
                grouping(students[id], 1, cycle);
            }

            totalResult.append(result).append(System.lineSeparator());
        }
    }

    private static void beforeEach() throws IOException {
        int totalStudent = Integer.parseInt(in.readLine());
        result = totalStudent;
        students = new Student[totalStudent+1];

        StringTokenizer input = new StringTokenizer(in.readLine(), " ");
        for (int id = 1; id < students.length; id++) {
            int nextStudentId = Integer.parseInt(input.nextToken());
            students[id] = new Student(id, nextStudentId);

            if (id == nextStudentId) {
                students[id].checked = true;
                result--;
            }
        }
    }

    private static void grouping(Student student, int order, HashMap<Student, Integer> cycle) {
        if (student.checked) {
            for (Student existStudent : cycle.keySet()) {
                existStudent.checked = true;
            }
            return;
        }

        if (cycle.containsKey(student)) {
            Integer existOrder = cycle.get(student);
            for (Student existStudent : cycle.keySet()) {
                existStudent.checked = true;
            }

            result -= cycle.size()+1 - existOrder;
            return;
        }

        cycle.put(student, order);

        Student nextStudent = students[student.nextStudentId];
        int nextOrder = order + 1;
        grouping(nextStudent, nextOrder, cycle);
    }

    private static void printer() throws IOException {
        out.write(totalResult.toString());

        out.close();
    }

    private static class Student {
        int id;
        int nextStudentId;
        boolean checked = false;

        public Student(int id, int nextStudentId) {
            this.id = id;
            this.nextStudentId = nextStudentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return id == student.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
}
