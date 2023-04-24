package io.quarkiverse.lucene.it;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/lucene")
@ApplicationScoped
public class LuceneResource {
    private final LuceneBackend backend = new LuceneBackend();
    private final Map<String, Directory> directories = new ConcurrentHashMap<>();

    @POST
    @Path("/index/{name}/")
    public Response createIndex(@PathParam("name") String name, @QueryParam("class") String directoryClass)
            throws ClassNotFoundException, IOException {
        Class<?> clazz = Class.forName(directoryClass);
        directories.put(name, backend.createDirectory(clazz, name));
        return Response.ok().build();
    }

    @POST
    @Path("/index/{name}/forceMerge")
    public Response forceMerge(@PathParam("name") String name)
            throws IOException {
        Directory directory = directories.get(name);
        if (directory == null) {
            return Response.noContent().build();
        }
        backend.forceMerge(directory);
        return Response.ok().build();
    }

    @GET
    @Path("/index/{name}/files")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listIndex(@PathParam("name") String name) throws IOException {
        Directory directory = directories.get(name);
        if (directory == null) {
            return Response.noContent().build();
        }
        return Response.ok().entity(directory.listAll()).build();
    }

    @DELETE
    @Path("/indexes")
    public Response deleteAllIndexes() throws IOException {
        for (Directory d : directories.values()) {
            backend.destroyDirectory(d);
        }
        return Response.ok().build();
    }

    @POST
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response indexPerson(@QueryParam("index") String indexName, Person data)
            throws IOException {
        Directory directory = directories.get(indexName);
        if (directory == null) {
            return Response.noContent().build();
        }
        backend.indexPerson(data, directory);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public Response query(@QueryParam("q") String queryString, @QueryParam("index") String indexName)
            throws IOException, ParseException {
        Directory directory = directories.get(indexName);
        if (directory == null) {
            return Response.noContent().build();
        }

        List<String> results = backend.search(directory, queryString);
        return Response.ok().entity(results).build();
    }
}
