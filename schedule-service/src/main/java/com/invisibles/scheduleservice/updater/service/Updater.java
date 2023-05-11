package com.invisibles.scheduleservice.updater.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.invisibles.scheduleservice.model.AuditoriumEntity;
import com.invisibles.scheduleservice.model.CourseLinkEntity;
import com.invisibles.scheduleservice.model.GroupEntity;
import com.invisibles.scheduleservice.model.GroupRepository;
import com.invisibles.scheduleservice.model.LessonEntity;
import com.invisibles.scheduleservice.model.TeacherEntity;
import com.invisibles.scheduleservice.service.AuditoriumService;
import com.invisibles.scheduleservice.service.LessonService;
import com.invisibles.scheduleservice.updater.Model.Auditorium;
import com.invisibles.scheduleservice.updater.Model.CourseLink;
import com.invisibles.scheduleservice.updater.Model.GetGroup;
import com.invisibles.scheduleservice.updater.Model.GetTimeTable;
import com.invisibles.scheduleservice.updater.Model.GetWeekId;
import com.invisibles.scheduleservice.updater.Model.Group;
import com.invisibles.scheduleservice.updater.Model.Pair;
import com.invisibles.scheduleservice.updater.Model.Teacher;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Updater {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    private GroupRepository groupRepository;

    @Scheduled(fixedDelay = 3600000, initialDelay = 0)
    public void UpdateTimeTable() throws ExecutionException, InterruptedException {

        GetGroup getGroups = new GetGroup();
        getGroups.get();
        boolean groupListReady = getGroups.getGroupList().get();
        if (groupListReady) {
            System.out.println(GetGroup.groupsList.size());
            int weekIdStart = GetWeekId.Get("2023-04-24");
            int weekIdEnd = GetWeekId.Get("2023-06-30");
            int numOfThreads = 5; // You can adjust this based on your requirements
            ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
            Set<GroupEntity> grEn = new HashSet<>();
            for (GroupEntity groupEntity : groupToGroupEntity(GetGroup.groupsList) ){
                if(groupRepository.findByNumber(groupEntity.getNumber()).isEmpty()){
                    grEn.add(groupEntity);
                }
            }
            groupRepository.saveAll(grEn);

            for (int j = 0; j < GetGroup.groupsList.size(); j++) {
                Group group = GetGroup.groupsList.get(j);
                executorService = Executors.newFixedThreadPool(numOfThreads);
                for (int i = weekIdStart; i < weekIdEnd; i++) {

                    log.info(group.getName() + " - " + group.getFaculty() + " - " + j + " - week:" + i);
                    int finalI = i;
                    executorService.submit(() -> {
                        try {
                            getPairsForAWeek(finalI, group);
                        } catch (InterruptedException e) {
                            log.error(e.getMessage());
                            throw new RuntimeException(e);

                        }
                    });
                    //getPairsForAWeek(i, group);

                }
                executorService.shutdown();
                try {
                    // Wait for all tasks to complete before moving to the next line
                    executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                    log.info("parsed group " + group.getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }

            }
        }
    }



    public void getPairsForAWeek(int weekId, Group group) throws InterruptedException {
        GetTimeTable getTimeTable = new GetTimeTable(group, weekId, auditoriumService, lessonService, this);
//        List<Pair> data = getTimeTable.get();
//        for (Pair current : data) {
//            if (Objects.equals(current.getName(), "no_pair")) continue;
//            lessonService.createLesson(createLesson(current));
//        }
        log.info("parsed group " + group.getName() + " week id " + weekId);
        //TODO logging
    }

    public LessonEntity createLesson(Pair pair) {
        LessonEntity lesson = new LessonEntity();
        lesson.setDate(pair.getDate());
        lesson.setTimeStart(pair.getTime().get(0));
        lesson.setTimeEnd(pair.getTime().get(1));
        lesson.setType(pair.getPairType());
        lesson.setTeachers(teacherToTeacherEntity(pair.getTeacher()));
        lesson.setGroupEntities(groupToGroupEntity(pair.getGroup()));
        lesson.setAuditoriums(auditoriumToAuditoriumEntity(pair.getAuditorium()));
        lesson.setLessonId(pair.getLessonId());
        lesson.setLessonNote(pair.getNote());
        lesson.setName(pair.getName());
        lesson.setCourseLinks(courseToCourseEntity(pair.getLinks()));
        return lesson;
    }

    private Set<TeacherEntity> teacherToTeacherEntity(List<Teacher> teachers){
        Set<TeacherEntity> teacherEntitySet = new HashSet<>();
        for (Teacher teacher : teachers){
            TeacherEntity t = new TeacherEntity();
            t.setName(teacher.getName());
            t.setUrl(teacher.getUrl());
            teacherEntitySet.add(t);
        }
        return teacherEntitySet;
    }

    private Set<GroupEntity> groupToGroupEntity(List<Group> groups){
        Set<GroupEntity> groupEntitySet = new HashSet<>();
        for (Group group : groups){
            GroupEntity g = new GroupEntity();
            g.setUrlKey(group.getKey());
            g.setNumber(group.getName());
            g.setFaculty(group.getFaculty());
            groupEntitySet.add(g);
        }
        return groupEntitySet;
    }

    private Set<AuditoriumEntity> auditoriumToAuditoriumEntity(List<Auditorium> auditoriums){
        Set<AuditoriumEntity> auditoriumEntitySet = new HashSet<>();
        for (Auditorium auditorium : auditoriums){
            AuditoriumEntity a = new AuditoriumEntity();
            a.setNumber(auditorium.getNumber());
            a.setBuilding(auditorium.getBuilding());
            a.setUrlKey(auditorium.getUrl());
            auditoriumEntitySet.add(a);
        }
        return auditoriumEntitySet;
    }

    private Set<CourseLinkEntity> courseToCourseEntity(List<CourseLink> courseLinks){
        Set<CourseLinkEntity> courseEntitySet = new HashSet<>();
        for (CourseLink link : courseLinks){
            CourseLinkEntity c = new CourseLinkEntity();
            c.setAnchor(link.getAnchor());
            c.setGroupname(link.getGroup());
            c.setUrl(link.getUrl());
            courseEntitySet.add(c);
        }
        return courseEntitySet;
    }
}
