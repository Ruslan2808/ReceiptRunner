package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.exception.PdfIOException;
import by.kantsevich.receiptrunner.exception.PdfNotFoundException;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.ReceiptProduct;
import by.kantsevich.receiptrunner.util.ReceiptInformation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.time.format.DateTimeFormatter;

import java.util.List;

@Component
public class PdfReceiptMapper implements ReceiptMapper<Document> {

    private static final String PDF_TEMPLATE = "src/main/resources/template/receipt_pdf_template.pdf";
    private static final String PDF = "receipt.pdf";
    private static final int FONT_SIZE = 15;
    private static final int INDENT_SIZE = 14;
    private static final Font FONT = FontFactory.getFont(FontFactory.COURIER, FONT_SIZE);
    private static final int WIDTH_PERCENTAGE = 100;
    private static final float SPACING = 10.0f;

    @Override
    public Document map(Receipt receipt) {
        var pdfReceipt = new Document();
        var pdfReader = getPdfReader();
        var pdfWriter = getPdfWriter(pdfReceipt);

        pdfReceipt.open();

        setTemplateToFirstPagePdfReceipt(pdfWriter, pdfReader);
        addContentToPdfReceipt(receipt, pdfReceipt);

        pdfReceipt.close();
        pdfReader.close();
        pdfWriter.close();

        return pdfReceipt;
    }

    private PdfReader getPdfReader() {
        try {
            return new PdfReader(PDF_TEMPLATE);
        } catch (IOException e) {
            throw new PdfIOException(e.getMessage());
        }
    }

    private PdfWriter getPdfWriter(Document pdfReceipt) {
        try {
            return PdfWriter.getInstance(pdfReceipt, new FileOutputStream(PDF));
        } catch (FileNotFoundException e) {
            throw new PdfNotFoundException(e.getMessage());
        } catch (DocumentException e) {
            throw new PdfIOException(e.getMessage());
        }
    }

    private void setTemplateToFirstPagePdfReceipt(PdfWriter pdfWriter, PdfReader pdfReader) {
        var pdfImportedPage = pdfWriter.getImportedPage(pdfReader, 1);
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

        pdfContentByte.addTemplate(pdfImportedPage, 0, 0);
    }

    private void addContentToPdfReceipt(Receipt receipt, Document pdfReceipt) {
        try {
            addIndentToPdfReceipt(pdfReceipt);
            addServiceInfoToPdfReceipt(pdfReceipt);
            addCreationInfoToPdfReceipt(receipt, pdfReceipt);
            addProductsToPdfReceipt(receipt, pdfReceipt);
            addTotalInfoToPdfReceipt(receipt, pdfReceipt);
        } catch (DocumentException e) {
            throw new PdfIOException(e.getMessage());
        }
    }

    private void addIndentToPdfReceipt(Document pdfReceipt) throws DocumentException {
        var indent = new Paragraph();

        indent.add("\n".repeat(INDENT_SIZE));
        pdfReceipt.add(indent);
    }

    private void addServiceInfoToPdfReceipt(Document pdfReceipt) {
        List<String> serviceInfo = List.of(
                ReceiptInformation.TITLE,
                ReceiptInformation.SHOP,
                ReceiptInformation.SHOP_ADDRESS,
                "Tel: " + ReceiptInformation.PHONE
        );

        serviceInfo.stream()
                .map(serviceLine -> new Paragraph(serviceLine, FONT))
                .forEach(serviceParagraph -> {
                    serviceParagraph.setAlignment(Element.ALIGN_CENTER);
                    addParagraphToPdfReceipt(serviceParagraph, pdfReceipt);
                });
    }

    private void addParagraphToPdfReceipt(Paragraph paragraph, Document pdfReceipt) {
        try {
            pdfReceipt.add(paragraph);
        } catch (DocumentException e) {
            throw new PdfIOException(e.getMessage());
        }
    }

    private void addCreationInfoToPdfReceipt(Receipt receipt, Document pdfReceipt) throws DocumentException {
        var creationInfoTable = new PdfPTable(2);
        creationInfoTable.setWidthPercentage(WIDTH_PERCENTAGE);
        creationInfoTable.setWidths(new float[]{70.0f, 30.0f});
        creationInfoTable.setSpacingBefore(SPACING);
        creationInfoTable.setSpacingAfter(SPACING);

        String cashier = String.format("CASHIER: №%d", ReceiptInformation.CASHIER_NUMBER);
        String creationDate = String.format("DATE: %s",
                receipt.getCreationDate().format(DateTimeFormatter.ofPattern(ReceiptInformation.DATE_PATTERN)));
        String creationTime = String.format("TIME: %s",
                receipt.getCreationTime().format(DateTimeFormatter.ofPattern(ReceiptInformation.TIME_PATTERN)));

        var cashierCell = getCellInPdfReceipt(cashier, Element.ALIGN_LEFT);
        var dateCell = getCellInPdfReceipt(creationDate, Element.ALIGN_RIGHT);
        var timeCell = getCellInPdfReceipt(creationTime, Element.ALIGN_RIGHT);
        timeCell.setColspan(2);
        timeCell.setPaddingRight(20.0f);

        creationInfoTable.addCell(cashierCell);
        creationInfoTable.addCell(dateCell);
        creationInfoTable.addCell(timeCell);

        pdfReceipt.add(creationInfoTable);
    }

    private void addProductsToPdfReceipt(Receipt receipt, Document pdfReceipt) throws DocumentException {
        var productsTable = new PdfPTable(4);
        productsTable.setWidthPercentage(WIDTH_PERCENTAGE);
        productsTable.setWidths(new float[]{10.0f, 60.0f, 15.0f, 15.0f});
        productsTable.setSpacingAfter(SPACING);

        List<String> columnsTitle = List.of("QTY", "PRODUCT", "PRICE", "TOTAL");
        columnsTitle.stream()
                .map(columnTitle -> getCellInPdfReceipt(columnTitle,
                        "TOTAL".equals(columnTitle) ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT))
                .forEach(productsTable::addCell);

        for (ReceiptProduct receiptProduct : receipt.getReceiptProducts()) {
            var qty = receiptProduct.getQty().toString();
            var name= receiptProduct.getName();
            var price = String.format("%.2f$", receiptProduct.getPrice());
            var total = String.format("%.2f$", receiptProduct.calculateTotal());

            var qtyCell = getCellInPdfReceipt(qty, Element.ALIGN_LEFT);
            var nameCell = getCellInPdfReceipt(name, Element.ALIGN_LEFT);
            var priceCell = getCellInPdfReceipt(price, Element.ALIGN_LEFT);
            var totalCell = getCellInPdfReceipt(total, Element.ALIGN_RIGHT);

            if (receiptProduct.getIsPromotional() && receiptProduct.getQty() > 5) {
                String discount = String.format("DISC: %.2f%%", ReceiptProduct.DISCOUNT_PROMOTIONAL_PRODUCT);
                var discountParagraph = new Paragraph(discount, FONT);

                nameCell.addElement(discountParagraph);
            }

            productsTable.addCell(qtyCell);
            productsTable.addCell(nameCell);
            productsTable.addCell(priceCell);
            productsTable.addCell(totalCell);
        }

        pdfReceipt.add(productsTable);
    }

    private void addTotalInfoToPdfReceipt(Receipt receipt, Document pdfReceipt) throws DocumentException {
        var totalInfoTable = new PdfPTable(2);
        totalInfoTable.setWidthPercentage(WIDTH_PERCENTAGE);
        totalInfoTable.setWidths(new float[]{70.0f, 30.0f});
        totalInfoTable.setSpacingAfter(SPACING);

        if (receipt.getDiscount() > 0) {
            var totalWithoutDiscount = String.format("%.2f$", receipt.calculateUnDiscountedTotal());
            var discount = String.format("%.2f%%", receipt.getDiscount());

            var titleTotalWithoutDiscountCell = getCellInPdfReceipt("TOTAL WITHOUT DISCOUNT", Element.ALIGN_LEFT);
            var totalWithoutDiscountCell = getCellInPdfReceipt(totalWithoutDiscount, Element.ALIGN_RIGHT);
            var titleDiscountCell = getCellInPdfReceipt("DISCOUNT", Element.ALIGN_LEFT);
            var discountCell = getCellInPdfReceipt(discount, Element.ALIGN_RIGHT);

            totalInfoTable.addCell(titleTotalWithoutDiscountCell);
            totalInfoTable.addCell(totalWithoutDiscountCell);
            totalInfoTable.addCell(titleDiscountCell);
            totalInfoTable.addCell(discountCell);
        }

        var total = String.format("%.2f$", receipt.calculateTotal());

        var titleTotalCell = getCellInPdfReceipt("TOTAL", Element.ALIGN_LEFT);
        var totalCell = getCellInPdfReceipt(total, Element.ALIGN_RIGHT);

        totalInfoTable.addCell(titleTotalCell);
        totalInfoTable.addCell(totalCell);

        pdfReceipt.add(totalInfoTable);
    }

    private PdfPCell getCellInPdfReceipt(String content, int alignment) {
        var cell = new PdfPCell();
        var paragraph = new Paragraph(content, FONT);

        paragraph.setAlignment(alignment);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);

        return cell;
    }
}
