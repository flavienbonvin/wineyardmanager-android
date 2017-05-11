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
        name = "jobApi",
        version = "v1",
        resource = "job",
        namespace = @ApiNamespace(
                ownerDomain = "entities.wineyardmanager.flavien.luca.com",
                ownerName = "entities.wineyardmanager.flavien.luca.com",
                packagePath = ""
        )
)
public class JobEndpoint {

    private static final Logger logger = Logger.getLogger(JobEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Job.class);
    }

    /**
     * Returns the {@link Job} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Job} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "job/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Job get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Job with ID: " + id);
        Job job = ofy().load().type(Job.class).id(id).now();
        if (job == null) {
            throw new NotFoundException("Could not find Job with ID: " + id);
        }
        return job;
    }

    /**
     * Inserts a new {@code Job}.
     */
    @ApiMethod(
            name = "insert",
            path = "job",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Job insert(Job job) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that job.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(job).now();
        logger.info("Created Job with ID: " + job.getId());

        return ofy().load().entity(job).now();
    }

    /**
     * Updates an existing {@code Job}.
     *
     * @param id  the ID of the entity to be updated
     * @param job the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Job}
     */
    @ApiMethod(
            name = "update",
            path = "job/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Job update(@Named("id") long id, Job job) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(job).now();
        logger.info("Updated Job: " + job);
        return ofy().load().entity(job).now();
    }

    /**
     * Deletes the specified {@code Job}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Job}
     */
    @ApiMethod(
            name = "remove",
            path = "job/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Job.class).id(id).now();
        logger.info("Deleted Job with ID: " + id);
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
            path = "job",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Job> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Job> query = ofy().load().type(Job.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Job> queryIterator = query.iterator();
        List<Job> jobList = new ArrayList<Job>(limit);
        while (queryIterator.hasNext()) {
            jobList.add(queryIterator.next());
        }
        return CollectionResponse.<Job>builder().setItems(jobList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Job.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Job with ID: " + id);
        }
    }
}