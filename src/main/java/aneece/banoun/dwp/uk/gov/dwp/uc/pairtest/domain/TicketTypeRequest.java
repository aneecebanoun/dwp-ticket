package aneece.banoun.dwp.uk.gov.dwp.uc.pairtest.domain;

public class TicketTypeRequest {

    private final int noOfTickets;
    private final Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {

        ADULT(20), CHILD(10), INFANT(0);
        private final Integer price;

        Type(Integer price) {
            this.price = price;
        }

        public Integer getPrice() {
            return price;
        }
    }

}

