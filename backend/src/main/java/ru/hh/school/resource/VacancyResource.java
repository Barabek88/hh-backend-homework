package ru.hh.school.resource;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.Verification;
import ru.hh.school.dto.VacancyDto;
import ru.hh.school.service.HHDataService;

import javax.inject.Singleton;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static ru.hh.school.Params.*;

@Path("/vacancy")
@RequiredArgsConstructor
@Singleton
public class VacancyResource {
    private static final Logger logger = LoggerFactory.getLogger(VacancyResource.class);
    private final HHDataService hhDataService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<VacancyDto> getVacancy(@QueryParam("query") String query,
                                       @QueryParam("page") @DefaultValue(DEFAULT_PAGE) @Min(MINIMUM_PAGE) int page,
                                       @QueryParam("per_page") @DefaultValue(DEFAULT_PER_PAGE)
                                       @Min(MINIMUM_PER_PAGE) @Max(MAXIMUM_PER_PAGE) int per_page) {

        logger.info("Invoked Get/vacancy with params: query = {}; page = {}; per_page = {}", query, page, per_page);

        Verification.checkPagingParams(per_page, page);

        return hhDataService.getVacancies(page, per_page, query);
    }

    @GET
    @Path("/{vacancy_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VacancyDto getEmployers(@PathParam("vacancy_id") long vacancy_id) {
        logger.info("Invoked Get/vacancy/{};", vacancy_id);
        return hhDataService.getVacancy(vacancy_id);
    }
}
