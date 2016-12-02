package ru.itpark.soa;

import java.util.Map;

public interface Command {

    Map<String, Object> execute(CommandContext cntx) throws Exception;
}
