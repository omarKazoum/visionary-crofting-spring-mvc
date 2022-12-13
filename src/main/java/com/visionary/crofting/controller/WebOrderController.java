package com.visionary.crofting.controller;

import com.visionary.crofting.dto.OrderDTO;
import com.visionary.crofting.dto.OrderItemDTO;
import com.visionary.crofting.entity.Order;
import com.visionary.crofting.entity.Product;
import com.visionary.crofting.exceptions.BusinessException;
import com.visionary.crofting.response.ApiResponse;
import com.visionary.crofting.service.IOrderService;
import com.visionary.crofting.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.schema.Model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class WebOrderController {
    @Autowired
    IOrderService orderService;
    /***
     * get a list of all orders
     */
    @GetMapping("/")
    public ModelAndView getOrders(
                                @RequestParam(value = "page",required = false,defaultValue = "0") int pageIndex,
                                @RequestParam(name = "size", required = false,defaultValue = "5") int size,
                                @RequestParam(name = "ref", required = false,defaultValue = "") String reference){
        Map<String,Object> model=new HashMap<>();
        Page<Order> page=orderService.findAll(PageRequest.of(pageIndex,size),reference);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        model.put("orders",page.getContent());
        model.put("edit",true);
        model.put("page",page);
        model.put("formatter",formatter);
        return new ModelAndView("orders",model);
    }
    @GetMapping("/{ref}/changeStatus")
    View changeOrderStatus(@RequestParam(name = "status") Order.OrderStatusEnum newStatus, @PathVariable("ref") String reference){
        //TODO  update order status
        boolean isStatusChanged=false;
        try {
            isStatusChanged=orderService.updateStatus(reference,newStatus);
        }catch (BusinessException exception){
            exception.printStackTrace();
        }
        if(isStatusChanged){
            return new RedirectView("/orders/?msg=status changed successfully&type=success");
        }else{
            return new RedirectView("/orders/?msg=failed to change status&type=danger");
        }
    }


}
