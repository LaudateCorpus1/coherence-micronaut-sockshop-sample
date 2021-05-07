/*
 * Copyright (c) 2020, 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.micronaut.examples.sockshop.carts;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.tracing.annotation.NewSpan;

import javax.inject.Inject;

import java.util.concurrent.CompletionStage;


/**
 * Implementation of the Cart Service REST API.
 */
@Controller("/carts-async")
public class CartResourceAsync implements CartApiAsync {

    @Inject
    private CartRepositoryAsync carts;

    @Override
    @NewSpan
    public CompletionStage<Cart> getCart(String customerId) {
        return carts.getOrCreateCart(customerId);
    }

    @Override
    @NewSpan
    public CompletionStage<HttpResponse> deleteCart(String customerId) {
        return carts.deleteCart(customerId)
                .thenApply(fDeleted ->
                        fDeleted
                         ? HttpResponse.accepted()
                         : HttpResponse.notFound());
    }

    @Override
    @NewSpan
    public CompletionStage<HttpResponse> mergeCarts(String customerId, String sessionId) {
        return carts.mergeCarts(customerId, sessionId)
                .thenApply(fMerged ->
                       fMerged
                        ? HttpResponse.accepted()
                        : HttpResponse.notFound());
    }
}
