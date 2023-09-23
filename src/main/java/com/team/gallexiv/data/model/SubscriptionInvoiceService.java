package com.team.gallexiv.data.model;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SubscriptionInvoiceService {

    @Autowired
    private SubscriptionInvoiceDao subscriptionInvoiceD;

    public void insertSubscriptionInvoice(SubscriptionInvoice payRequest) {
        subscriptionInvoiceD.save(payRequest);
    }
}
