package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 백준 1374번 문제 - 강의실(골드)
 * 우선순위 큐, 그리디 알고리즘
 */
public class B1374 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Lectures lectures = readLectures();
        LectureRooms lectureRooms = new LectureRooms();

        for (Lecture lecture : lectures.lecturesSortedByStartAt) {
            if (lectureRooms.hasEmptyRoom(lecture)) {
                lectureRooms.use(lecture);
            } else {
                LectureRoom lectureRoom = new LectureRoom(lecture);
                lectureRooms.insert(lectureRoom);
            }
        }

        out.write(lectureRooms.size() + "");
        out.close();
    }

    private static Lectures readLectures() throws IOException {
        int lectureCount = Integer.parseInt(in.readLine());

        Set<Lecture> lectures = new HashSet<>();
        for (int i = 0; i < lectureCount; i++) {
            String[] lectureInput = in.readLine().split(" ");

            int id = Integer.parseInt(lectureInput[0]);
            int startAt = Integer.parseInt(lectureInput[1]);
            int endAt = Integer.parseInt(lectureInput[2]);

            lectures.add(new Lecture(id, startAt, endAt));
        }

        return new Lectures(lectures);
    }

    private static class LectureRooms {
        final PriorityQueue<LectureRoom> lectureRooms = new PriorityQueue<>();

        public boolean hasEmptyRoom(Lecture lecture) {
            return !lectureRooms.isEmpty() && lectureRooms.peek().canUse(lecture.startAt);
        }

        public void use(Lecture lecture) {
            LectureRoom usedLectureRoom = lectureRooms.poll();
            usedLectureRoom.canUseAt = lecture.endAt;

            lectureRooms.offer(usedLectureRoom);
        }

        public void insert(LectureRoom lectureRoom) {
            lectureRooms.offer(lectureRoom);
        }

        public int size() {
            return lectureRooms.size();
        }
    }

    private static class LectureRoom implements Comparable<LectureRoom> {
        int canUseAt;

        public LectureRoom(Lecture lecture) {
            this.canUseAt = lecture.endAt;
        }

        public void use(Lecture lecture) {
            this.canUseAt = lecture.endAt;
        }

        public boolean canUse(int currentTime) {
            return canUseAt <= currentTime;
        }

        @Override
        public int compareTo(LectureRoom o) {
            return this.canUseAt - o.canUseAt;
        }
    }

    private static class Lectures {
        final int minStartAt;
        final int maxEndAt;
        final List<Lecture> lecturesSortedByStartAt;

        public Lectures(Collection<Lecture> lectures) {
            lecturesSortedByStartAt = lectures.stream()
                    .sorted(Comparator.comparingInt(Lecture::getStartAt))
                    .collect(Collectors.toList());

            minStartAt = lecturesSortedByStartAt.get(0).startAt;
            maxEndAt = lecturesSortedByStartAt.get(lecturesSortedByStartAt.size() - 1).endAt;
        }
    }

    private static class Lecture {
        final int id;
        final int startAt;
        final int endAt;

        public Lecture(int id, int startAt, int endAt) {
            this.id = id;
            this.startAt = startAt;
            this.endAt = endAt;
        }

        public int getStartAt() {
            return startAt;
        }
    }
}
