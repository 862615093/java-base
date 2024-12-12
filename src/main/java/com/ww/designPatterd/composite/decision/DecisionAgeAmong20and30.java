package com.ww.designPatterd.composite.decision;

import com.ww.designPatterd.composite.DecisionComponent;
import com.ww.designPatterd.composite.User;

/**
 * 决策部分 - 年龄 20 - 30 之间
 */
public class DecisionAgeAmong20and30 extends DecisionComponent {
    public DecisionAgeAmong20and30(String name) {
        super(name);
    }

    @Override
    protected boolean judge(User user) {
        return 20 <= user.getAge() && user.getAge() < 30;
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