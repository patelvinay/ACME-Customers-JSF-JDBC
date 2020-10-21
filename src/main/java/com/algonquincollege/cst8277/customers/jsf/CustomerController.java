/*****************************************************************c******************o*******v******id********
 * File: CustomerController.java
 * Course materials (20F) CST 8277
 * @author (original) Mike Norman
 * @author Vinay Patel
 *
 */
package com.algonquincollege.cst8277.customers.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import javax.inject.Inject;
import javax.inject.Named;

import com.algonquincollege.cst8277.customers.dao.CustomerDao;
import com.algonquincollege.cst8277.customers.model.CustomerPojo;

/**
 * Description: Responsible for collection of Customer Pojo's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behaviour to DAO
 */

@Named
@SessionScoped
public class CustomerController implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Map<String, Object> sessionMap;

    @Inject
    protected CustomerDao customerDao;

    protected List<CustomerPojo> customers;

    /**
     * @param customerDao
     * method to make controller work
     */
    @Inject
    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
        this.sessionMap = new HashMap<String, Object>();
    }
   
    /**
     * @return sessionMap
     */
    public Map<String, Object> getSessionMap() {
        
        return sessionMap;
    }

    /**
     * @param sessionMap
     */
    public void setSessionMap(Map<String, Object> sessionMap) {
        
        this.sessionMap = sessionMap;
    }
    
    /**
     * load all customers from database
     */
    public void loadCustomers() {
        setCustomers(customerDao.readAllCustomers());
    }

    /**
     * get customers
     * @return customers
     */
    public List<CustomerPojo> getCustomers() {
        return customers;
    }
    
    /**
     * @param customers
     * set customers
     */
    public void setCustomers(List<CustomerPojo> customers) {
        this.customers = customers;
    }

    /**
     * navigate to add new customer page(form)
     * @return will redirect to add-customer.xhtml 
     * 
     */
    public String navigateToAddForm() {
        sessionMap.put("newCustomer", new CustomerPojo());
        return "add-customer?faces-redirect=true";
    }
	
    /**
     * @return will redirect to list-customer.xhtml after successfull insert query.
     * @param customer 
     */
    public String submitCustomer(CustomerPojo customer) {
        customerDao.createCustomer(customer);
        loadCustomers(); //load customer
        return "list-customers.xhtml?faces-redirect=true";
    }

    /**
     * navigate to update customer page(form)
     * @return will redirect to edit-customer.xhtml
     * @param id
     */
    public String navigateToUpdateForm(int id) {
        CustomerPojo uCustomer = customerDao.readCustomerById(id); //read the customer by id to get the old data 
        sessionMap.put("updateCustomer", uCustomer);
        return "edit-customer.xhtml?faces-redirect=true";
    }

    /**
     * @return will redirect to list-customers.xhtml after successfull update query.
     * @param customer
     */
    public String submitUpdatedCustomer(CustomerPojo customer) {
       customerDao.updateCustomer(customer);
       loadCustomers(); //load customer
       return "list-customers.xhtml?faces-redirect=true";
    }

    /**
     * @return will redirect to list-customers.xhtml after successfull delete query.
     * @param id
     */
    public String deleteCustomer(int id) {
            customerDao.deleteCustomerById(id);
            return "list-customers?faces-redirect=true";
        
    }

}