package com.zqw.gp.component.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zqw.gp.component.entity.LeftTicketDTO;
import com.zqw.gp.component.entity.Ticket;
import com.zqw.gp.component.service.client.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zqw
 * @date 2023/3/7 20:30
 */
@RequestMapping("ticket")
@RestController
public class TicketController extends BaseController{
    @Autowired
    private TicketClient ticketClient;

    @Autowired
    private Gson gson;

    @PostMapping("query")
    public Object query(@RequestBody JsonObject jo){
        LeftTicketDTO dto= gson.fromJson(jo, LeftTicketDTO.class);
        List<Ticket> data = ticketClient.queryTicket(dto);
        return this.success(data);
    }
}
