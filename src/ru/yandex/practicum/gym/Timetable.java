package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }   /* По заданию алгоритмическая сложность должна быть O(1).
        Если использовать TreeMap О(log n), не соблюдение ТЗ.
        Так же не нашел в задании, для чего нужна сортировка по дням.
        И все методы получения всегда имеют аргументы в виде ключа.*/


    public void addNewTrainingSession(TrainingSession trainingSession) {
        Map<TimeOfDay, List<TrainingSession>> dayMap =
                timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>());

        List<TrainingSession> trainingList =
                dayMap.computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>());
        trainingList.add(trainingSession);
    }


    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        Map<TimeOfDay, List<TrainingSession>> map = timetable.get(dayOfWeek);
        List<TrainingSession> trainingSessionList = new ArrayList<>();
        map.forEach((key, value) -> trainingSessionList.addAll(value));
        Collections.sort(trainingSessionList);
        return trainingSessionList;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> map = timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        return map.getOrDefault(timeOfDay, new ArrayList<>()); // чтобы не было исключения.
    }

    public List<CounterOfTrainings> getCountByCoaches() {

        Map<Coach, Integer> mapCoachCount = new HashMap<>();

        for (DayOfWeek day : timetable.keySet()) {
            Map<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);
            if (dayMap != null) {
                for (Map.Entry<TimeOfDay, List<TrainingSession>> entry : dayMap.entrySet()) {
                    for (TrainingSession session : entry.getValue()) {
                        Coach coach = session.getCoach();
                        mapCoachCount.put(coach, mapCoachCount.getOrDefault(coach, 0) + 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> list = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : mapCoachCount.entrySet()) {
            list.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(list);
        return list;
    }
}

