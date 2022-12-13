package com.visionary.crofting;

import com.visionary.crofting.controller.WebOrderController;
import com.visionary.crofting.repository.SupplierRepository;
import com.visionary.crofting.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WebOrderController.class)
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
