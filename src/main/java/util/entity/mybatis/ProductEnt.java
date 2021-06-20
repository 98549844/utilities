package util.entity.mybatis;

import java.io.Serializable;

public class ProductEnt implements Serializable {
    private Integer id;

    private String category;

    private String item;

    private Double price;

    private String itemsn;

    private Integer qty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getItemsn() {
        return itemsn;
    }

    public void setItemsn(String itemsn) {
        this.itemsn = itemsn;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}