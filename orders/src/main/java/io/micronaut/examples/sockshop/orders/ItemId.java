/*
 * Copyright (c) 2020, 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.micronaut.examples.sockshop.orders;

import java.io.Serializable;

import lombok.Data;

/**
 * Composite JPA key for the {@link Item} class.
 */
@Data
public class ItemId implements Serializable {
    /**
     * The item identifier.
     */
    private String itemId;

    /**
     * The ID of the order this item belongs to.
     */
    private String order;
}
