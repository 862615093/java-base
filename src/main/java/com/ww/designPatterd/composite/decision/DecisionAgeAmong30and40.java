package com.ww.designPatterd.composite.decision;

import com.ww.designPatterd.composite.DecisionComponent;
import com.ww.designPatterd.composite.User;

/**
 * 决策部分 - 年龄 30 - 40 之间
 */
public class DecisionAgeAmong30and40 extends DecisionComponent {
    public DecisionAgeAmong30and40(String name) {
        super(name);
    }

    @Override
    protected boolean judge(User user) {
        return 30 <= user.getAge() && user.getAge() < 40;
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
