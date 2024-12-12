package com.ww.designPatterd.composite.decision;

import com.ww.designPatterd.composite.DecisionComponent;
import com.ww.designPatterd.composite.User;

/**
 * 决策部分 - 年龄 小于 20 岁
 */
public class DecisionAgeLess20 extends DecisionComponent {
    public DecisionAgeLess20(String name) {
        super(name);
    }

    @Override
    protected boolean judge(User user) {
        return user.getAge() < 20;
    }

    @Override
    protected void add(DecisionComponent decisionComponent) {
        decisionComponents.add(decisionComponent);
    }

    @Override
    protected void remove(DecisionComponent decisionComponent) {
        decisionComponents.remove(decisionComponent);
    }
}