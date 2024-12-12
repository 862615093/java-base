package com.ww.designPatterd.composite;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 决策组件
 */
@Slf4j
public abstract class DecisionComponent {

    //组件名称
    protected String name;

    protected List<DecisionComponent> decisionComponents = new ArrayList<>();


    public DecisionComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 决策 ：递归调用 + 遍历集合
     *
     * @param user 用户信息
     * @return 决策结果
     */
    protected DecisionComponent decision(User user) {
        log.info("外层 class={}", this.getClass());
        if (judge(user)) {
            log.info("进入 {} 决策分支", getName());
            for (DecisionComponent decisionComponent : decisionComponents) {
                log.info("for 循环 class={}", decisionComponent.getClass());
                if (decisionComponent.judge(user)) {
                    return decisionComponent.decision(user);
                }
            }
        }
        return null;
    }

    //决策方法
    protected abstract boolean judge(User user);

    //添加决策方法
    protected abstract void add(DecisionComponent decisionComponent);

    //移除决策方法
    protected abstract void remove(DecisionComponent decisionComponent);

}
