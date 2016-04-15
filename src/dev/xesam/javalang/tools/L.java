package dev.xesam.javalang.tools;

/**
 * Created by xe on 16-4-15.
 */
public class L {
    public static void log(Object... objects) {
        StringBuilder stringBuilder = new StringBuilder();
        if (objects == null) {
            stringBuilder.append("_null");
        } else {
            for (Object object : objects) {
                stringBuilder.append(String.valueOf(object)).append(" ");
            }
        }
        System.out.println(stringBuilder.toString());
    }
}
