package com.svelteup.app.backend.aws.s3.services.interfaces;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;


/**
 * Interface used by AWS services to expose a standard crud interface.
 * @param <AwsGetEvent> The event assigned to  get operations.
 * @param <AwsPutPostEvent> The event assigned to PutPost operations/
 * @param <AwsDeleteEvent>  The event assignmed to delete operations.
 * @param <Entity>  The returned data type of the service get operation.
 */
public interface AwsService<AwsGetEvent,AwsPutPostEvent,AwsDeleteEvent,Entity> {
    List<Entity> get(AwsGetEvent event) throws Http400Exception, Http403Exception, Http500Exception, NotSupportedException;
    void put(AwsPutPostEvent event) throws Http400Exception, Http403Exception, Http500Exception, NotSupportedException, InterruptedException, IOException;;
    void post(AwsPutPostEvent event) throws Http400Exception, Http403Exception, Http500Exception, NotSupportedException, InterruptedException, IOException;;
    void delete(AwsDeleteEvent even) throws Http400Exception, Http403Exception, Http500Exception, NotSupportedException;;
}
