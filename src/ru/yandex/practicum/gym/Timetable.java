package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) { // переделать

        if (!timetable.containsKey(trainingSession.getDayOfWeek())) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMap = new TreeMap<>();
            ArrayList<TrainingSession> trainingList = new ArrayList<>();

            trainingList.add(trainingSession);
            dayMap.put(trainingSession.getTimeOfDay(), trainingList);
            timetable.put(trainingSession.getDayOfWeek(), dayMap);

        } else {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMap = timetable.get(trainingSession.getDayOfWeek());
            if (!dayMap.containsKey(trainingSession.getTimeOfDay())) {
                ArrayList<TrainingSession> trainingList = new ArrayList<>();
                trainingList.add(trainingSession);
                dayMap.put(trainingSession.getTimeOfDay(), trainingList);
            } else {
                ArrayList<TrainingSession> trainingList = dayMap.get(trainingSession.getTimeOfDay());
                trainingList.add(trainingSession);
            }
        }
    }


    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> map = timetable.getOrDefault(dayOfWeek, new TreeMap<>()); // для того чтобы небыло NULL
        return map.getOrDefault(timeOfDay, new ArrayList<>()); // чтобы не было исключения.
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {

        HashMap<Coach, Integer> mapCoachCount = new HashMap<>();

        for (DayOfWeek day : timetable.keySet()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMap = timetable.get(day);
            if (dayMap != null) {
                for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> entry : dayMap.entrySet()) {
                    for (TrainingSession session : entry.getValue()) {
                        Coach coach = session.getCoach();
                        mapCoachCount.put(coach, mapCoachCount.getOrDefault(coach, 0) + 1);
                    }
                }
            }
        }

        ArrayList<CounterOfTrainings> list = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : mapCoachCount.entrySet()) {
            list.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(list);
        return list;
    }
}
