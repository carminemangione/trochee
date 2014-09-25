package me.trochee.db;

import com.google.common.io.Resources;
import me.trochee.config.TrocheeConfigDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum TrocheeDataSourceFactory {

    INSTANCE;

    private final Properties properties = new Properties();

    private TrocheeDataSourceFactory() {
        try {
            try (InputStream in = Resources.getResource("config/db.properties").openStream()) {
                properties.load(in);
            }
            if (TrocheeConfigDirectory.INSTANCE.present()) {
                File file = new File(TrocheeConfigDirectory.INSTANCE.getFile(), "db.properties");
                try (InputStream in = new FileInputStream(file)) {
                    properties.load(in);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PooledDataSource make() {
        try {
            return PooledDataSourceFactory.buildDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
