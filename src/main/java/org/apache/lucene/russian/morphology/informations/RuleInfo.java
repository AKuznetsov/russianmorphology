package org.apache.lucene.russian.morphology.informations;

import java.io.Serializable;


public class RuleInfo implements Serializable {
    private Heuristic[][] rules;

    public RuleInfo(Heuristic[][] rules) {
        this.rules = rules;
    }

    public Heuristic[] getRule(short ruleId) {
        return rules[ruleId];
    }
}
