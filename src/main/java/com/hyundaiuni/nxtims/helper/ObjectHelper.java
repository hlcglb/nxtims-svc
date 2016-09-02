package com.hyundaiuni.nxtims.helper;

import java.util.Objects;

public class ObjectHelper {
    private ObjectHelper() {}

    public static String toString(Object[] objs, String nullDefault) {
        if(objs == null) {
            return nullDefault;
        }

        StringBuilder builder = new StringBuilder();
        final int len = objs.length;
        int i = 0;

        while(i < len) {
            builder.append("obj[" + i + "]=" + Objects.toString(objs[i], nullDefault));

            if(i < len - 1) {
                builder.append(",");
            }

            i++;
        }

        return builder.toString();
    }
}
