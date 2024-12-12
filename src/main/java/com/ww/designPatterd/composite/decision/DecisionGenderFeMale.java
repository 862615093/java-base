package com.ww.designPatterd.composite.decision;

import com.ww.designPatterd.composite.DecisionComponent;
import com.ww.designPatterd.composite.Gender;
import com.ww.designPatterd.composite.User;

/**
 * 决策部分 - 性别
 */
public class DecisionGenderFeMale extends DecisionComponent {
    public DecisionGenderFeMale(String name) {
        super(name);
    }

    @Override
    protected boolean judge(User user) {
        return user.getGender() == Gender.FEMALE;
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
