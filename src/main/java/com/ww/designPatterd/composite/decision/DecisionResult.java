package com.ww.designPatterd.composite.decision;

import com.ww.designPatterd.composite.DecisionComponent;
import com.ww.designPatterd.composite.User;

import java.util.StringJoiner;

/**
 * 决策结果
 */
public class DecisionResult extends DecisionComponent {
    /**
     * 商品类型
     */
    private String type;

    public DecisionResult(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DecisionResult.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .toString();
    }

    @Override
    protected DecisionComponent decision(User user) {
        return this;
    }

    @Override
    protected boolean judge(User user) {
        return true;
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
