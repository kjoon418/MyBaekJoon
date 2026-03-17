package baekjoon.gold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * 백준 11000번 문제 - 강의실 배정(골드)
 * 우선순위 큐, 그리디 알고리즘
 */
public class B11000 {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Lectures lectures = readLectures();
        LectureRooms lectureRooms = new LectureRooms();

        for (Lecture lecture : lectures.getLecturesSortedByStartAt()) {
            lectureRooms.useRoom(lecture);
        }

        out.write(lectureRooms.size() + "");
        out.close();
    }

    private static Lectures readLectures() throws IOException {
        int lectureCount = Integer.parseInt(in.readLine());

        ArrayList<Lecture> lectures = new ArrayList<>();
        for (int i = 0; i < lectureCount; i++) {
            String[] lectureInput = in.readLine().split(" ");

            int startAt = Integer.parseInt(lectureInput[0]);
            int endAt = Integer.parseInt(lectureInput[1]);
            lectures.add(new Lecture(startAt, endAt));
        }

        return new Lectures(lectures);
    }

    private static final class LectureRooms {
        private final PriorityQueue<LectureRoom> lectureRooms = new PriorityQueue<>();

        public void useRoom(Lecture lecture) {
            if (hasEmptyRoom(lecture)) {
                LectureRoom emptyRoom = lectureRooms.poll();

                emptyRoom.use(lecture);
                lectureRooms.offer(emptyRoom);
            } else {
                LectureRoom lectureRoom = new LectureRoom(lecture);
                lectureRooms.offer(lectureRoom);
            }
        }

        public int size() {
            return lectureRooms.size();
        }

        private boolean hasEmptyRoom(Lecture lecture) {
            return !lectureRooms.isEmpty() && lectureRooms.peek().canUse(lecture);
        }
    }

    private static final class LectureRoom implements Comparable<LectureRoom> {
        private int canUseAt;

        public LectureRoom(Lecture lecture) {
            this.canUseAt = lecture.endAt;
        }

        public boolean canUse(Lecture lecture) {
            return canUseAt <= lecture.startAt;
        }

        public void use(Lecture lecture) {
            canUseAt = lecture.endAt;
        }

        @Override
        public int compareTo(LectureRoom o) {
            return this.canUseAt - o.canUseAt;
        }
    }

    private static final class Lectures {
        private final List<Lecture> lecturesSortedByStartAt;

        public Lectures(Collection<Lecture> lectures) {
            lecturesSortedByStartAt = lectures.stream()
                    .sorted(Comparator.comparingInt(Lecture::getStartAt))
                    .collect(Collectors.toList());
        }

        public List<Lecture> getLecturesSortedByStartAt() {
            return lecturesSortedByStartAt;
        }
    }

    private static final class Lecture {
        private final int startAt;
        private final int endAt;

        public Lecture(int startAt, int endAt) {
            this.startAt = startAt;
            this.endAt = endAt;
        }

        public int getStartAt() {
            return startAt;
        }
    }
}
