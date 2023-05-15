package com.invisibles.scheduleservice.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.invisibles.scheduleservice.model.AuditoriumEntity;
import com.invisibles.scheduleservice.model.AuditoriumRepository;
import com.invisibles.scheduleservice.model.CourseLinkEntity;
import com.invisibles.scheduleservice.model.CourseLinkRepository;
import com.invisibles.scheduleservice.model.GroupEntity;
import com.invisibles.scheduleservice.model.GroupRepository;
import com.invisibles.scheduleservice.model.LessonEntity;
import com.invisibles.scheduleservice.model.LessonRepository;
import com.invisibles.scheduleservice.model.TeacherEntity;
import com.invisibles.scheduleservice.model.TeacherRepository;
import com.invisibles.scheduleservice.Utility.Utilities;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private TeacherRepository teacherRepository;
    private GroupRepository groupRepository;
    private AuditoriumRepository auditoriumRepository;
    private CourseLinkRepository courseLinkRepository;

    @Override
    public synchronized LessonEntity createLesson(LessonEntity lesson) {
        Optional<LessonEntity> optionalLesson = lessonRepository.getLessonEntityByLessonId(lesson.getLessonId());
        if (optionalLesson.isPresent()) {
            return null;
        }
        if (lesson.getCourseLinks() != null) {
            Set<CourseLinkEntity> courseLinkEntities = lesson.getCourseLinks();
            for (CourseLinkEntity courseLink : courseLinkEntities) {
               // CourseLinkEntity t = courseLinkRepository.save(courseLink);
                lesson.addCourseLink(courseLink);
            }
        }
        if (lesson.getTeachers() != null) {
            Set<TeacherEntity> teachers = lesson.getTeachers();
            Set<TeacherEntity> newTeachers = new HashSet<>();
            for (TeacherEntity teacher : teachers) {
                Optional<TeacherEntity> newTeacher = Optional.empty();
                if (teacher.getName() != null) {
                    newTeacher = teacherRepository.findByNameContaining(teacher.getName());
                } else if (teacher.getId() != null) {
                    newTeacher = teacherRepository.findById(teacher.getId());
                }
                if (newTeacher.isPresent()) {
                    newTeachers.add(newTeacher.get());
                } else {
                    TeacherEntity t = teacherRepository.save(teacher);
                    newTeachers.add(t);
                }
            }
            lesson.setTeachers(newTeachers);
        }
        if (lesson.getGroupEntities() != null) {
            Set<GroupEntity> groupEntities = lesson.getGroupEntities();
            Set<GroupEntity> newGroupEntities = new HashSet<>();
            for (GroupEntity groupEntity : groupEntities) {
                Optional<GroupEntity> newGroup = null;
                if (groupEntity.getNumber() != null) {
                    newGroup = groupRepository.findByNumber(groupEntity.getNumber());
                } else if (groupEntity.getId() != null) {
                    newGroup = groupRepository.findById(groupEntity.getId());
                }
                if (newGroup.isPresent()) {

                    newGroupEntities.add(newGroup.get());
                } else {
                    GroupEntity g = groupRepository.save(groupEntity);
                    newGroupEntities.add(g);
                }
            }
            optionalLesson = lessonRepository.getLessonEntityByLessonId(lesson.getLessonId());
            if (optionalLesson.isPresent()) {
                return null;
            }
            lesson.setGroupEntities(newGroupEntities);

        }
        if (lesson.getAuditoriums() != null) {
            Set<AuditoriumEntity> auditoriums = lesson.getAuditoriums();
            Set<AuditoriumEntity> newAuditoriumEntities = new HashSet<>();
            for (AuditoriumEntity auditorium : auditoriums) {
                Optional<AuditoriumEntity> newAuditorium = null;
                if (auditorium.getUrlKey() != null) {
                    newAuditorium = auditoriumRepository.findByUrlKey(auditorium.getUrlKey());
                } else if (auditorium.getId() != null) {
                    newAuditorium = auditoriumRepository.findById(auditorium.getId());
                } else if (auditorium.getNumber() != null) {
                    newAuditorium = auditoriumRepository.findByNumber(auditorium.getNumber());
                }
                if (newAuditorium.isPresent()) {

                    newAuditoriumEntities.add(newAuditorium.get());
                } else {
                    AuditoriumEntity a = auditoriumRepository.save(auditorium);
                    newAuditoriumEntities.add(a);
                }
            }
            lesson.setAuditoriums(newAuditoriumEntities);

        }
        return lessonRepository.save(lesson);
    }


    @Override
    public Optional<LessonEntity> getLessonById(String Id) {
        Optional<LessonEntity> optionalLesson = lessonRepository.getLessonEntityByLessonId(Id);
        return optionalLesson;
    }

    @Override
    public List<LessonEntity> getAllLessons() {
        return (List<LessonEntity>) lessonRepository.findAll();
    }


    @Override
    public LessonEntity updateLesson(LessonEntity lesson) {
        return null;
    }

//    @Override //todo ADD NEW FIELDS
//    public LessonEntity updateLesson(LessonEntity lesson) {
//        LessonEntity existingLesson = lessonRepository.findById(lesson.getId()).get();
//        existingLesson.setDate(lesson.getDate());
//        existingLesson.setTimeStart(lesson.getTimeStart());
//        existingLesson.setTimeEnd(lesson.getTimeEnd());
//        existingLesson.setTeachers(lesson.getTeachers());
//        existingLesson.setLessonNote(lesson.getLessonNote());
//        existingLesson.setType(lesson.getType());
//        existingLesson.setGroupEntities(lesson.getGroupEntities());
//        existingLesson.setAuditoriums(lesson.getAuditoriums());
//        LessonEntity updatedLesson = lessonRepository.save(existingLesson);
//        return updatedLesson;
//    }

    @Override
    public List<LessonEntity> getLessonByGroupUrlKey(String urlKey) {
        return lessonRepository.getLessonEntitiesByGroupEntitiesUrlKey(urlKey);

    }

    @Override
    public List<LessonEntity> getLessonByAuditoriumNumber(String number) {
        return lessonRepository.getLessonEntitiesByAuditoriumsNumber(number);
    }

    @Override
    public List<LessonEntity> getLessonByTeacherName(String name) {
        return lessonRepository.getLessonEntitiesByTeachersName(name);
    }

    @Override
    @Transactional
    public synchronized List<LessonEntity> getLessonsBetweenDates(String date1, String date2, String group) {
        LocalDate dateStart = Utilities.stringToDate(date1);
        LocalDate dateEnd = Utilities.stringToDate(date2);

        List<LessonEntity> optLessons = lessonRepository.findLessonsBetweenDatesAndGroupName(dateStart, dateEnd, group);

        List<LessonEntity> lessons = optLessons;
        lessons.forEach(lesson -> {
            lesson.getGroupEntities().size();
            lesson.getAuditoriums().size();
            lesson.getTeachers().size();
            // Initialize the groupEntities Set
        });
        return lessons;

    }

    @Override

    public synchronized List<LessonEntity> getLessonsForMonth(String date, String group) {
        LocalDate[] dates = Utilities.getMonthStartEnd(date);
        List<LessonEntity> optLessons = lessonRepository.findLessonsBetweenDatesAndGroupName(dates[0], dates[1], group);
        List<LessonEntity> lessons = optLessons;
        lessons.forEach(lesson -> {
            lesson.getGroupEntities().size();
            lesson.getAuditoriums().size();
            lesson.getTeachers().size();
        });
        return optLessons;

    }

    @Override
    public List<LessonEntity> getLessonsBetweenDatesTeacher(String date1, String date2, String teacherName) {
        LocalDate dateStart = Utilities.stringToDate(date1);
        LocalDate dateEnd = Utilities.stringToDate(date2);

        List<LessonEntity> optLessons = lessonRepository.findLessonsBetweenDatesAndTeacherName(dateStart, dateEnd, teacherName);

        List<LessonEntity> lessons = optLessons;
        lessons.forEach(lesson -> {
            lesson.getGroupEntities().size();
            lesson.getAuditoriums().size();
            lesson.getTeachers().size();
            // Initialize the groupEntities Set
        });
        return lessons;
    }

    @Override
    public List<LessonEntity> getLessonsForMonthTeacher(String date, String teacherName) {
        LocalDate[] dates = Utilities.getMonthStartEnd(date);
        List<LessonEntity> optLessons = lessonRepository.findLessonsBetweenDatesAndTeacherName(dates[0], dates[1], teacherName);
        List<LessonEntity> lessons = optLessons;
        lessons.forEach(lesson -> {
            lesson.getGroupEntities().size();
            lesson.getAuditoriums().size();
            lesson.getTeachers().size();
        });
        return optLessons;
    }

    @Override
    public List<LessonEntity> getLessonsBetweenAuditorium(String date1, String date2, String auditoriumNumber) {
        LocalDate dateStart = Utilities.stringToDate(date1);
        LocalDate dateEnd = Utilities.stringToDate(date2);

        List<LessonEntity> optLessons = lessonRepository.findLessonsBetweenDatesAndAuditoriumNumber(dateStart, dateEnd, auditoriumNumber);

        List<LessonEntity> lessons = optLessons;
        lessons.forEach(lesson -> {
            lesson.getGroupEntities().size();
            lesson.getAuditoriums().size();
            lesson.getTeachers().size();
            // Initialize the groupEntities Set
        });
        return lessons;
    }

    @Override
    public List<LessonEntity> getLessonsForMonthAuditorium(String date, String auditoriumNumber) {
        LocalDate[] dates = Utilities.getMonthStartEnd(date);
        List<LessonEntity> optLessons = lessonRepository.findLessonsBetweenDatesAndAuditoriumNumber(dates[0], dates[1], auditoriumNumber);
        List<LessonEntity> lessons = optLessons;
        lessons.forEach(lesson -> {
            lesson.getGroupEntities().size();
            lesson.getAuditoriums().size();
            lesson.getTeachers().size();
        });
        return optLessons;
    }

    @Override
    public void deleteLesson(Long Id) {
        lessonRepository.deleteById(Id);
    }
}
