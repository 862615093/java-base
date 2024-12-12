package com.ww.designPatterd.composite;

import com.ww.designPatterd.composite.decision.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestComposite {

    public static void main(String[] args) {

        //构建决策树-组合模式根节点
        DecisionComponent decisionComposite = new DecisionComposite("用户信息");

        //添加决策-叶子节点
        DecisionComponent genderFeMale = new DecisionGenderFeMale("性别女");
        DecisionComponent genderMale = new DecisionGenderMale("性别男");

        decisionComposite.add(genderFeMale);
        decisionComposite.add(genderMale);

        //添加决策-子节点的子节点
        DecisionComponent ageAmong20and30 = new DecisionAgeAmong20and30("20-30年龄之间");
        DecisionComponent ageAmong30and40 = new DecisionAgeAmong30and40("30-40年龄之间");
        DecisionComponent ageLess20 = new DecisionAgeLess20("小于20岁");
        DecisionComponent ageThan40 = new DecisionAgeThan40("大于40岁");

        genderMale.add(ageAmong20and30);
        genderMale.add(ageAmong30and40);
        genderMale.add(ageLess20);
        genderMale.add(ageThan40);

        //添加决策结果-叶子节点
        ageLess20.add(new DecisionResult("决策结果", "玩具"));
        ageAmong30and40.add(new DecisionResult("决策结果", "枸杞"));

        //测试
        User user = new User(Gender.MALE, 35);
        DecisionComponent decisionResult = decisionComposite.decision(user);
        log.info("决策结果：{}", decisionResult);


    }


}
