package ru.hh.school.resource;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.Verification;
import ru.hh.school.dto.EmployerAddFavoritesDto;
import ru.hh.school.dto.EmployerGetFavoritesDto;
import ru.hh.school.dto.EmployerUpdateFavoritesDto;
import ru.hh.school.service.EmployerFavoritesService;

import javax.inject.Singleton;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static ru.hh.school.Params.*;

@Path("/favorites/employer")
@RequiredArgsConstructor
@Singleton
public class EmployerFavoritesResource {
    private static final Logger logger = LoggerFactory.getLogger(EmployerFavoritesResource.class);
    private final EmployerFavoritesService employerFavoritesService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployerToFavorites(EmployerAddFavoritesDto employer) {
        logger.info("Invoked POST/favorites/employer with params: employer.id = {};", employer.getEmployerId());

        if (employer.getEmployerId() <= 0)
            throw new IllegalArgumentException("The employer.id is less or equal 0: " + employer.getEmployerId());

        long result = employerFavoritesService.addEmployer(employer);
        //return Response.status(Response.Status.CREATED).entity(json).build();
        return Response.ok("Employer was added, id = " + result).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployersFromFavorites(@QueryParam("page") @DefaultValue(DEFAULT_PAGE) @Min(MINIMUM_PAGE) int page,
                                              @QueryParam("per_page") @DefaultValue(DEFAULT_PER_PAGE) @Min(MINIMUM_PER_PAGE) @Max(MAXIMUM_PER_PAGE) int perPage) {
        logger.info("Invoked Get/favorites/employer with params: page = {}; per_page = {}", page, perPage);

        Verification.checkPagingParams(perPage, page);
        List<EmployerGetFavoritesDto> employerDto = employerFavoritesService.getEmployersFromFavorites(page, perPage);
        return Response.ok(employerDto).build();
    }

    @PUT()
    @Path("/{employer_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployersFromFavorites(@PathParam("employer_id") long employer_id, EmployerUpdateFavoritesDto employerUpdateFavoritesDto) {
        logger.info("Invoked PUT/favorites/employer/{}, comment:{}", employer_id, employerUpdateFavoritesDto.getComment());

        if (employer_id <= 0)
            throw new IllegalArgumentException("The employer.id is less or equal 0: " + employer_id);

        employerFavoritesService.updateEmployersFromFavorites(employer_id, employerUpdateFavoritesDto);
        return Response.ok("Employer was updated, id = " + employer_id).build();
    }

    @DELETE()
    @Path("/{employer_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployersFromFavorites(@PathParam("employer_id") long employer_id) {
        logger.info("Invoked DELETE/favorites/employer/{}", employer_id);

        if (employer_id <= 0)
            throw new IllegalArgumentException("The employer.id is less or equal 0: " + employer_id);

        employerFavoritesService.deleteEmployersFromFavorites(employer_id);
        return Response.ok("Employer was deleted, id = " + employer_id).build();
    }

    @POST
    @Path("/{employer_id}/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshEmployerToFavorites(@PathParam("employer_id") long employer_id) {
        logger.info("Invoked POST/favorites/employer/refresh/ with params: employer.id = {};", employer_id);

        if (employer_id <= 0)
            throw new IllegalArgumentException("The employer.id is less or equal 0: " + employer_id);

        employerFavoritesService.refreshEmployerToFavorites(employer_id);
        return Response.ok("Employer was refreshed, id = " + employer_id).build();
    }


}
