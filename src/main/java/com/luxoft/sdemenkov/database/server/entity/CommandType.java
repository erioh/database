package com.luxoft.sdemenkov.database.server.entity;

public enum CommandType {
    CREATE("CREATE"), DROP("DROP"), SHOW("SHOW"), INSERT("INSERT"), SELECT("SELECT");
    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public static CommandType getByName(String name) {
        for (CommandType commandType : values()) {
            if (commandType.name.equals(name)) {
                return commandType;
            }
        }
        throw new RuntimeException("Unknown command " + name);
    }
}
