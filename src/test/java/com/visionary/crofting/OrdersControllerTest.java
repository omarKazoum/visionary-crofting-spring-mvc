package com.visionary.crofting;

import com.visionary.crofting.controller.OrderController;
import com.visionary.crofting.entity.Order;
import com.visionary.crofting.repository.OrderRepository;
import com.visionary.crofting.repository.SupplierRepository;
import com.visionary.crofting.service.IOrderService;
import com.visionary.crofting.service.Impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
public class OrdersControllerTest {
    @Mock
    IOrderService orderService;
    @Mock
    SupplierRepository supplierRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetALlOrders() throws Exception {
        //arrange
       // when(orderService.getAll()).thenReturn(Arrays.asList(new Order()));
        //act
        mockMvc.perform(get("/api/orders/")).andExpect(status().isOk());
        //assert

    }

}
