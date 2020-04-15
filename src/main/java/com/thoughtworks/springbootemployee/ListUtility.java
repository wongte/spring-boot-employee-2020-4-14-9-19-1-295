package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListUtility<T> {
    public List<T> getListByPage(List<T> list, Integer page, Integer pageSize) {
        if (page == null) page = 1;
        if (pageSize == null) pageSize = list.size();

        int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize - 1;

        if (endIndex >= list.size()) {
            endIndex = list.size();
        }

        return IntStream.range(startIndex, endIndex + 1).boxed()
                .map(list::get)
                .collect(Collectors.toList());
    }
}
