package ru.hh.school.resource;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.Verification;
import ru.hh.school.dto.EmployerDto;
import ru.hh.school.dto.EmployerNameDto;
import ru.hh.school.service.HHDataService;

import javax.inject.Singleton;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static ru.hh.school.Params.*;


@Path("/employer")
@RequiredArgsConstructor
@Singleton
public class EmployerResource {
    private static final Logger logger = LoggerFactory.getLogger(EmployerResource.class);
    private final HHDataService hhDataService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployerNameDto> getEmployers(@QueryParam("query") String query,
                                              @QueryParam("page") @DefaultValue(DEFAULT_PAGE) @Min(MINIMUM_PAGE) int page,
                                              @QueryParam("per_page") @DefaultValue(DEFAULT_PER_PAGE)
                                              @Min(MINIMUM_PER_PAGE) @Max(MAXIMUM_PER_PAGE) int perPage) {
        logger.info("Invoked Get/employer with params: query = {}; page = {}; per_page = {}", query, page, perPage);

        Verification.checkPagingParams(perPage, page);

        return hhDataService.getEmployers(page, perPage, query);
    }

    @GET
    @Path("/{employer_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EmployerDto getEmployers(@PathParam("employer_id") long employer_id) {
        logger.info("Invoked Get/employer/{};", employer_id);
        return hhDataService.getEmployer(employer_id);
    }
}
