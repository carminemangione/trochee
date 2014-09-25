package me.trochee.config;

import java.io.File;

public enum TrocheeConfigDirectory {

    INSTANCE;

    private static final String CONFIG_PROP = "TROCHEE_CONFIG_DIR";

    private final String configurationDirectory;

    TrocheeConfigDirectory() {
        String configurationDirectory = System.getProperty(CONFIG_PROP);
        if (configurationDirectory == null) {
            configurationDirectory = System.getenv(CONFIG_PROP);
        }
        this.configurationDirectory = configurationDirectory;
    }

    public File getFile() {
        return new File(configurationDirectory);
    }

    public boolean present() {
        return configurationDirectory != null;
    }
}
