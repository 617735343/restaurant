package com.imooc.imooc_restaurant.ui.vo;

import com.imooc.imooc_restaurant.bean.Product;

public class ProductItem extends Product {

    public int count;

    public ProductItem(Product product){
        this.id=product.getId();
        this.name=product.getName();
        this.label=product.getLabel();
        this.description=product.getDescription();
        this.icon=product.getIcon();
        this.price=product.getPrice();
        this.restaurant=product.getRestaurant();

    }
}
