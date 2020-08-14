package rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface ApiBase<T> {

    @GET
    Response getAll();

    @Path("/{id}")
    @GET
    Response get(@PathParam("id") Long id);

    @PUT
    Response create(T elem);

    @PATCH
    @Path("/{id}")
    Response update(@PathParam("id") Long id, T elem);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Long id);
}