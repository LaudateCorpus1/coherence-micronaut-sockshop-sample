/*
 * Copyright (c) 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.micronaut.examples.sockshop.catalog;

import edu.umd.cs.findbugs.annotations.Nullable;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;

import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.tracing.annotation.NewSpan;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Implementation of the Catalog Service {@code /catalogue} API.
 */
@Controller("/catalogue")
public class CatalogResource implements CatalogApi {

    @Inject
    private CatalogRepository catalog;

    @Override
    @NewSpan
    public Collection<? extends Sock> getSocks(@Nullable String tags, String order, int pageNum, int pageSize) {
        return catalog.getSocks(tags, order, pageNum, pageSize);
    }

    @Override
    @NewSpan
    public Count getSockCount(String tags) {
        return new Count(catalog.getSockCount(tags));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "if socks are found"),
            @ApiResponse(responseCode = "404", description = "if socks do not exist")
    })
    @Override
    @NewSpan
    public HttpResponse<Sock> getSock(String sockId) {
        Sock sock = catalog.getSock(sockId);
        return sock == null
                ? HttpResponse.status(HttpStatus.NOT_FOUND)
                : HttpResponse.ok(sock);
    }

    @Override
    @NewSpan
    public HttpResponse<StreamedFile> getImage(String image) {
        InputStream img = getClass().getClassLoader().getResourceAsStream("web/images/" + image);
        return img == null
                ? HttpResponse.status(HttpStatus.NOT_FOUND)
                : HttpResponse.ok(new StreamedFile(img, MediaType.IMAGE_JPEG_TYPE));
    }

    public static class Count {
        public long size;
        public Object err;

        public Count(long size) {
            this.size = size;
        }
    }
}
