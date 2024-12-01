package com.ww.streamStudy.groupby;

import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByTest {


    public static void main(String[] args) {

        List<Student> scoreList = new ArrayList<>();
        scoreList.add(new Student().setStudentId("001").setScoreYear("2018").setScore(100));
        scoreList.add(new Student().setStudentId("001").setScoreYear("2019").setScore(59));
        scoreList.add(new Student().setStudentId("001").setScoreYear("2019").setScore(99));
        scoreList.add(new Student().setStudentId("002").setScoreYear("2018").setScore(99));


        //根据scoreYear和studentId字段进行分组
        Map<String, List<Student>> map = scoreList.stream()
                .collect(Collectors.groupingBy(score -> score.getScoreYear()+'-'+score.getStudentId()));

        Map<String, List<String>> collect = map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(e -> {
                                    return e.getStudentId() + e.getScoreYear();
                                })
                                .collect(Collectors.toList())
                ));

        System.out.println(JSONUtil.toJsonPrettyStr(collect));






    }
}
