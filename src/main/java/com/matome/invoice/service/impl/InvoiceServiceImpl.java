package com.matome.invoice.service.impl;

import com.matome.invoice.persistence.Invoice;
import com.matome.invoice.persistence.LineItem;
import com.matome.invoice.repository.InvoiceRepository;
import com.matome.invoice.repository.LineItemRepository;
import com.matome.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;

    private LineItemRepository listItemRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, LineItemRepository listItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.listItemRepository = listItemRepository;
    }

    @Override
    public Invoice addInvoice(Invoice invoiceDto) throws Exception {
        if (invoiceDto == null) {
            throw new Exception("No invoice found");
        }

        List<LineItem> listItems = invoiceDto.getLineItems();
        Invoice invoice = invoiceRepository.save(invoiceDto);

        if (listItems != null) {
            for (LineItem listItem : listItems) {
                if (listItem != null) {
                    listItem.setInvoice(invoice);
                    listItemRepository.save(listItem);
                }
            }
        }

        Optional<Invoice> option = invoiceRepository.findById(invoice.getId());
        return option.get();
    }

    @Override
    public List<Invoice> getInvoices() {
        Iterable<Invoice> iterator = invoiceRepository.findAll();
        List<Invoice> invoices = new ArrayList<>();
        iterator.forEach(invoices::add);
        return invoices;
    }

    @Override
    public Invoice getInvoice(long invoiceId) {
        Optional<Invoice> option = invoiceRepository.findById(invoiceId);
        if (option.isPresent()) {
            return option.get();
        } else {
            return null;
        }
    }
}
