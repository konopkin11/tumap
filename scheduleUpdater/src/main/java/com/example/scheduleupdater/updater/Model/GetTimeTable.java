package com.example.scheduleupdater.updater.Model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.scheduleupdater.updater.constants.Constants;
import com.example.scheduleupdater.updater.service.Updater;

public class GetTimeTable {

    private int weekId;
    private final Group group;

    private List<Pair> data = new ArrayList<>();
    private boolean startRequest;


    private final Updater updater;


    public GetTimeTable(Group group, int weekId, Updater updater ) {
        if (weekId != 0 ) {
            this.weekId = weekId;
        }

        this.updater = updater;
        this.group = group;
        this.startRequest = true;

        start();
    }

    private void start() {
        try {
            String html = request();
            data = getDataFromPageNew(html);
            startRequest = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Pair> getDataFromPageNew(String html) {
        Document doc = Jsoup.parse(html);
        //Element table = document.select("table.table").first();
        List<Pair> sortedPairs = new ArrayList<>();
        Elements table = doc.select(".screen-reader-element");
        Elements elements = table.select("div.js-lesson-info-modal");

        for (Element element : elements) {
            String lessonId = element.attr("data-lesson-id");
            if(updater.isExistLessonById(lessonId)){
                continue;
            }
            String courseLinksUrl = element.attr("data-course-links-url");
            //get course links
            List<CourseLink> courseLinks = new ArrayList<>();
            try {
                 courseLinks = parseCourseLinkUrls(Constants.URL_BASE_TIMETABLE + courseLinksUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //-----
            String lessonTitle = element.select("h4.modal-title").text().trim();
            String lessonType = element.select("p:contains(Вид занятия:)").text().replace("Вид занятия:", "").trim();
            String lessonDate = element.select("p:contains(Дата проведения:)").text().replace("Дата проведения:", "").trim();
            String lessonTime = element.select("p:contains(Время проведения:)").text().replace("Время проведения:", "").trim();
            String lessonNote = element.select("p:contains(Комментарий:)").text().replace("Место проведения:", "").trim();

            // get auditorium list
            Elements lessonLocation = element.select("p:contains(Место проведения:)");

            List<Auditorium> auditoriums = new ArrayList<>();
            for (String audLink : extractLocationLinks(lessonLocation)){

                if(!updater.isExistAuditoriumByUrl(audLink)){
                try {
                    Document audDoc = Jsoup.connect(Constants.URL_BASE_TIMETABLE + audLink).get();
                    auditoriums.add(extractAuditoriumInfo(audDoc, audLink));
                } catch (IOException e) {
                    throw new RuntimeException(e); //todo logger
                }}
            }
            //---
            Elements lessonTeachers = element.select("p.modal-info-teachers a");
            List<Teacher> lessonTeacherList = new ArrayList<>();
            for (Element teacherLink : lessonTeachers) {
                Teacher t = new Teacher();
                t.setUrl(teacherLink.attr("href"));
                t.setName(teacherLink.text());
                lessonTeacherList.add(t);
            }

            Elements lessonGroupsElements = element.select("p:contains(Совместно с группами:) a");
            List<Group> groupList = new ArrayList<>();
            for (Element lessonGroupElement : lessonGroupsElements){
                Group g = new Group(
                        lessonGroupElement.attr("href"),
                        lessonGroupElement.text(),
                        lessonGroupElement.attr("href").split("/")[2]
                );
                groupList.add(g);
            }
            groupList.add(group);
            updater.sendLessonToService(updater.createLesson(new Pair(lessonTitle,
                    lessonType,
                    auditoriums,
                    groupList,
                    lessonNote,
                    lessonTeacherList,
                    lessonDate,
                    Arrays.stream(lessonTime.split("-")).toList(),
                    courseLinks,
                    lessonId
                    )));
            // pairs.add(new Pair(name, pairType, auditorium, group, note, teacher));
        }
        return sortedPairs;
    }
    public List<Pair> get() throws InterruptedException {
        while (startRequest) {
            Thread.sleep(1000);
        }
        return data;
    }

    private String request() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.URL_BASE_TIMETABLE + group.getKey()  + "?week_id=" + weekId;
        return restTemplate.getForObject(url, String.class);
    }


    private static <T> List<List<T>> sliceIntoChunks(List<T> list, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            List<T> chunk = list.subList(i, Math.min(list.size(), i + chunkSize));
            chunks.add(chunk);
        }
        return chunks;
    }

    public static List<CourseLink> parseCourseLinkUrls(String courseLinksUrl) throws IOException {
        URL jsonUrl = new URL(courseLinksUrl);
        BufferedReader reader = new BufferedReader(new InputStreamReader(jsonUrl.openStream()));
        Type listType = new TypeToken<List<CourseLink>>(){}.getType();
        List<CourseLink> courseLinks = new Gson().fromJson(reader, listType);

        return courseLinks;
    }

    public static String[] extractLocationLinks(Elements lessonLocation) {

        Elements links = lessonLocation.select("span.auditoriums a");
        String[] linkHrefs = new String[links.size()];
        int i = 0;
        for (Element link : links) {
            String linkHref = link.attr("href");
            linkHrefs[i++] = linkHref;
        }
        return linkHrefs;
    }

    private Auditorium extractAuditoriumInfo(Document doc, String url) {
        Element breadcrumbDiv = doc.selectFirst("div.breadcrumbs");
        Element spanElement = breadcrumbDiv.selectFirst("span");
        String auditoriumName = spanElement.text();
        Element h4Element = doc.selectFirst("h4.modal-title");
        String building = h4Element.text();
        Auditorium auditorium = new Auditorium();
        auditorium.setUrl(url);
        auditorium.setBuilding(building);
        auditorium.setNumber(auditoriumName);
        return auditorium;
    }
}
