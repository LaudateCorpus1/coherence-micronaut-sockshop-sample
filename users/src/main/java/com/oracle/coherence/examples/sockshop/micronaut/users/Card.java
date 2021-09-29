/*
 * Copyright (c) 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */

package com.oracle.coherence.examples.sockshop.micronaut.users;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representation of a credit card.
 */
@Data
@NoArgsConstructor
@Schema(description = "User credit card")
public class Card implements Serializable {
    /**
     * The card identifier.
     */
    @Schema(description = "Card identifier")
    private String cardId;

    /**
     * The card number.
     */
    @Schema(description = "Card number")
    private String longNum;

    /**
     * The expiration date.
     */
    @Schema(description = "Expiration date")
    private String expires;

    /**
     * The security code.
     */
    @Schema(description = "CCV code")
    private String ccv;

    /**
     * The user this card belongs to, purely for JPA optimization.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private User user;

    /**
     * Construct {@code Card} with specified parameters.
     */
    public Card(String longNum, String expires, String ccv) {
        this.longNum = longNum;
        this.expires = expires;
        this.ccv = ccv;
    }

    /**
     * Return the user this address belongs to.
     *
     * @return the user this address belongs to
     */
    User getUser() {
    return user;
    }

    /**
     * Set the uer this address belongs to.
     *
     * @param user the user to set
     *
     * @return this card
     */
    Card setUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * Set the card id.
     */
    Card setCardId(String id) {
        this.cardId = id;
        return this;
    }

    /**
     * Return CardId for this card.
     */
    public CardId getId() {
        return new CardId(user.getUsername(), cardId);
    }

    /**
     * Return the card with masked card number.
     *
     * @return the card with masked card number
     */
    public Card mask() {
        if (longNum != null) {
            int len = longNum.length() - 4;
            longNum = "****************".substring(0, len) + longNum.substring(len);
        }
        return this;
    }

    /**
     * Return the last 4 digit of the card number.
     *
     * @return the last 4 digit of the card number
     */
    public String last4() {
        return longNum == null ? null : longNum.substring(longNum.length() - 4);
    }

    /**
     * Return {@code _links} attribute for this entity.
     *
     * @return {@code _links} attribute for this entity
     */
    @JsonProperty("_links")
    public Links getLinks() {
        return Links.card(getId());
    }
}
