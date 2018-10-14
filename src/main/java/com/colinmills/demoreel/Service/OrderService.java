package com.colinmills.demoreel.Service;

import com.colinmills.demoreel.Entity.Book;
import com.colinmills.demoreel.Entity.Order;
import com.colinmills.demoreel.Entity.OrderComparator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private List<Order> buyOrders = new ArrayList<>();
    private List<Order> sellOrders = new ArrayList<>();
    private OrderComparator orderComparator = new OrderComparator();

    public Book handleBookings() {
        Book book = new Book();

        if(buyOrders != null) {
            buyOrders.sort(orderComparator);
            Collections.reverse(buyOrders);
            book.setBuys(buyOrders.toArray(new Order[buyOrders.size()]));
        }

        if(sellOrders != null) {
            sellOrders.sort(orderComparator);
            book.setSells(sellOrders.toArray(new Order[sellOrders.size()]));
        }

        return book;
    }

    public void handleBuy(Order buyOrder) {

        if (sellOrders.size() > 0) {
            // sort sell orders by price, ascending
            sellOrders.sort(orderComparator);
            Order sellOrder = sellOrders.get(0);

            // deduct orders from both buyOrder and sell order until either buyOrder.qty = 0 or sell price > buyOrder.prc
            while (buyOrder.getQty() > 0 && sellOrder.getPrc() <= buyOrder.getPrc()) {
                float buyQty = buyOrder.getQty();
                float sellQty = sellOrder.getQty();

                if(buyQty >= sellQty){
                    buyQty -= sellQty;
                    buyOrder.setQty(buyQty);
                    sellOrder.setQty(0);
                } else {
                    sellQty -= buyQty;
                    buyOrder.setQty(0);
                    sellOrder.setQty(sellQty);
                }

                if(sellOrders.indexOf(sellOrder) < sellOrders.size()-1){
                    sellOrder = sellOrders.get(sellOrders.indexOf(sellOrder)+1);
                } else {
                    break;
                }
            }
        }

        List<Order> removeThese = sellOrders.stream().filter(order -> order.getQty() == 0).collect(Collectors.toList());
        sellOrders.removeAll(removeThese);

        if (buyOrder.getQty() > 0) {
            buyOrders.add(buyOrder);
        }
    }

    public void handleSell(Order sellOrder) {

        if(buyOrders.size() > 0) {
            // sort buy orders by price, descending
            buyOrders.sort(orderComparator);
            Collections.reverse(buyOrders);
            Order buyOrder = buyOrders.get(0);

            // deduct from buy orders until we're out of sell orders or the buys get too cheap
            while (sellOrder.getQty() > 0 && sellOrder.getPrc() <= buyOrder.getPrc()) {
                float buyQty = buyOrder.getQty();
                float sellQty = sellOrder.getQty();

                if (sellQty >= buyQty) {
                    sellQty -= buyQty;
                    sellOrder.setQty(sellQty);
                    buyOrder.setQty(0);
                } else {
                    buyQty -= sellQty;
                    sellOrder.setQty(0);
                    buyOrder.setQty(buyQty);
                }

                if (buyOrders.indexOf(buyOrder) < buyOrders.size() - 1) {
                    buyOrder = buyOrders.get(buyOrders.indexOf(buyOrder) + 1);
                } else {
                    break;
                }
            }
        }

        List<Order> removeThese = buyOrders.stream().filter(order -> order.getQty() == 0).collect(Collectors.toList());
        buyOrders.removeAll(removeThese);

        if (sellOrder.getQty() > 0) {
            sellOrders.add(sellOrder);
        }
    }
}
