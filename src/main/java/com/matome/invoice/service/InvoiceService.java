package com.matome.invoice.service;

import com.matome.invoice.persistence.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice addInvoice(Invoice invoice) throws Exception;

    List<Invoice> getInvoices();

    Invoice getInvoice(long invoiceId);
}
