package com.luca.flavien.wineyardmanager.entities;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "wineVarietyApi",
        version = "v1",
        resource = "wineVariety",
        namespace = @ApiNamespace(
                ownerDomain = "entities.wineyardmanager.flavien.luca.com",
                ownerName = "entities.wineyardmanager.flavien.luca.com",
                packagePath = ""
        )
)
public class WineVarietyEndpoint {

    private static final Logger logger = Logger.getLogger(WineVarietyEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(WineVariety.class);
    }

    /**
     * Returns the {@link WineVariety} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code WineVariety} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "wineVariety/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public WineVariety get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting WineVariety with ID: " + id);
        WineVariety wineVariety = ofy().load().type(WineVariety.class).id(id).now();
        if (wineVariety == null) {
            throw new NotFoundException("Could not find WineVariety with ID: " + id);
        }
        return wineVariety;
    }

    /**
     * Inserts a new {@code WineVariety}.
     */
    @ApiMethod(
            name = "insert",
            path = "wineVariety",
            httpMethod = ApiMethod.HttpMethod.POST)
    public WineVariety insert(WineVariety wineVariety) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that wineVariety.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(wineVariety).now();
        logger.info("Created WineVariety with ID: " + wineVariety.getId());

        return ofy().load().entity(wineVariety).now();
    }

    /**
     * Updates an existing {@code WineVariety}.
     *
     * @param id          the ID of the entity to be updated
     * @param wineVariety the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code WineVariety}
     */
    @ApiMethod(
            name = "update",
            path = "wineVariety/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public WineVariety update(@Named("id") long id, WineVariety wineVariety) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(wineVariety).now();
        logger.info("Updated WineVariety: " + wineVariety);
        return ofy().load().entity(wineVariety).now();
    }

    /**
     * Deletes the specified {@code WineVariety}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code WineVariety}
     */
    @ApiMethod(
            name = "remove",
            path = "wineVariety/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(WineVariety.class).id(id).now();
        logger.info("Deleted WineVariety with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "wineVariety",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<WineVariety> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<WineVariety> query = ofy().load().type(WineVariety.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<WineVariety> queryIterator = query.iterator();
        List<WineVariety> wineVarietyList = new ArrayList<WineVariety>(limit);
        while (queryIterator.hasNext()) {
            wineVarietyList.add(queryIterator.next());
        }
        return CollectionResponse.<WineVariety>builder().setItems(wineVarietyList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(WineVariety.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find WineVariety with ID: " + id);
        }
    }
}