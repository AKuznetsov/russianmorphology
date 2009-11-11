package org.apache.lucene.morphology;

import java.io.Serializable;
import java.util.HashSet;


public class PrefixRule implements Serializable {
    private Character lastLetter;
    private String prefix;
    private HashSet<String> forms;

    public Character getLastLetter() {
        return lastLetter;
    }

    public void setLastLetter(Character lastLetter) {
        this.lastLetter = lastLetter;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public HashSet<String> getForms() {
        return forms;
    }

    public void setForms(HashSet<String> forms) {
        this.forms = forms;
    }

    public String getHashString() {
        return "" + prefix.charAt(0) + lastLetter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrefixRule that = (PrefixRule) o;

        if (forms != null ? !forms.equals(that.forms) : that.forms != null) return false;
        if (lastLetter != null ? !lastLetter.equals(that.lastLetter) : that.lastLetter != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lastLetter != null ? lastLetter.hashCode() : 0;
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (forms != null ? forms.hashCode() : 0);
        return result;
    }
}
