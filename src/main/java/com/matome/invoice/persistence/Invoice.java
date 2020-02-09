package com.matome.invoice.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String client;
    private Long vatRate;
    private Date invoiceDate;

    @OneToMany(mappedBy = "invoice")
    private List<LineItem> lineItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getVatRate() {
        return vatRate;
    }

    public void setVatRate(Long vatRate) {
        this.vatRate = vatRate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Transient
    public BigDecimal getSubTotal() {
        BigDecimal subTotal = new BigDecimal(0);
        if (lineItems != null) {
            for (LineItem listItem : lineItems) {
                if (listItem != null) {
                    subTotal = subTotal.add(listItem.getLineItemTotal());
                }
            }
        }
        return subTotal.setScale(2, RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getVat() {
        BigDecimal vatTotal = new BigDecimal(0);
        if (vatRate != null) {
            double percentage = ((double) vatRate) / 100;
            vatTotal = getSubTotal().multiply(new BigDecimal(percentage));
        }
        return vatTotal.setScale(2, RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getTotal() {
        BigDecimal total = getSubTotal().add(getVat());
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
