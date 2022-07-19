package com.svelteup.app.backend.productorder.models;

import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import com.svelteup.app.backend.modelcontroller.models.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ProductOrder represents a ProductOrder ordered by a user.
 * owningUsername represents the buyer.
 * secondaryOwningUsername represents the seller.
 */
@Entity()
@NoArgsConstructor
@Data() @EqualsAndHashCode(callSuper = false)
public class ProductOrder extends PairedUserNonPrimaryKeyEntity {
    @Id
    @GeneratedValue
    private Long productOrderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,  insertable = true, updatable = false,referencedColumnName = "surrogateId")
    private Product productOrderProduct;
    private int productOrderQuantity;
    private double productOrderCost;
    @Enumerated(value = EnumType.ORDINAL)
    ApplicationNotificationEnums productOrderStatus;

    public ProductOrder(String authenticatedUser, Double productOrderCost, Integer productOrderQuantity, ApplicationNotificationEnums productOrderStatus, Product orderedProduct) {
        super(orderedProduct.getOwningUsername(),authenticatedUser);
        this.productOrderCost = productOrderCost;
        this.productOrderQuantity = productOrderQuantity;
        this.productOrderStatus = productOrderStatus;
        this.productOrderProduct = orderedProduct;
    }
}
