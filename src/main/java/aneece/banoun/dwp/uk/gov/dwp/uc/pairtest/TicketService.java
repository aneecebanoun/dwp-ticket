package aneece.banoun.dwp.uk.gov.dwp.uc.pairtest;

import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public interface TicketService {

    void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException;

}
