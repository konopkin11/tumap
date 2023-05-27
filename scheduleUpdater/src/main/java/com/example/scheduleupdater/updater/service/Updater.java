package com.example.scheduleupdater.updater.service;


import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.scheduleupdater.updater.Model.Auditorium;
import com.example.scheduleupdater.updater.Model.CourseLink;
import com.example.scheduleupdater.updater.Model.GetGroup;
import com.example.scheduleupdater.updater.Model.GetTimeTable;
import com.example.scheduleupdater.updater.Model.GetWeekId;
import com.example.scheduleupdater.updater.Model.Group;
import com.example.scheduleupdater.updater.Model.Pair;
import com.example.scheduleupdater.updater.Model.Teacher;

import com.example.scheduleupdater.updater.exception.RestTemplateResponseErrorHandler;
import com.example.scheduleupdater.updater.scheduleModels.AuditoriumEntity;
import com.example.scheduleupdater.updater.scheduleModels.CourseLinkEntity;
import com.example.scheduleupdater.updater.scheduleModels.GroupEntity;
import com.example.scheduleupdater.updater.scheduleModels.LessonEntity;
import com.example.scheduleupdater.updater.scheduleModels.TeacherEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Updater {

    //@Autowired
   // private EurekaClient eurekaClient;

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
            for (GroupEntity groupEntity : groupToGroupEntity(GetGroup.groupsList)) { //лист true/false???
                if (!isExistGroupByNumber(groupEntity.getNumber())) {
                    grEn.add(groupEntity);
                }
            }
            sendGroupsToService(grEn);


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
                            //
                            // throw new RuntimeException(e);

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
        GetTimeTable getTimeTable = new GetTimeTable(group, weekId, this);
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

    private Set<TeacherEntity> teacherToTeacherEntity(List<Teacher> teachers) {
        Set<TeacherEntity> teacherEntitySet = new HashSet<>();
        for (Teacher teacher : teachers) {
            TeacherEntity t = new TeacherEntity();
            t.setName(teacher.getName());
            t.setUrl(teacher.getUrl());
            teacherEntitySet.add(t);
        }
        return teacherEntitySet;
    }

    private Set<GroupEntity> groupToGroupEntity(List<Group> groups) {
        Set<GroupEntity> groupEntitySet = new HashSet<>();
        for (Group group : groups) {
            GroupEntity g = new GroupEntity();
            g.setUrlKey(group.getKey());
            g.setNumber(group.getName());
            g.setFaculty(group.getFaculty());
            groupEntitySet.add(g);
        }
        return groupEntitySet;
    }

    private Set<AuditoriumEntity> auditoriumToAuditoriumEntity(List<Auditorium> auditoriums) {
        Set<AuditoriumEntity> auditoriumEntitySet = new HashSet<>();
        for (Auditorium auditorium : auditoriums) {
            AuditoriumEntity a = new AuditoriumEntity();
            a.setNumber(auditorium.getNumber());
            a.setBuilding(auditorium.getBuilding());
            a.setUrlKey(auditorium.getUrl());
            auditoriumEntitySet.add(a);
        }
        return auditoriumEntitySet;
    }

    private Set<CourseLinkEntity> courseToCourseEntity(List<CourseLink> courseLinks) {
        Set<CourseLinkEntity> courseEntitySet = new HashSet<>();
        for (CourseLink link : courseLinks) {
            CourseLinkEntity c = new CourseLinkEntity();
            c.setAnchor(link.getAnchor());
            c.setGroupname(link.getGroup());
            c.setUrl(link.getUrl());
            courseEntitySet.add(c);
        }
        return courseEntitySet;
    }

    public String getUrlScheduleService() {
        String vipAddress = "schedule-service";

        InstanceInfo nextServerInfo = null;
        try {
            //nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            //log.info(nextServerInfo.getIPAddr() + ":" + nextServerInfo.getPort());
            //return "http://localhost:8090";
            return "https://tumap.invisibles-studio.space";//nextServerInfo.getIPAddr() + ":" + nextServerInfo.getPort();
        } catch (Exception e) {
            log.error("Cannot get an instance of example service to talk to from eureka");

        }


        return null;
//        InstanceInfo service = eurekaClient
//                .getApplication("schedule-service")
//                .getInstances()
//                .get(0);
//
//        String hostName = service.getHostName();
//        int port = service.getPort();
//
    }
    HttpHeaders createHeaders(){
        String adminuserCredentials = "user:password";
        String encodedCredentials =
                new String(Base64.encodeBase64(adminuserCredentials.getBytes()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
    private String postRequest(Object toJson, String path) {
        String url = getUrlScheduleService();
        if (url == null) {
            return null;
        }
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = createHeaders();
        String json = new GsonBuilder().setExclusionStrategies(new ExclStrategy()).create().toJson(toJson);
        if(Objects.equals(json, "[]")) return "ok" ;
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        String response = template.postForObject(
                (url + path),
                entity, String.class);
        return response;
    }

    private ResponseEntity<String> getRequest(String path){
        String url = getUrlScheduleService();
        if (url == null) return new ResponseEntity<>("null", HttpStatus.SERVICE_UNAVAILABLE);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        try {

             response = template.getForEntity(
                    ( url + path), String.class);
        }
        catch (HttpClientErrorException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
    public void sendLessonToService(LessonEntity lesson) {
        String response = postRequest(lesson, "/lesson");

        log.info("Response sendLessonToService from service C for sys info :" + response);
    }

    public void sendGroupsToService(Set<GroupEntity> group) {
        String response = postRequest(group, "/group");
        log.info("Response sendGroupsToService from service C for sys info :" + response);
    }

    public boolean isExistAuditoriumByUrl(String audLink) {
        ResponseEntity<String> response = getRequest("/auditorium/" + audLink);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Response isExistAuditoriumByUrl from service C for sys info :" + response);
            return true;
        }
        return false;

    }

    public boolean isExistLessonByIdAndDate(String lessonId, String date) {
        ResponseEntity<String> response = getRequest("/lesson/" + lessonId + "/date/"+date);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Response isExistLessonById from service C for sys info :" + response);
            return true;
        }
        return false;
    }

    public boolean isExistGroupByNumber(String number) {
        ResponseEntity<String> response = getRequest("/group/" + number);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Response  isExistGroupByNumber from service C for sys info :" + response);
            return true;
        }
        return false;
    }
}
