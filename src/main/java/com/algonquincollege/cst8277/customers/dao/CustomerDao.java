/*****************************************************************c******************o*******v******id********
 * File: CustomerDao.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * @author Bhavinkumar Patel
 *
 */
package com.algonquincollege.cst8277.customers.dao;

import java.util.List;

import com.algonquincollege.cst8277.customers.model.CustomerPojo;

/**
 * File: CustomerDao.java
 * Course materials (20F) CST 8277
 * @author (original) Mike Norman
 * @author Vinay Patel
 * Description: API for the database C-R-U-D operations
 */
public interface CustomerDao {

    // C
    public CustomerPojo createCustomer(CustomerPojo customer);
    // R
    public CustomerPojo readCustomerById(int customerId);
    public List<CustomerPojo> readAllCustomers();
    // U
    public void updateCustomer(CustomerPojo customer);
    // D
    public void deleteCustomerById(int customerId);

}