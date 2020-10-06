package com.projectreveal.reveal.backend.flyway;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.migration.JavaMigration;
import org.flywaydb.core.internal.resolver.MigrationInfoHelper;
import org.flywaydb.core.internal.util.Pair;

public abstract class CustomFlywayMigrations implements JavaMigration {

	private final MigrationVersion version;
    private final String description;
    
    /**
     * Creates a new instance of a Java-based migration following Flyway's default naming convention.
     */
    public CustomFlywayMigrations() {
        String shortName = getClass().getSimpleName();
        String prefix;



        boolean repeatable = shortName.startsWith("R");
        if (shortName.startsWith("V") || repeatable



        ) {
            prefix = shortName.substring(0, 1);
        } else {
            throw new FlywayException("Invalid Java-based migration class name: " + getClass().getName()
                    + " => ensure it starts with V" +



                    " or R," +
                    " or implement org.flywaydb.core.api.migration.JavaMigration directly for non-default naming");
        }
        Pair<MigrationVersion, String> info =
                MigrationInfoHelper.extractVersionAndDescription(shortName, prefix, "__", new String[]{""}, repeatable);
        version = info.getLeft();
        description = info.getRight();
    }

    @Override
    public MigrationVersion getVersion() {
        return version;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean isUndo() {
        return false;
    }

    @Override
    public boolean canExecuteInTransaction() {
        return true;
    }




}
