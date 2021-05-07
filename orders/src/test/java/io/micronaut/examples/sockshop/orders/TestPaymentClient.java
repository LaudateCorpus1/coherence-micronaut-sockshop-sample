/*
 * Copyright (c) 2020, 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.micronaut.examples.sockshop.orders;

import io.micronaut.context.annotation.Primary;

import javax.inject.Singleton;

//@Mock
@Singleton
@Primary
public class TestPaymentClient implements PaymentClient {
   public TestPaymentClient() {
   }

   public Payment authorize(PaymentRequest request) {
      return TestDataFactory.payment(request.getCustomer().getId());
   }
}
