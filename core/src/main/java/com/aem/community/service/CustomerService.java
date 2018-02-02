package com.aem.community.service;

import com.aem.community.core.models.Customer;

public interface CustomerService {
	public void addOrEditCustomer(Customer customer);
	public void deleteCustomer(int id);
}
