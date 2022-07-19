package com.svelteup.app.backend.payment.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import org.springframework.stereotype.Repository;

/**
 * This JPA repository is used to fetch CustomerPayment Info from the database.
 */
@Repository()
public interface RCustomerPaymentInfo extends RSurrogateJpaRepository<CustomerPaymentInfo,String> {
}
