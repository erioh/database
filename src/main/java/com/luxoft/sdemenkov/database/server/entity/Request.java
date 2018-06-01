package com.luxoft.sdemenkov.database.server.entity;

import java.util.Map;
import java.util.Objects;

public final class Request {
    private final CommandType commandType;
    private final TargetType targetType;
    private final Map<String, String> requestParametersMap;

    public Request(CommandType commandType, TargetType targetType, Map<String, String> requestParametersMap) {
        this.commandType = commandType;
        this.targetType = targetType;
        this.requestParametersMap = requestParametersMap;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public Map<String, String> getRequestParametersMap() {
        return requestParametersMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return commandType == request.commandType &&
                targetType == request.targetType &&
                Objects.equals(requestParametersMap, request.requestParametersMap);
    }

    @Override
    public int hashCode() {

        return Objects.hash(commandType, targetType, requestParametersMap);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request{");
        sb.append("commandType=").append(commandType);
        sb.append(", targetType=").append(targetType);
        sb.append(", requestParametersMap=").append(requestParametersMap);
        sb.append('}');
        return sb.toString();
    }
}
