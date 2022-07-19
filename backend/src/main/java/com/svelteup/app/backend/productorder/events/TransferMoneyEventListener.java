package com.svelteup.app.backend.productorder.events;

import com.svelteup.app.backend.payment.services.SGateWayFinancialProcessing;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component()
@RequiredArgsConstructor()
public class TransferMoneyEventListener implements ApplicationListener<TransferMoneyEvent>
{
    protected final SGateWayFinancialProcessing gateWayFinancialProcessingService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(TransferMoneyEvent event) {
        gateWayFinancialProcessingService.transferMoney(event);
    }
}
