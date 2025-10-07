package com.trikynguci.springbootvinylecommercebackend.payment;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VNPayProviderTest {

    @Test
    void verifyCallback_shouldReturnFalseForMissingHash() {
        VNPayProvider p = new VNPayProvider();
        Map<String, String> params = new HashMap<>();
        params.put("vnp_TxnRef", "ORDER-1");
        assertFalse(p.verifyCallback(params));
    }
}
