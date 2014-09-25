package me.trochee.db;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.readLines;

public class Queries {

    public static String fromResource(String resourcePath){
        try {
            List<String> lines = new ArrayList<>();
            lines.add("-- " + resourcePath + "\n");
            lines.addAll(readLines(getResource(resourcePath), Charsets.UTF_8));
            return Joiner.on("\n").join(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Queries() {
    }
}
