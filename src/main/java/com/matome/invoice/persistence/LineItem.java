package com.matome.invoice.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class LineItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;
    private String description;
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "invoice")
    private Invoice invoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Transient
    public BigDecimal getLineItemTotal() {
        BigDecimal lineItemTotal = new BigDecimal(0);
        if (unitPrice != null && quantity != null) {
            lineItemTotal = lineItemTotal.add(unitPrice).multiply(new BigDecimal(quantity));
        }
        return lineItemTotal.setScale(2, RoundingMode.HALF_UP);
    }
}
