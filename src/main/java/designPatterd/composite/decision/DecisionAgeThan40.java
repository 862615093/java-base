package designPatterd.composite.decision;

import designPatterd.composite.DecisionComponent;
import designPatterd.composite.User;

/**
 * 决策部分 - 年龄 大于 40 岁
 */
public class DecisionAgeThan40 extends DecisionComponent {

    public DecisionAgeThan40(String name) {
        super(name);
    }

    @Override
    protected boolean judge(User user) {
        return 40 <= user.getAge();
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