package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        ArrayList<TrainingSession> listTrainingSession = new ArrayList<>(); // Список для тренировок
        listTrainingSession.add(trainingSession); // добавили тренировку в список

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeTraining = new TreeMap<>(); // мапа для списка тренировок
        timeTraining.put(trainingSession.getTimeOfDay(), listTrainingSession); // добавили в мапу список

        timetable.put(trainingSession.getDayOfWeek(), timeTraining);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> map = timetable.getOrDefault(dayOfWeek, new TreeMap<>()); // для того чтобы небыло NULL
        return map.getOrDefault(timeOfDay, new ArrayList<>()); // чтобы не было исключения.
    }
}
