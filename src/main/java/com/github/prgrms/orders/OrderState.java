package com.github.prgrms.orders;

public enum OrderState {
    REQUESTED("REQUESTED"),
    ACCEPTED("ACCEPTED"),
    SHIPPING("SHIPPING"),
    COMPLETED("COMPLETED"),
    REJECTED("REJECTED");

    private String state;

    OrderState(String state) {
        this.state = state;
    }

    public String getValue() {
        return state;
    }

}
