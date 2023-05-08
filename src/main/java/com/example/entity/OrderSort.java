package com.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderSort {
    private List<String> lawyer;
    private List<Integer> sort;

    public OrderSort(List<String> lawyer, List<Integer> sort) {
        this.lawyer = lawyer;
        this.sort = sort;
    }
}
