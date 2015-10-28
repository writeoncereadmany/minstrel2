package com.writeoncereadmany.util;

import java.util.stream.Collector;

public class Joiner
{
    public static Collector<? super String, JoinerBuilder, String> joinWith(String separator)
    {
        return Collector.of(() -> new JoinerBuilder(separator), JoinerBuilder::append, JoinerBuilder::combine, JoinerBuilder::toString);
    }

    private static class JoinerBuilder
    {
        private final String separator;
        private final StringBuilder stringBuilder = new StringBuilder();
        private boolean started = false;

        public JoinerBuilder(String separator)
        {
            this.separator = separator;
        }

        public JoinerBuilder append(String toAppend)
        {
            if(started)
            {
               stringBuilder.append(separator);
            }
            stringBuilder.append(toAppend);
            started = true;
            return this;
        }

        public JoinerBuilder combine(JoinerBuilder builder)
        {
            return append(builder.toString());
        }

        public String toString()
        {
            return stringBuilder.toString();
        }
    }
}
