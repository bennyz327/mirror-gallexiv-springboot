package com.team.gallexiv.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "subscription_invoice", schema = "gallexiv_v3", catalog = "")
public class SubscriptionInvoice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invoice_id")
    private long invoiceId;
    @Basic
    @Column(name = "tradeNo")
    private String tradeNo;
    @Basic
    @Column(name = "tradeDate")
    private Timestamp tradeDate;
    @Basic
    @Column(name = "totalAmount")
    private BigDecimal totalAmount;
    @Basic
    @Column(name = "trade_status_code")
    private Integer tradeStatusCode;
    @Basic
    @Column(name = "settle_month")
    private String settleMonth;
    @ManyToOne
    @JoinColumn(name = "subscriptionId", referencedColumnName = "subscriptionId", nullable = false)
    private UserSubscription userSubscriptionBySubscriptionId;

    public SubscriptionInvoice() {
    }
    public SubscriptionInvoice(String tradeNo, Timestamp tradeDate, BigDecimal totalAmount, Integer tradeStatusCode, String settleMonth, UserSubscription userSubscriptionBySubscriptionId) {
        this.tradeNo = tradeNo;
        this.tradeDate = tradeDate;
        this.totalAmount = totalAmount;
        this.tradeStatusCode = tradeStatusCode;
        this.settleMonth = settleMonth;
        this.userSubscriptionBySubscriptionId = userSubscriptionBySubscriptionId;
    }
}
