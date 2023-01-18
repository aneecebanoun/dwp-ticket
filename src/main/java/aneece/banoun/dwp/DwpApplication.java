package aneece.banoun.dwp;

import aneece.banoun.dwp.thirdparty.paymentgateway.TicketPaymentService;
import aneece.banoun.dwp.thirdparty.paymentgateway.TicketPaymentServiceImpl;
import aneece.banoun.dwp.thirdparty.seatbooking.SeatReservationService;
import aneece.banoun.dwp.thirdparty.seatbooking.SeatReservationServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DwpApplication {

	public static void main(String[] args) {

		SpringApplication.run(DwpApplication.class, args);
	}

	@Bean
	public TicketPaymentService ticketPaymentService(){
		return new TicketPaymentServiceImpl();
	}

	@Bean
	public SeatReservationService seatReservationService(){
		return new SeatReservationServiceImpl();
	}
}
