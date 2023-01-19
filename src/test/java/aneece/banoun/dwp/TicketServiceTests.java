package aneece.banoun.dwp;

import static aneece.banoun.dwp.TicketServiceTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.TicketService;
import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import com.jayway.jsonpath.internal.Utils;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TicketServiceTests {

    @Autowired
    private TicketService ticketService;

    @Test
    void validBooking() {
        ticketService = spy(ticketService);
        ticketService.purchaseTickets(VALID_BOOKING_ID, ticketServiceTestData.get(VALID_BOOKING_ID).toArray(new TicketTypeRequest[]{}));
        verify(ticketService, times(1)).purchaseTickets(VALID_BOOKING_ID, ticketServiceTestData.get(VALID_BOOKING_ID).toArray(new TicketTypeRequest[]{}));
    }

    @Test
    void invalidBookingNoAdults() {
        ticketService = spy(ticketService);
        TicketTypeRequest[] invalidDataWithNoAdults = ticketServiceTestData.get(INVALID_NO_ADULT_BOOKING_ID).toArray(new TicketTypeRequest[]{});
        assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(INVALID_NO_ADULT_BOOKING_ID, invalidDataWithNoAdults));
    }

    @Test
    void invalidBookingNoAdultsSeats() {
        ticketService = spy(ticketService);
        TicketTypeRequest[] invalidDataWithNoAdults = ticketServiceTestData.get(INVALID_ZERO_ADULT_SEAT_BOOKING_ID).toArray(new TicketTypeRequest[]{});
        assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(INVALID_ZERO_ADULT_SEAT_BOOKING_ID, invalidDataWithNoAdults));
    }


    @Test
    void invalidBookingTooManyTickets() {
        ticketService = spy(ticketService);
        TicketTypeRequest[] invalidDataWithTooManyTickets = ticketServiceTestData.get(INVALID_TOO_MANY_TICKETS_ID).toArray(new TicketTypeRequest[]{});
        assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(INVALID_TOO_MANY_TICKETS_ID, invalidDataWithTooManyTickets));
    }

    //As per requirement no other public methods allowed, so the only way to test private method is through the reflection
    //Ideally testing only for public methods and grouped by business logic as above tests
    @Test
    void testCalculation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ticketService = spy(ticketService);
        Method groupTicketsMethod = TicketServiceImpl.class.getDeclaredMethod("groupTickets", List.class);
        groupTicketsMethod.setAccessible(true);
        List<TicketTypeRequest> validData = ticketServiceTestData.get(VALID_BOOKING_ID);
        Map<TicketTypeRequest.Type, List<TicketTypeRequest>> groupTickets = (Map<TicketTypeRequest.Type, List<TicketTypeRequest>>) groupTicketsMethod.invoke(ticketService, validData);
        Method getTotalAmountToPayMethod = TicketServiceImpl.class.getDeclaredMethod("getTotalAmountToPay", Map.class);
        getTotalAmountToPayMethod.setAccessible(true);
        getTotalAmountToPayMethod.invoke(ticketService, groupTickets);
        Integer totalAmountToPay = (Integer) getTotalAmountToPayMethod.invoke(ticketService, groupTickets);
        Method getTotalSeatsToAllocateMethod = TicketServiceImpl.class.getDeclaredMethod("getTotalSeatsToAllocate", Map.class);
        getTotalSeatsToAllocateMethod.setAccessible(true);
        Integer totalSeatsToAllocate = (Integer) getTotalSeatsToAllocateMethod.invoke(ticketService, groupTickets);
        assertEquals(8, totalSeatsToAllocate);
        assertEquals(130, totalAmountToPay);
    }

}
