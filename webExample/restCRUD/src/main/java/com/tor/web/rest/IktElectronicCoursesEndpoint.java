package com.tor.web.rest;

import com.tor.web.model.IktElectronicCourses;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

/**
 *
 */
@Stateless
@Path("/iktcourses")
public class IktElectronicCoursesEndpoint {
    @javax.persistence.PersistenceContext(unitName= "rest_CRUD_PU")
    private EntityManager em;

   /* @PostConstruct
    void postConstruct() {
        if (em == null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("rest-CRUD-PU");
            em = entityManagerFactory.createEntityManager();
        }
    }*/

    @POST
    @Consumes("application/json")
    public Response create(IktElectronicCourses entity) {
        em.persist(entity);
        return Response.created(
                UriBuilder.fromResource(IktElectronicCoursesEndpoint.class)
                        .path(String.valueOf(entity.getId())).build()).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Integer id) {
        IktElectronicCourses entity = em.find(IktElectronicCourses.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") Integer id) {
        TypedQuery<IktElectronicCourses> findByIdQuery = em
                .createQuery(
                        "SELECT DISTINCT i FROM IktElectronicCourses i WHERE i.id = :entityId ORDER BY i.id",
                        IktElectronicCourses.class);
        findByIdQuery.setParameter("entityId", id);
        IktElectronicCourses entity;
        try {
            entity = findByIdQuery.getSingleResult();
        } catch (NoResultException nre) {
            entity = null;
        }
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public List<IktElectronicCourses> listAll(
            @QueryParam("start") Integer startPosition,
            @QueryParam("max") Integer maxResult) {
        System.out.println("em = " + em);
        TypedQuery<IktElectronicCourses> findAllQuery = em.createQuery(
                "SELECT DISTINCT i FROM IktElectronicCourses i ORDER BY i.id",
                IktElectronicCourses.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        final List<IktElectronicCourses> results = findAllQuery.getResultList();
        return results;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(@PathParam("id") Integer id,
                           IktElectronicCourses entity) {
        if (entity == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (id == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (!id.equals(entity.getId())) {
            return Response.status(Status.CONFLICT).entity(entity).build();
        }
        if (em.find(IktElectronicCourses.class, id) == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        try {
            entity = em.merge(entity);
        } catch (OptimisticLockException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getEntity()).build();
        }

        return Response.noContent().build();
    }
}
