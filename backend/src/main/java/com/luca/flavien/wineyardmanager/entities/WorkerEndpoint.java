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
 * Google Cloud Endpoints REST full API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "workerApi",
        version = "v1",
        resource = "worker",
        namespace = @ApiNamespace(
                ownerDomain = "entities.wineyardmanager.flavien.luca.com",
                ownerName = "entities.wineyardmanager.flavien.luca.com",
                packagePath = ""
        )
)
public class WorkerEndpoint {

    private static final Logger logger = Logger.getLogger(WorkerEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an Of a Service wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Worker.class);
    }

    /**
     * Returns the {@link Worker} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Worker} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "worker/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Worker get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Worker with ID: " + id);
        Worker worker = ofy().load().type(Worker.class).id(id).now();
        if (worker == null) {
            throw new NotFoundException("Could not find Worker with ID: " + id);
        }
        return worker;
    }

    /**
     * Inserts a new {@code Worker}.
     */
    @ApiMethod(
            name = "insert",
            path = "worker",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Worker insert(Worker worker) {
        // Typically in a REST full API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that worker.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(worker).now();
        logger.info("Created Worker with ID: " + worker.getId());

        return ofy().load().entity(worker).now();
    }

    /**
     * Updates an existing {@code Worker}.
     *
     * @param id     the ID of the entity to be updated
     * @param worker the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Worker}
     */
    @ApiMethod(
            name = "update",
            path = "worker/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Worker update(@Named("id") long id, Worker worker) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(worker).now();
        logger.info("Updated Worker: " + worker);
        return ofy().load().entity(worker).now();
    }

    /**
     * Deletes the specified {@code Worker}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Worker}
     */
    @ApiMethod(
            name = "remove",
            path = "worker/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Worker.class).id(id).now();
        logger.info("Deleted Worker with ID: " + id);
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
            path = "worker",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Worker> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Worker> query = ofy().load().type(Worker.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Worker> queryIterator = query.iterator();
        List<Worker> workerList = new ArrayList<Worker>(limit);
        while (queryIterator.hasNext()) {
            workerList.add(queryIterator.next());
        }
        return CollectionResponse.<Worker>builder().setItems(workerList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Worker.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Worker with ID: " + id);
        }
    }
}