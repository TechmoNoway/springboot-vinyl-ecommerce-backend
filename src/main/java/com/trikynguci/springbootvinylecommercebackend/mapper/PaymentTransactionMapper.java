package com.trikynguci.springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.trikynguci.springbootvinylecommercebackend.model.PaymentTransaction;

@Mapper
public interface PaymentTransactionMapper {
    void savePaymentTransaction(PaymentTransaction tx);

    PaymentTransaction getById(@Param("id") Long id);

    PaymentTransaction getLatestByOrderId(@Param("orderId") String orderId);

    PaymentTransaction getByProviderTransactionId(@Param("provider") String provider, @Param("providerTxId") String providerTxId);

    PaymentTransaction getByIdempotencyKey(@Param("orderId") String orderId, @Param("idempotencyKey") String idempotencyKey);

    void updatePaymentUrlById(@Param("id") Long id, @Param("paymentUrl") String paymentUrl, @Param("responsePayload") String responsePayload);

    void updateStatusById(@Param("id") Long id, @Param("status") String status, @Param("providerTxId") String providerTxId, @Param("responsePayload") String responsePayload);

    void updateStatusByProviderTxId(@Param("provider") String provider, @Param("providerTxId") String providerTxId, @Param("status") String status, @Param("responsePayload") String responsePayload);
}
