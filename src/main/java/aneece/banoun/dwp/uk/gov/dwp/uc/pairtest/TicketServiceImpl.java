package aneece.banoun.dwp.uk.gov.dwp.uc.pairtest;


import aneece.banoun.dwp.thirdparty.paymentgateway.TicketPaymentService;
import aneece.banoun.dwp.thirdparty.seatbooking.SeatReservationService;
import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type.*;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketPaymentService ticketPaymentService;
    @Autowired
    private SeatReservationService seatReservationService;

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        List<TicketTypeRequest> ticketTypeRequestList = Arrays.stream(ticketTypeRequests).toList();
        Map<TicketTypeRequest.Type, List<TicketTypeRequest>> groupedTickets = groupTickets(ticketTypeRequestList);
        validateAdultTickets(groupedTickets.get(ADULT));
        ticketPaymentService.makePayment(accountId, getTotalAmountToPay(groupedTickets));
        seatReservationService.reserveSeat(accountId, getTotalSeatsToAllocate(groupedTickets));
    }

    private Integer getTotalAmountToPay(Map<TicketTypeRequest.Type, List<TicketTypeRequest>> groupedTickets) {
        Integer adultSeats = seatCounter(groupedTickets.get(ADULT));
        Integer childrenSeats = seatCounter(groupedTickets.get(CHILD));
        return (adultSeats * ADULT.getPrice()) + (childrenSeats * CHILD.getPrice());
    }

    private Integer getTotalSeatsToAllocate(Map<TicketTypeRequest.Type, List<TicketTypeRequest>> groupedTickets) {
        Integer adultSeats = seatCounter(groupedTickets.get(ADULT));
        Integer childrenSeats = seatCounter(groupedTickets.get(CHILD));
        return adultSeats + childrenSeats;
    }


    private Map<TicketTypeRequest.Type, List<TicketTypeRequest>> groupTickets(List<TicketTypeRequest> ticketTypeRequestList) {
        return ticketTypeRequestList.stream()
                .collect(Collectors.groupingBy(TicketTypeRequest::getTicketType,
                        Collectors.toList()));
    }

    private void validateAdultTickets(List<TicketTypeRequest> adults) {
        if (adults == null || seatCounter(adults) < 1) {
            throw new InvalidPurchaseException();
        }
    }

    private int seatCounter(List<TicketTypeRequest> ticketTypeRequests) {
        return ticketTypeRequests.stream().map(TicketTypeRequest::getNoOfTickets).mapToInt(Integer::intValue).sum();
    }


}
