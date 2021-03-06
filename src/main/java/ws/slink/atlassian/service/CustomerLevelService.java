package ws.slink.atlassian.service;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerLevelService {

    private static final int MAX_LEVEL=4;

    public static int getLevel(String projectKey, String value) {
        AtomicInteger result = new AtomicInteger(0);
        for (int i = 1; i <= MAX_LEVEL; i++) {
            if (Arrays.stream(ConfigService.instance().getList(projectKey, i).split(" "))
                .map(s -> s.trim())
                .filter(StringUtils::isNotBlank)
                .anyMatch(s
                    ->  s.contains("*")
                    ?   value.toLowerCase().contains(s.replaceAll("\\*", "").toLowerCase())
                    :   s.equalsIgnoreCase(value))) {
                result.set(i);
                break;
            }
        }
        // System.out.println("LEVEL FOR '" + value + "' is " + result);
        return result.get();
    }

}
