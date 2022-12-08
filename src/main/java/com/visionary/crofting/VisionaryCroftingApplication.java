package com.visionary.crofting;

import com.visionary.crofting.config.MvcConfig;
import com.visionary.crofting.entity.Client;
import com.visionary.crofting.entity.Product;
import com.visionary.crofting.entity.RoleEnum;
import com.visionary.crofting.entity.Stock;
import com.visionary.crofting.repository.ClientRepository;
import com.visionary.crofting.repository.ProductRepository;
import com.visionary.crofting.repository.StockRepository;
import com.visionary.crofting.repository.SupplierRepository;
import com.visionary.crofting.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.UUID;

@SpringBootApplication
@EnableSwagger2
public class VisionaryCroftingApplication implements CommandLineRunner {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	StockRepository stockRepository;
	@Autowired
	MvcConfig config;
	@Autowired
	ProductRepository productRepository;
	public static void main(String[] args) {
		SpringApplication.run(VisionaryCroftingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		insertData();
	}
	public void insertData(){
		//let's insert some users to test login
		Client client=new Client();
		client.setPassword(PasswordHasher.hash("passpass"));
		client.setEmail("client@gmail.com");
		client.setPhone("sqd");
		client.setName("sqd");
		client.setUuid(UUID.randomUUID().toString());
		client.setRole(RoleEnum.CLIENT);

		clientRepository.save(client);

		Stock stock=new Stock();
		stock.setEmail("stock@gmail.com");
		stock.setName("stock 1");
		stock.setAddress("that is an adress");
		stock.setPassword("$argon2i$v=19$m=65536,t=22,p=1$MQXQIwg7dke5mG1fujOO3Q$ytaeAO1PVvL/JJBTVMuqpZwFIVEuJbUOnuVtqJt1SPg");
		stock.setPhone("0998");
		stock=stockRepository.save(stock);
		System.out.println("config value is "+config.getName());
		for (int i = 0; i <100; i++) {
			Product p=new Product();
			p.setDescription("some product "+i);
			p.setInitialPrice(i*10);
			p.setTitle("product "+i);
			p.setStock(stock);
			p.setReference(UUID.randomUUID().toString().substring(0,20));
			p.setQuantity(100);
			productRepository.save(p);
		}
	}

}
