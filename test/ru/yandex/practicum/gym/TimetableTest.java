package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.MonthDay;
import java.util.*;


public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        ArrayList<TrainingSession> list = new ArrayList<>();
        for (ArrayList<TrainingSession> ls : trainingSessions.values()) {
            list.addAll(ls);
        }

        assertEquals(1, list.size(), "Должно вернуться 1");

        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessions1 =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        ArrayList<TrainingSession> list1 = new ArrayList<>();
        for (ArrayList<TrainingSession> ls : trainingSessions.values()) {
            list.addAll(ls);
        }

        assertTrue(list1.isEmpty(), "Должно вернуть true");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsInMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        ArrayList<TrainingSession> listMonday = new ArrayList<>();

        for (ArrayList<TrainingSession> ls : trainingSessionsInMonday.values()) {
            listMonday.addAll(ls);
        }
        assertEquals(1, listMonday.size(), "Должон вернуться 1");

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsInThursday =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        ArrayList<TrainingSession> listThursday = new ArrayList<>();
        for (ArrayList<TrainingSession> ls : trainingSessionsInThursday.values()) {
            listThursday.addAll(ls);
        }
        assertEquals(2, listThursday.size(), "Должно вернуться 2");

        List<TimeOfDay> timeList = new ArrayList<>(trainingSessionsInThursday.keySet());

        TimeOfDay firstTime = new TimeOfDay(13, 0);
        TimeOfDay secondTime = new TimeOfDay(20, 0);

        assertEquals(firstTime, timeList.get(0));
        assertEquals(secondTime, timeList.get(1));


        // Проверить, что за вторник не вернулось занятий

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsInTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, trainingSessionsInTuesday.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        ArrayList<TrainingSession> firstList = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, firstList.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        ArrayList<TrainingSession> secondList = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(0, secondList.size());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach firstCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach secondCoach = new Coach("Петрук", "Евгений", "Григорьевич");
        TrainingSession firstTrainingSession = new TrainingSession(group, firstCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, firstCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(20, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(group, secondCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(15, 40));
        timetable.addNewTrainingSession(firstTrainingSession);


        // Проверить, что вернется список с одним занятием
        assertEquals(1, timetable.getCountByCoaches().size());
        // Проверить, что вернется Нужный coach
        Coach expectedCoach = new Coach("Васильев", "Николай", "Сергеевич");
        assertEquals(expectedCoach, timetable.getCountByCoaches().getFirst().getCoach());

        //Проверить, что вернется для Васи 2 занятия
        timetable.addNewTrainingSession(secondTrainingSession);
        int expectedCount = 2;
        assertEquals(expectedCount, timetable.getCountByCoaches().getFirst().getCount());

        //Проверить, что вернется список из 2 коучей
        timetable.addNewTrainingSession(thirdTrainingSession);
        int expectedCountCouach = 2;
        assertEquals(expectedCountCouach, timetable.getCountByCoaches().size());

        //Проверить, что список отсортирован верно

        Coach expectedFistCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach expectedSecondCoach = new Coach("Петрук", "Евгений", "Григорьевич");

        int expectedCountFirstCoach = 2;
        int expectedCountSecondCoach = 1;

        assertEquals(expectedFistCoach, timetable.getCountByCoaches().getFirst().getCoach());
        assertEquals(expectedSecondCoach, timetable.getCountByCoaches().getLast().getCoach());

        assertEquals(expectedCountFirstCoach, timetable.getCountByCoaches().getFirst().getCount());
        assertEquals(expectedCountSecondCoach, timetable.getCountByCoaches().getLast().getCount());


    }
}
