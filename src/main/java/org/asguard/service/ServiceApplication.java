package org.asguard.service;


import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.asguard.service.daoimpl.EmployeeDaoImpl;
import org.asguard.service.model.DefaultConfiguration;
import org.asguard.service.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceApplication extends Application<DefaultConfiguration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceApplication.class);

	private HibernateBundle<DefaultConfiguration> hibernateConfig =  new HibernateBundle<DefaultConfiguration>(Employee.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(DefaultConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(Bootstrap<DefaultConfiguration> b) {
		b.addBundle(hibernateConfig);
	}

	@Override
	public void run(DefaultConfiguration c, Environment e) throws Exception {
		LOGGER.info("Registering REST resources");
		EmployeeDaoImpl employeeDao = new EmployeeDaoImpl(hibernateConfig.getSessionFactory());
		e.jersey().register(new EmployeeService(employeeDao));
	}

	public static void main(String[] args) throws Exception {
		new ServiceApplication().run(args);
	}

}
