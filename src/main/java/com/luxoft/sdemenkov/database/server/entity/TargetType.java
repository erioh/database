package com.luxoft.sdemenkov.database.server.entity;

public enum TargetType {
    SCHEMA("SCHEMA"), TABLE("TABLE"),ATTRIBUTE("ATTRIBUTE");
    private final String name;

    TargetType(String name) {
        this.name = name;
    }

    public static TargetType getByName(String name) {
        for (TargetType targetType : values()) {
            if (targetType.name.equals(name)) {
                return targetType;
            }
        }
        throw new RuntimeException("Unknown TargetType "+ name);
    }
}
