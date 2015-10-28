package com.writeoncereadmany.util;

import java.util.stream.Collector;

/**
 * Created by tom on 28/10/2015.
 */
public class Joiner
{
    public static Collector<? super String, StringBuilder, String> joinWith(String separator)
    {
        return Collector.of(StringBuilder::new,
                (builder, string) -> builder.append(separator).append(string),
                (builder1, builder2) -> builder1.append(separator).append(builder2.toString()),
                StringBuilder::toString);
    }

}
