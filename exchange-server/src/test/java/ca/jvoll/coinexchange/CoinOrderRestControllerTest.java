package ca.jvoll.coinexchange;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExchangeApplication.class)
@WebAppConfiguration
public class CoinOrderRestControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "testbillybob";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account;

    private List<CoinOrder> orderList = new ArrayList<>();

    @Autowired
    private CoinOrderRepository orderRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.orderRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();

        this.account = accountRepository.save(new Account(userName, "password"));
        this.orderList.add(orderRepository.save(new CoinOrder(this.account, CoinOrder.Type.BUY, 5, 10)));
        this.orderList.add(orderRepository.save(new CoinOrder(this.account, CoinOrder.Type.SELL, 7, 11)));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/george/orders/")
                .content(this.json(new CoinOrder()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleOrder() throws Exception {
        mockMvc.perform(get("/" + userName + "/orders/"
                + this.orderList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.coinOrder.id", is(this.orderList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.coinOrder.type", is(CoinOrder.Type.BUY.toString())))
                .andExpect(jsonPath("$.coinOrder.quantity", is(5)))
                .andExpect(jsonPath("$.coinOrder.priceInCents", is(10)));
    }

    @Test
    public void readOrders() throws Exception {
        mockMvc.perform(get("/" + userName + "/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].coinOrder.id", is(this.orderList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.content[0].coinOrder.type", is(CoinOrder.Type.BUY.toString())))
                .andExpect(jsonPath("$.content[0].coinOrder.quantity", is(5)))
                .andExpect(jsonPath("$.content[0].coinOrder.priceInCents", is(10)))
                .andExpect(jsonPath("$.content[1].coinOrder.id", is(this.orderList.get(1).getId().intValue())))
                .andExpect(jsonPath("$.content[1].coinOrder.type", is(CoinOrder.Type.SELL.toString())))
                .andExpect(jsonPath("$.content[1].coinOrder.quantity", is(7)))
                .andExpect(jsonPath("$.content[1].coinOrder.priceInCents", is(11)));
    }

    @Test
    public void createOrder() throws Exception {
        String orderJson = json(new CoinOrder(
                this.account, CoinOrder.Type.SELL, 15, 22));
        System.out.println(orderJson);

        this.mockMvc.perform(post("/" + userName + "/orders")
                .contentType(contentType)
                .content(orderJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}