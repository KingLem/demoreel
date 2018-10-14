package com.colinmills.demoreel.Controller;

import com.colinmills.demoreel.Entity.Book;
import com.colinmills.demoreel.Entity.Order;
import com.colinmills.demoreel.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;

@RestController
public class TransactionController {
    @Resource
    OrderService orderService;

    @GetMapping("/book")
    public @ResponseBody
    ResponseEntity<Book> getBookings() {
        Book response = orderService.handleBookings();
        ResponseEntity<Book> okayResponse = new ResponseEntity<>(response, HttpStatus.OK);
        return okayResponse;
    }

    @PostMapping(path = "/buy")
    public void buyRequest(WebRequest request) throws Exception {
        // add buy order
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(request.getParameterNames().next(), Order.class);
        orderService.handleBuy(order);
    }

    @PostMapping(path = "/sell")
    public void sellRequest(WebRequest request) throws Exception {
        // add sell order
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(request.getParameterNames().next(), Order.class);
        orderService.handleSell(order);
    }
}
