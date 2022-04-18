package ru.hh.school.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.dto.EmployerDto;
import ru.hh.school.dto.EmployerNameDto;
import ru.hh.school.dto.VacancyDto;

import javax.inject.Singleton;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@Singleton
public class HHDataService {

    private static final String HH_API_URI = "https://api.hh.ru";
    private static final String HH_EMPLOYERS_PATH = "employers";
    private static final String HH_VACANCIES_PATH = "vacancies";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Logger logger = LoggerFactory.getLogger(HHDataService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<EmployerNameDto> getEmployers(int page, int perPage, String text) {
        logger.info("Invoked to fetch data from {}, method: {}", this.getClass(), "getEmployers");

        try {
            HttpResponse<String> response = client.send(createGetRequest(createUriWithPaging(HH_EMPLOYERS_PATH, page, perPage, text)),
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK_200) {
                logger.info("Failed to fetch data from {}, method: {},  response: {}", this.getClass(), "getEmployers", response.body());
                throw new RuntimeException(response.body());
            }

            JsonNode jsonNode = objectMapper.readTree(response.body());
            return objectMapper.readValue(jsonNode.get("items").toString(), new TypeReference<>() {
            });

        } catch (IOException | InterruptedException e) {
            logger.error("Failed to fetch data from {}, method: {}", this.getClass(), "getEmployers", e.getCause());
            return Collections.emptyList();
        }
    }

    public EmployerDto getEmployer(long employer_id) {
        logger.info("Invoked to fetch data from {}, method: {}", this.getClass(), "getEmployer");

        try {
            HttpResponse<String> response = client.send(createGetRequest(createUriWithId(HH_EMPLOYERS_PATH, String.valueOf(employer_id))),
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK_200) {
                logger.info("Failed to fetch data from {}, method: {},  response: {}", this.getClass(), "getEmployer", response.body());
                throw new RuntimeException(response.body());
            }

            return objectMapper.readValue(response.body(), EmployerDto.class);

        } catch (IOException | InterruptedException e) {
            logger.error("Failed to fetch data from {}, method: {}", this.getClass(), "getEmployers", e.getCause());
            return null;
        }
    }

    public List<VacancyDto> getVacancies(int page, int perPage, String text) {
        logger.info("Invoked to fetch data from {}, method: {}", this.getClass(), "getVacancies");

        try {
            HttpResponse<String> response = client.send(createGetRequest(createUriWithPaging(HH_VACANCIES_PATH, page, perPage, text)),
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK_200) {
                logger.info("Failed to fetch data from {}, method: {},  response: {}", this.getClass(), "getVacancies", response.body());
                throw new RuntimeException(response.body());
            }

            JsonNode jsonNode = objectMapper.readTree(response.body());
            return objectMapper.readValue(jsonNode.get("items").toString(), new TypeReference<>() {
            });

        } catch (IOException | InterruptedException e) {
            logger.error("Failed to fetch data from {}, method: {}", this.getClass(), "getVacancies", e.getCause());
            return Collections.emptyList();
        }
    }

    public VacancyDto getVacancy(long vacancy_id) {
        logger.info("Invoked to fetch data from {}, method: {}", this.getClass(), "getVacancy");

        try {
            HttpResponse<String> response = client.send(createGetRequest(createUriWithId(HH_VACANCIES_PATH, String.valueOf(vacancy_id))),
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK_200) {
                logger.info("Failed to fetch data from {}, method: {},  response: {}", this.getClass(), "getVacancy", response.body());
                throw new RuntimeException(response.body());
            }

            return objectMapper.readValue(response.body(), VacancyDto.class);

        } catch (IOException | InterruptedException e) {
            logger.error("Failed to fetch data from {}, method: {}", this.getClass(), "getVacancy", e.getCause());
            return null;
        }
    }

    private HttpRequest createGetRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();
    }

    private URI createUriWithPaging(String path, int page, int perPage, String text) {
        return UriBuilder.fromUri(HH_API_URI)
                .path(path)
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .queryParam("text", text)
                .build();
    }

    private URI createUriWithId(String path, String id) {
        return UriBuilder.fromUri(HH_API_URI)
                .path(path)
                .path(id)
                .build();
    }
}
