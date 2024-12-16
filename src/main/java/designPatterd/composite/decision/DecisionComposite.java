package designPatterd.composite.decision;

import designPatterd.composite.DecisionComponent;
import designPatterd.composite.User;

/**
 * 决策部分
 */
public class DecisionComposite extends DecisionComponent {

    public DecisionComposite(String name) {
        super(name);
    }

    @Override
    protected boolean judge(User user) {
        return user != null;
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