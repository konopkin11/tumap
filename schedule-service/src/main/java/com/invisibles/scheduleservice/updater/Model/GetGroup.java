package com.invisibles.scheduleservice.updater.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.invisibles.scheduleservice.updater.constants.Constants;

import lombok.Data;

@Component
@Data
public class GetGroup {

    private final List<String> groupsListToParse = Arrays.asList("rtf", "fvs", "fet", "ef", "yuf", "zivf", "rkf", "fsu", "fit", "gf", "fb");
    public static final List<Group> groupsList = new ArrayList<>();
    private int groupParsed = 0;


    public List<Group> get() {
        if (!groupsList.isEmpty()) {
            return groupsList;
        } else {
            parseFacultiesPage();
            return groupsList;
        }
    }
    public CompletableFuture<Boolean> getGroupList() {
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        result.complete(true);
        return result;
    }
    //@Scheduled(fixedDelay = 3600000, initialDelay = 0)
    public void parseFacultiesPage() {
        System.out.println("I'm updating groups now");
        groupsListToParse.forEach(name -> {
            try {
                String html = request(name);
                parsing(html, name);
                groupParsed++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void parsing(String html, String faculty) {
        Document document = Jsoup.parse(html);
        Elements uls = document.select("ul.list-inline");
        for (Element ul : uls) {
            Elements lis = ul.select("li");
            for (Element li : lis) {
                String group = li.child(0).attr("href");
                String groupName = li.child(0).text();
                groupsList.add(new Group(group, groupName, faculty));
            }
        }
    }

    private String request(String name) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Constants.URL_GET_GROUPS + name, String.class);
    }
}
