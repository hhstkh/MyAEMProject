package com.aem.community.service.impl;

import java.sql.Connection;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.community.core.models.Customer;
import com.aem.community.service.CustomerService;
import com.day.commons.datasource.poolservice.DataSourcePool;

@Component
public class CustomerServiceImpl implements CustomerService {
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	private DataSourcePool source;

	@Override
	public void addOrEditCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(int id) {
		// TODO Auto-generated method stub
		
	}
	
	private Connection getConnection() {
		DataSource dataSource = null;
		Connection con = null;
		
		try {
			dataSource = (DataSource) source.getDataSource("customer");
			con = dataSource.getConnection();
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
