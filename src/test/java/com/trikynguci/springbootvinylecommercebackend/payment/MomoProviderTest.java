package com.trikynguci.springbootvinylecommercebackend.payment;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MomoProviderTest {

    @Test
    void verifyCallback_missingSignature_returnsFalse() {
        MomoProvider p = new MomoProvider();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", "ORD-1");
        assertFalse(p.verifyCallback(params));
    }
}
