package com.colinmills.demoreel.Entity;

import java.util.Comparator;

public class OrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if(o1.getPrc() < o2.getPrc()){
            return -1;
        } else if (o1.getPrc() > o2.getPrc()){
            return 1;
        }

        return 0;
    }
}
