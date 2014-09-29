package com.dictionaryapi.dto;

import javax.xml.bind.annotation.XmlElement;

public class Entry {

    private String id;
    private String ew;
    private String fl;
    private String pr;

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    public String getEw() {
        return ew;
    }

    @XmlElement
    public void setEw(String ew) {
        this.ew = ew;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getPr() {
        return pr;
    }

    @XmlElement
    public void setPr(String pr) {
        this.pr = pr;
    }

    public boolean isTrochee() {
        boolean toReturn = false;
        if (pr != null) {
            final String[] syllables = pr.split("\\-");
            toReturn = syllables.length == 2 && syllables[0].charAt(0) == 712;
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", ew='" + ew + '\'' +
                ", fl='" + fl + '\'' +
                ", pr='" + pr + '\'' +
                '}';
    }
}
