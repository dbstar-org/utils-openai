package io.github.dbstarll.utils.openai;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractOpenAiClientTest {
    private static final String TOKEN_KEY = "OPEN_AI_KEY";

    protected final String getOpenAiKey() {
        final String keyFromProperty = System.getProperty(TOKEN_KEY);
        if (StringUtils.isNotBlank(keyFromProperty)) {
            return keyFromProperty;
        }

        final String opts = System.getenv("MAVEN_OPTS");
        if (StringUtils.isNotBlank(opts)) {
            for (String opt : StringUtils.split(opts)) {
                if (opt.startsWith("-D" + TOKEN_KEY + "=")) {
                    return opt.substring(3 + TOKEN_KEY.length());
                }
            }
        }

        return null;
    }
}
