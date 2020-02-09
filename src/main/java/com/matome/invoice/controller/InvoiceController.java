package com.matome.invoice.controller;

import com.matome.invoice.persistence.Invoice;
import com.matome.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping()
    public Invoice addInvoice(@RequestBody Invoice invoice) throws Exception {
        return invoiceService.addInvoice(invoice);
    }

    @GetMapping()
    public List<Invoice> viewAllInvoices() {
        return invoiceService.getInvoices();
    }

    @GetMapping("{invoiceId}")
    public Invoice viewInvoice(@PathVariable long invoiceId) {
        return invoiceService.getInvoice(invoiceId);
    }
}
