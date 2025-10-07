package com.trikynguci.springbootvinylecommercebackend.service;

import com.trikynguci.springbootvinylecommercebackend.mapper.PaymentTransactionMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.OrderMapper;
import com.trikynguci.springbootvinylecommercebackend.model.PaymentTransaction;
import com.trikynguci.springbootvinylecommercebackend.payment.MomoProvider;
import com.trikynguci.springbootvinylecommercebackend.payment.VNPayProvider;
import com.trikynguci.springbootvinylecommercebackend.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    @Test
    void createPayment_idempotency_returnsExisting() {
        PaymentTransactionMapper mapper = Mockito.mock(PaymentTransactionMapper.class);
        OrderMapper orderMapper = Mockito.mock(OrderMapper.class);
        VNPayProvider vn = Mockito.mock(VNPayProvider.class);
        MomoProvider momo = Mockito.mock(MomoProvider.class);

        PaymentServiceImpl svc = new PaymentServiceImpl(mapper, orderMapper, vn, momo);

        PaymentTransaction existing = PaymentTransaction.builder().id(100L).orderId("O1").idempotencyKey("key1").build();
        Mockito.when(mapper.getByIdempotencyKey("O1", "key1")).thenReturn(existing);

        PaymentTransaction out = svc.createPayment("O1", 1000L, "VND", "VNPAY", "key1", "http://return");
        assertNotNull(out);
        assertEquals(100L, out.getId());
        Mockito.verify(mapper, Mockito.never()).savePaymentTransaction(Mockito.any());
    }
}
