package org.javamagazine.sportsteamregistrationservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.javamagazine.sportsteamregistrationservice.entity.TeamRoster;

/**
 *
 * @author juneau
 */
@javax.ejb.Stateless
@Path("teamrosterregistrationservice")
public class TeamRosterFacadeREST {

    @PersistenceContext(unitName = "SportsTeamRegistrationServicePU")
    private EntityManager em;

    public TeamRosterFacadeREST() {
    }

    //@JWTTokenNeeded
    @POST
    @Path("addPlayer")
    @Produces(MediaType.APPLICATION_XML)
    public Response addPlayer(@FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("position") String position) {

        String p_result = null;
        boolean valid = true;
        if (firstName == null) {
            valid = false;
        } else if (lastName == null) {
            valid = false;
        } else if (position == null) {
            valid = false;
        }

        if (valid) {
            Long rosterCount = countRoster();
            TeamRoster teamMember = new TeamRoster();
            teamMember.setFirstName(firstName.toUpperCase());
            teamMember.setLastName(lastName.toUpperCase());
            teamMember.setPosition(position.toUpperCase());
            teamMember.setRegistrationDate(new Date());
            teamMember.setId(BigDecimal.valueOf(rosterCount + 1));
            em.persist(teamMember);
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }

    }
    
    @GET
    public Long countRoster(){
        return (Long) em.createQuery("select count(o) from TeamRoster o")
                .getSingleResult();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
