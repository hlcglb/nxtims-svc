package com.hyundaiuni.nxtims.util;

import java.util.Objects;

public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils{
    private ObjectUtils() {}

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
