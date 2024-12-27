package com.payment.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.entity.Order;
import com.payment.repo.OrderRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.annotation.PostConstruct;

@Service
public class OrderService {
	@Autowired
	private OrderRepo ordersRepository;
	
	private String razorpayId="rzp_test_TsntkpEqFUD2lH";
	private String razorpaySecret="97VXsPxHY5Xf5MMotSb5hrKQ";
	
	private RazorpayClient razorpayCLient;
	
	@PostConstruct
	public void init() throws RazorpayException {
		this.razorpayCLient = new RazorpayClient(razorpayId, razorpaySecret);
	}
	
	public Order createOrder(Order order) throws RazorpayException {
		
        JSONObject options = new JSONObject();
        options.put("amount", order.getAmount() * 100);
        options.put("currency", "INR");
        options.put("receipt", order.getEmail());
        com.razorpay.Order razorpayOrder = razorpayCLient.orders.create(options);
        if(razorpayOrder != null) {
        order.setRazorpayOrderId(razorpayOrder.get("id"));
        order.setOrderStatus(razorpayOrder.get("status"));
        }
        
        return ordersRepository.save(order);
    }
	
	public String generateUsername(String name) {
        if (name == null || name.length() < 4) {
            throw new IllegalArgumentException("Name must be at least 4 characters long");
        }
        
        String firstFourLetters = name.substring(0, 4);
        
        
        String username = "METI" + firstFourLetters;
        
        return username;
    }

	public Order updateStatus(Map<String, String> map) {
    	String razorpayId = map.get("razorpay_order_id");
    	Order order = ordersRepository.findByRazorpayOrderId(razorpayId);
    	order.setOrderStatus("PAYMENT DONE");
    	Order orders = ordersRepository.save(order);
    	return orders;
    }
	
}
