package by.kantsevich.receiptrunner.model.product;

public class ReceiptProduct {

    public final static double DISCOUNT_PROMOTIONAL_PRODUCT = 10.0;

    private Integer qty;
    private String name;
    private Double price;
    private Boolean isPromotional;

    public ReceiptProduct() {}

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPromotional() {
        return isPromotional;
    }

    public void setPromotional(Boolean promotional) {
        isPromotional = promotional;
    }

    public Double calculateTotal() {
        if (isPromotional && qty > 5) {
            return price * qty * (1 - DISCOUNT_PROMOTIONAL_PRODUCT / 100);
        }

        return price * qty;
    }

}
