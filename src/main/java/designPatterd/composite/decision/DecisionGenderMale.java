package designPatterd.composite.decision;

import designPatterd.composite.DecisionComponent;
import designPatterd.composite.Gender;
import designPatterd.composite.User;

/**
 * 决策部分 - 性别
 */
public class DecisionGenderMale extends DecisionComponent {

    public DecisionGenderMale(String name) {
        super(name);
    }

    @Override
    protected boolean judge(User user) {
        return user.getGender() == Gender.MALE;
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