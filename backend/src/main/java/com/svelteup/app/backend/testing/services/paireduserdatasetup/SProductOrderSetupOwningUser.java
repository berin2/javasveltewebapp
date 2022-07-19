package com.svelteup.app.backend.testing.services.paireduserdatasetup;

import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.notificationmessage.events.NotificationEvent;
import com.svelteup.app.backend.notificationmessage.services.notifications.SOrderStatusNotification;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.productorder.repositories.RProductOrder;
import com.svelteup.app.backend.testing.dto.PairedOwningUserDataSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SProductOrderSetupOwningUser extends APairedOwningUserDataSetup {
    @Autowired RProductOrder rProductOrder;
    @Autowired SOrderStatusNotification sOrderStatusNotification;

    @Override
    public void setupEntity(PairedOwningUserDataSetupDto dto) throws NotSupportedException, IOException, InterruptedException {

        for(Product productToOrder: dto.chosenSecondaryOwningUser.productList)
        {
            ProductOrder productOrder = new ProductOrder(dto.chosenSecondaryOwningUser.owningUser.getUsername(), productToOrder.getProductCost(), dto.getRandomIntegerWithinRange(), ApplicationNotificationEnums.randomEnumValue(),productToOrder);
            NotificationEvent event = new NotificationEvent(productOrder,dto.chosenSecondaryOwningUser.owningUser.getUsername(),productOrder.getProductOrderProduct().getProductName(), dto.chosenOwningUser.owningUser.getUsername(), ApplicationNotificationEnums.ORDER_PLACED);
            sOrderStatusNotification.postNotificationEvent(event);
            rProductOrder.save(productOrder);
        }
    }
}