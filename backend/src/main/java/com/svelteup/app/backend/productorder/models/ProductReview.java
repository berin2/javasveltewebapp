package com.svelteup.app.backend.productorder.models;

import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToPutDto;
import com.svelteup.app.backend.modelcontroller.models.SurrogateEntity;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.productorder.dto.ProductReview.PostProductReviewDto;
import com.svelteup.app.backend.productorder.dto.ProductReview.PutProductReviewDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Data() @NoArgsConstructor @EqualsAndHashCode(callSuper = false)
public class ProductReview extends OwningUserNonPrimaryKeySurrogateEntity implements ToPutDto<PutProductReviewDto> {
    @Id()
    @GeneratedValue()
    Long productReviewId;
    String productReviewTitle;
    String productReviewDescription;
    Date productReviewDate;
    Integer productReviewStars;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "productOrderId")
    ProductOrder owningProductOrder;

    public ProductReview(SvelteUpUser owningUser, PostProductReviewDto productReviewDto, ProductOrder productOrder)
    {
        super(owningUser.getUsername());
        this.productReviewTitle = productReviewDto.productReviewTitle;
        this.productReviewDescription = productReviewDto.productReviewDescription;
        this.productReviewStars = productReviewDto.productReviewStars;
        this.productReviewDate = new Date(System.currentTimeMillis());
        this.owningProductOrder = productOrder;
    }

    @Override
    public void update(PutProductReviewDto updateDto) {
        this.productReviewTitle = updateDto.productReviewTitle;
        this.productReviewDescription = updateDto.productReviewDescription;
        this.productReviewStars = updateDto.productReviewStars;
    }

    @Override
    public PutProductReviewDto toExistingDto() {
        PutProductReviewDto returnDto = new PutProductReviewDto();
        returnDto.owningUser = this.getOwningUsername();
        returnDto.productReviewId = this.surrogateId;
        returnDto.productReviewStars = this.productReviewStars;

        Timestamp dateStamp = new Timestamp(this.productReviewDate.getTime());
        returnDto.productReviewDateTimeStamp  = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateStamp);

        returnDto.productReviewTitle = this.productReviewTitle;
        returnDto.productReviewDescription = this.productReviewDescription;
        returnDto.owningProductOrder = null;
        return returnDto;
    }
}
