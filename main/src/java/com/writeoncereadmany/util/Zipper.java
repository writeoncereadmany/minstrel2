package com.writeoncereadmany.util;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;


public class Zipper
{
    public static <L, R> List<Pair<L, R>> zip(List<L> left, List<R> right)
    {
        if(left.size() != right.size())
        {
            throw new IllegalArgumentException("Lists are not the same size");
        }
        ArrayList<Pair<L, R>> zipped = new ArrayList<>();
        for(int i = 0; i < left.size(); i++)
        {
            zipped.add(new Pair<>(left.get(i), right.get(i)));
        }
        return unmodifiableList(zipped);
    }
}
