package com.aninfo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long cbu;

    private TransactionType type;

    private Double amount;

    public Transaction(){
    }

    public Transaction(final Long cbu, final Double amount, final TransactionType type) {
        this.cbu = cbu;
        this.amount = amount;
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long cbu) {
        this.id = id;
    }

    public Long getCbu() {
        return this.cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return this.type;
    }

    public void setType(TransactionType transactionType) {
        this.type = transactionType;
    }
}
