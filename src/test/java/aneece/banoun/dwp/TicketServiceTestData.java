package aneece.banoun.dwp;

import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type.*;

public class TicketServiceTestData {

    private TicketServiceTestData() {
    }

    public static final Long VALID_BOOKING_ID = 1L;
    public static final Long INVALID_ZERO_ADULT_SEAT_BOOKING_ID = 2L;
    public static final Long INVALID_NO_ADULT_BOOKING_ID = 3L;
    public static Map<Long, List<TicketTypeRequest>> ticketServiceTestData;

    static {
        ticketServiceTestData = new HashMap<>();
        List<TicketTypeRequest> validBooking = new ArrayList<>();
        validBooking.add(new TicketTypeRequest(ADULT, 2));
        validBooking.add(new TicketTypeRequest(INFANT, 1));
        validBooking.add(new TicketTypeRequest(CHILD, 3));
        validBooking.add(new TicketTypeRequest(ADULT, 3));
        ticketServiceTestData.put(VALID_BOOKING_ID, validBooking);


        List<TicketTypeRequest> invalidBooking1 = new ArrayList<>();
        invalidBooking1.add(new TicketTypeRequest(ADULT, 0));
        invalidBooking1.add(new TicketTypeRequest(INFANT, 1));
        invalidBooking1.add(new TicketTypeRequest(CHILD, 3));
        ticketServiceTestData.put(INVALID_ZERO_ADULT_SEAT_BOOKING_ID, invalidBooking1);

        List<TicketTypeRequest> invalidBooking2 = new ArrayList<>();
        invalidBooking2.add(new TicketTypeRequest(INFANT, 1));
        invalidBooking2.add(new TicketTypeRequest(CHILD, 3));
        ticketServiceTestData.put(INVALID_NO_ADULT_BOOKING_ID, invalidBooking2);


    }

}
