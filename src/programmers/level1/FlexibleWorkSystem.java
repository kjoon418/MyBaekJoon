package programmers.level1;

import java.util.*;

/**
 * 유연근무제
 */
class FlexibleWorkSystem {
    
    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;
    private static final int SUNDAY = 7;
    
    private static final int ALLOWED_LIMIT = 10;
    
    public int solution(int[] schedules, int[][] timelogs, int startday) {
        List<Employee> employees = createEmployees(schedules, timelogs);
        
        int count = 0;
        for (Employee employee : employees) {
            if (isGoodEmployee(employee, startday)) {
                count++;
            }
        }
        
        return count;
    }
    
    private List<Employee> createEmployees(int[] schedules, int[][] timelogs) {
        List<Employee> employees = new ArrayList<>();
        
        for (int i = 0; i < schedules.length; i++) {
            Employee employee = new Employee(schedules[i], timelogs[i]);
            employees.add(employee);
        }
        
        return List.copyOf(employees);
    }
    
    private boolean isGoodEmployee(Employee employee, int startDay) {
        int day = startDay;
        for (Integer getToWorkTime : employee.getToWorkTimes) {
            if (isWeekend(day)) {
                day = getNextDay(day);
                continue;
            }
            
            if (isLate(getToWorkTime, employee.hopeTime)) {
                return false;
            }
            
            day = getNextDay(day);
        }
        
        return true;
    }
    
    private boolean isLate(int getToWorkTime, int hopeTime) {
        int allowedTime = getAllowedTime(hopeTime);
        
        return getToWorkTime > allowedTime;
    }
    
    private int getNextDay(int day) {
        if (day >= 7) {
            return 1;
        }
        
        return day + 1;
    }
    
    private boolean isWeekend(int day) {
        return day == 6 || day == 7;
    }
    
    private int getAllowedTime(int time) {
        int hour = getHour(time);
        int minute = getMinute(time) + ALLOWED_LIMIT;
        
        if (isOverOneMinute(minute)) {
            hour = getNextHour(hour);
            minute = getNextMinute(minute);
        }
        
        return getTime(hour, minute);
    }
    
    private int getHour(int time) {
        return time / 100;
    }
    
    private int getMinute(int time) {
        return time % 100;
    }
    
    private int getTime(int hour, int minute) {
        return (hour * 100) + minute;
    }
    
    private boolean isOverOneMinute(int minute) {
        return minute >= 60;
    }
    
    private int getNextHour(int hour) {
        if (hour >= 23) {
            return 0;
        }
        
        return hour + 1;
    }
    
    private int getNextMinute(int minute) {
        return minute % 60;
    }
    
    static class Employee {
        
        private final int hopeTime;
        private final List<Integer> getToWorkTimes;
        
        public Employee(int hopeTime, int[] timelog) {
            this.hopeTime = hopeTime;
            this.getToWorkTimes = convertArrayToList(timelog);
        }
        
        private List<Integer> convertArrayToList(int[] array) {
            ArrayList<Integer> list = new ArrayList<>();
            
            for (int element : array) {
                list.add(element);
            }
            
            return List.copyOf(list);
        }
        
    }
}
