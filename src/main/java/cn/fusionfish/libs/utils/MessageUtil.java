package cn.fusionfish.libs.utils;

import cn.fusionfish.libs.plugin.FusionPlugin;

import java.util.logging.Logger;

public class MessageUtil {

    private static final Logger LOGGER = FusionPlugin.getInstance().getLogger();

    public static void log(String msg) {
        LOGGER.info("§a" + msg);
    }

    public static void warn(String msg) {
        LOGGER.warning(msg);
    }

    public static void error(String msg) {
        LOGGER.severe(msg);
    }
}
