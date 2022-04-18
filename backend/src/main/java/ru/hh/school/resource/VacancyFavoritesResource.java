package ru.hh.school.resource;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.Verification;
import ru.hh.school.dto.VacancyAddFavoritesDto;
import ru.hh.school.dto.VacancyGetFavoritesDto;
import ru.hh.school.service.VacancyFavoritesService;

import javax.inject.Singleton;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static ru.hh.school.Params.*;

@Path("/favorites/vacancy")
@RequiredArgsConstructor
@Singleton
public class VacancyFavoritesResource {
    private static final Logger logger = LoggerFactory.getLogger(VacancyFavoritesResource.class);
    private final VacancyFavoritesService vacancyFavoritesService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVacancyToFavorites(VacancyAddFavoritesDto vacancy) {
        logger.info("Invoked POST/favorites/vacancy with params: vacancy.id = {};", vacancy.getVacancyId());

        if (vacancy.getVacancyId() <= 0)
            throw new IllegalArgumentException("The vacancy.id is less or equal 0: " + vacancy.getVacancyId());

        long result = vacancyFavoritesService.addVacancy(vacancy);

        return Response.ok("Vacancy was added, id = " + result).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVacanciesFromFavorites(@QueryParam("page") @DefaultValue(DEFAULT_PAGE) @Min(MINIMUM_PAGE) int page,
                                              @QueryParam("per_page") @DefaultValue(DEFAULT_PER_PAGE) @Min(MINIMUM_PER_PAGE) @Max(MAXIMUM_PER_PAGE) int perPage) {
        logger.info("Invoked POST/favorites/vacancy with params: page = {}; per_page = {}", page, perPage);

        Verification.checkPagingParams(perPage, page);
        List<VacancyGetFavoritesDto> vacancyDto = vacancyFavoritesService.getVacanciesFromFavorites(page, perPage);

        return Response.ok(vacancyDto).build();
    }

    @DELETE()
    @Path("/{vacancy_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVacancyFromFavorites(@PathParam("vacancy_id") long vacancy_id) {
        logger.info("Invoked DELETE/favorites/vacancy/{}", vacancy_id);

        if (vacancy_id <= 0)
            throw new IllegalArgumentException("The vacancy.id is less or equal 0: " + vacancy_id);

        vacancyFavoritesService.deleteVacancyFromFavorites(vacancy_id);
        return Response.ok("Vacancy was deleted, id = " + vacancy_id).build();
    }

    @POST
    @Path("/{vacancy_id}/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshVacancyToFavorites(@PathParam("vacancy_id") long vacancy_id) {
        logger.info("Invoked POST/favorites/vacancy/refresh/ with params: vacancy_id = {};", vacancy_id);

        if (vacancy_id <= 0)
            throw new IllegalArgumentException("The vacancy.id is less or equal 0: " + vacancy_id);

        vacancyFavoritesService.refreshVacancyToFavorites(vacancy_id);
        return Response.ok("Vacancy was refreshed, id = " + vacancy_id).build();
    }


}
