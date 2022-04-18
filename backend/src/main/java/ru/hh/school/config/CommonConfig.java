package ru.hh.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.school.converter.AreaConverter;
import ru.hh.school.converter.EmployerConverter;
import ru.hh.school.converter.SalaryConverter;
import ru.hh.school.converter.VacancyConverter;
import ru.hh.school.dao.AreaDao;
import ru.hh.school.dao.EmployerDao;
import ru.hh.school.dao.VacancyDao;
import ru.hh.school.resource.*;
import ru.hh.school.service.EmployerFavoritesService;
import ru.hh.school.service.HHDataService;
import ru.hh.school.service.VacancyFavoritesService;

@Configuration
@Import({
  // import your beans here
  ExampleResource.class,
  NabCommonConfig.class,
  EmployerResource.class,
  HHDataService.class,
  VacancyResource.class,
  EmployerFavoritesResource.class,
  AreaConverter.class,
  EmployerConverter.class,
  AreaDao.class,
  EmployerDao.class,
  EmployerFavoritesService.class,
  VacancyFavoritesResource.class,
  VacancyFavoritesService.class,
  VacancyDao.class,
  VacancyConverter.class,
  SalaryConverter.class
})
public class CommonConfig {

  @Bean
  public MappingConfig mappingConfig() {
    MappingConfig mappingConfig = new MappingConfig();
    mappingConfig.addPackagesToScan("ru.hh.school.entity");
    return mappingConfig;
  }
}
