package com.visionary.crofting;

import com.visionary.crofting.entity.Order;
import com.visionary.crofting.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class TestOrdersIntegration {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderRepository orderRepository;
    @Test
    void testGetAllOrders()throws Exception{
        Order order=new Order();
        order.setId(1L);
        order.setTotalPrice(100);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        System.out.println(orderRepository.findAll());
        mockMvc.perform(get("/api/orders/")).andDo(rh->{
            System.out.println("response is:\n"+rh.getResponse().getContentAsString());
        }).andExpect(jsonPath("$.data[0].totalPrice",is(100.0)));

    }
}
