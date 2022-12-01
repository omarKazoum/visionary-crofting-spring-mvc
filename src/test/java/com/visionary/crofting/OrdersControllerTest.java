package com.visionary.crofting;

import com.visionary.crofting.controller.OrderController;
import com.visionary.crofting.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {OrderController.class})
public class OrdersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    IOrderService orderService;
    @Test
    void testGetALlOrders(){
        mockMvc.perform(get("/api/orders")).andExpect(status().isOk());
    }

}
