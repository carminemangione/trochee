package com.dictionaryapi.dto;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "entry_list")
public class EntryList {

    private String version;
    private List<Entry> entries;

    public EntryList() {
        this.entries = new ArrayList<>();
    }

    public String getVersion() {
        return version;
    }

    @XmlAttribute
    public void setVersion(String version) {
        this.version = version;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public TrocheeResolveType resolveIsTrochee(String word) {
        int totalMatches = 0;
        int isTrochee = 0;


        for (Entry entry : entries) {
            if (word.equalsIgnoreCase(entry.getEw())) {
                totalMatches++;
                if (entry.isTrochee()) {
                    isTrochee++;
                }
            }
        }
        return TrocheeResolveType.fromCounts(isTrochee, totalMatches);
    }

    @Override
    public String toString() {
        return "EntryList{" +
                "version='" + version + '\'' +
                ", entries=" + entries +
                '}';
    }

    public static enum TrocheeResolveType {
        PERFECT_TROCHEE,
        IMPERFECT_TROCHEE,
        NOT_TROCHEE;

        public static TrocheeResolveType fromCounts(int isTrochee, int totalMatches) {
            if (totalMatches == 0 || isTrochee == 0) {
                return NOT_TROCHEE;
            }
            if (isTrochee == totalMatches) {
                return PERFECT_TROCHEE;
            }
            return IMPERFECT_TROCHEE;
        }
    }
}
