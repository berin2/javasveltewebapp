package com.svelteup.app.backend.modelcontroller.models;

import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.dto.product.PutProductDto;
import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToPutDto;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import lombok.Data;

import javax.persistence.*;

@Entity(name="product")
@Table(name="product")
@Data
public class Product extends OwningUserNonPrimaryKeySurrogateEntity implements ToPutDto<PutProductDto> {
    @Transient
    public static final Integer MAXIMUM_IMAGES = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    private String productName;
    private String productDescription;
    private Integer productCaloriesCount;
    private Boolean productAcceptsReturns;
    private Double productCost;


    @Override
    public String toString()
    {
        return "Not impl yet.";
    }
    public  Product()
    {
        super();
    }
    public Product(String principal, PostProductDto dto)
    {
        super(principal);
        this.productName = dto.productName;
        this.productDescription = dto.productDescription;
        this.productCaloriesCount = dto.productCalories;
        this.productAcceptsReturns = dto.productAcceptsReturns;
        this.productCost = dto.productCost;
    }

    @Override
    public void update(PutProductDto updateDto) {
        this.productName = updateDto.productName;
        this.productDescription = updateDto.productDescription;;
        this.productAcceptsReturns = updateDto.productAcceptsReturns;
        this.productCaloriesCount = updateDto.productCalories;
    }

    @Override
    public PutProductDto toExistingDto() {
        return new PutProductDto(this,null);
    }
}
