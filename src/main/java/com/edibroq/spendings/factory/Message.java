package com.edibroq.spendings.factory;

/**
 * Список допутимых значений
 *             --> AMOUNT
 *           /
 * START -->       <----------------------------------------------------------
 *    |     \     |                                                          |
 *    |      --> ADD ----> PRODUCT---> ADD_PRODUCT ---> PRICE --> ADD_PRICE -
 *    |           |
 *    --- BACK <--
 *
 */

public enum Message {

    START("/start"),

    ADD("add"),

    AMOUNT("amount"),

    BACK("back"),

    PRODUCT("product"),

    PRICE("price"),

    INVALID("invalid"),

    ADD_PRICE("add_price"),

    ADD_PRODUCT("add_product"),

    LIST("spendings");

    private final String text;

    Message(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
