package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionInvoiceDao extends JpaRepository<SubscriptionInvoice, Integer> {


}
