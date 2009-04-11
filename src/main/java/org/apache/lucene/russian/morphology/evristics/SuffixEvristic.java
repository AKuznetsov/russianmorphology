package org.apache.lucene.russian.morphology.evristics;

/**
 * Represent evristic that assume that
 * canonical from of word is defined by word suffix.
 * It contains to suffixes from given position of
 * canonical word form and for form.
 */
public class SuffixEvristic {
    private String formSuffix;
    private String normalSuffix;

    public SuffixEvristic(String formSuffix, String normalSuffix) {
        this.formSuffix = formSuffix;
        this.normalSuffix = normalSuffix;
    }

    public String getFormSuffix() {
        return formSuffix;
    }

    public void setFormSuffix(String formSuffix) {
        this.formSuffix = formSuffix;
    }

    public String getNormalSuffix() {
        return normalSuffix;
    }

    public void setNormalSuffix(String normalSuffix) {
        this.normalSuffix = normalSuffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuffixEvristic that = (SuffixEvristic) o;

        if (!formSuffix.equals(that.formSuffix)) return false;
        if (!normalSuffix.equals(that.normalSuffix)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = formSuffix.hashCode();
        result = 31 * result + normalSuffix.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SuffixEvristic{" +
                "formSuffix='" + formSuffix + '\'' +
                ", normalSuffix='" + normalSuffix + '\'' +
                '}';
    }
}
