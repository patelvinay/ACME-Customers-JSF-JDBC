/*****************************************************************c******************o*******v******id********
 * File: CustomerDaoImpl.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * @author Vinay Patel
 *
 */
package com.algonquincollege.cst8277.customers.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import com.algonquincollege.cst8277.customers.model.CustomerPojo;

/**
* Description: Implements the C-R-U-D API for the database
*/


@Named
@ApplicationScoped
public class CustomerDaoImpl implements CustomerDao, Serializable {
    /** explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;

    private static final String CUSTOMER_DS_JNDI =
    "java:app/jdbc/customers";
    private static final String READ_ALL =
    "select * from customer";
    private static final String READ_CUSTOMER_BY_ID =
    "select * from customer where id = ?";
    private static final String INSERT_CUSTOMER =
    "insert into customer(fname, lname, email, phonenumber) values(?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER_ALL_FIELDS =
    "update customer set fname=?, lname=?, email=?, phonenumber=? WHERE id=?";
    private static final String DELETE_CUSTOMER_BY_ID =
    "delete from customer where id = ?";

    @Inject
    protected ExternalContext externalContext;
    private void logMsg(String msg) {
        ((ServletContext)externalContext.getContext()).log(msg);
    }

    @Resource(lookup = CUSTOMER_DS_JNDI)
    protected DataSource customerDS;

    protected Connection conn;
    protected PreparedStatement readAllPstmt;
    protected PreparedStatement readByIdPstmt;
    protected PreparedStatement createPstmt;
    protected PreparedStatement updatePstmt;
    protected PreparedStatement deleteByIdPstmt;

    @PostConstruct
    protected void buildConnectionAndStatements() {
        try {
            logMsg("building connection and stmts");
            conn = customerDS.getConnection();
            readAllPstmt = conn.prepareStatement(READ_ALL);
            readByIdPstmt = conn.prepareStatement(READ_CUSTOMER_BY_ID);
            createPstmt = conn.prepareStatement(INSERT_CUSTOMER, RETURN_GENERATED_KEYS);
            updatePstmt = conn.prepareStatement(UPDATE_CUSTOMER_ALL_FIELDS);
            deleteByIdPstmt = conn.prepareStatement(DELETE_CUSTOMER_BY_ID);
          
        }
        catch (Exception e) {
            logMsg("something went wrong getting connection from database: " + e.getLocalizedMessage());
        }
    }

    @PreDestroy
    protected void closeConnectionAndStatements() {
        try {
            logMsg("closing stmts and connection");
            readAllPstmt.close();
            readByIdPstmt.close();
            createPstmt.close();
            updatePstmt.close();
            deleteByIdPstmt.close();
            
            
            conn.close();
        }
        catch (Exception e) {
            logMsg("something went wrong closing stmts or connection: " + e.getLocalizedMessage());
        }
    }
    

    /**
     * @return the list of customer
     */
    @Override
    public List<CustomerPojo> readAllCustomers() {
        logMsg("reading all customers");
        List<CustomerPojo> cutomer = new ArrayList<>();
        try {
            ResultSet rs = readAllPstmt.executeQuery();
            while (rs.next()) {
                CustomerPojo newEmployee = new CustomerPojo();
                newEmployee.setId(rs.getInt("id"));
                newEmployee.setFirstName(rs.getString("fname"));
                newEmployee.setLastName(rs.getString("lname"));
                newEmployee.setEmail(rs.getString("email"));
                newEmployee.setPhoneNumber(rs.getString("phonenumber"));
                cutomer.add(newEmployee);
            }
            try {
                rs.close();
            }
            catch (Exception e) {
                logMsg("something went wrong closing resultSet: " + e.getLocalizedMessage());
            }
        }
        catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
        return cutomer;
    }
    

    /**
     * @param new value for customer
     * 
     * add a new customer
     */
    @Override
    public CustomerPojo createCustomer(CustomerPojo customer) {
        logMsg("creating an customer");
        
        try {
            createPstmt.setString(1, customer.getFirstName());
            createPstmt.setString(2, customer.getLastName());
            createPstmt.setString(3, customer.getEmail());
            createPstmt.setString(4, customer.getPhoneNumber());
            createPstmt.executeUpdate();
            
        }
        catch (Exception e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
       
        return customer;
    }

    /**
     * @param value for customerId
     * 
     * read customer by Id
     * @return foundCustomer
     */
    @Override
    public CustomerPojo readCustomerById(int customerId) {
        logMsg("read a specific customer");
        CustomerPojo foundCustomer = null;
        foundCustomer = new CustomerPojo();
        try {
            readByIdPstmt.setInt(1, customerId);
            ResultSet rs = readByIdPstmt.executeQuery();
            
            if (rs.next()) {
            
                foundCustomer.setId(customerId);
                foundCustomer.setFirstName(rs.getString("fname"));
                foundCustomer.setLastName(rs.getString("lname"));
                foundCustomer.setEmail(rs.getString("email"));
                foundCustomer.setPhoneNumber(rs.getString("phonenumber"));
             
            }
            try {
                rs.close();
            }
            catch (Exception e) {
                logMsg("something went wrong closing resultSet: " + e.getLocalizedMessage());
            }
        }
        catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
        
        return foundCustomer;
    }

    /**
     * @param value for customer
     * 
     * update customer fields
     */
    @Override
    public void updateCustomer(CustomerPojo customer) {
        logMsg("updating a specific customer");
        try {
            updatePstmt.setString(1, customer.getFirstName());
            updatePstmt.setString(2, customer.getLastName());
            updatePstmt.setString(3, customer.getEmail());
            updatePstmt.setString(4, customer.getPhoneNumber());
            updatePstmt.setInt(5, customer.getId());
            updatePstmt.executeUpdate();
            
        }catch (SQLException e) {
            logMsg("something went wrong update from database (" + e.getSQLState() + ", " + e.getLocalizedMessage() + ")");
        }
        
    }

    /**
     * @param value for customerId
     * 
     * delete a customer
     */
    @Override
    public void deleteCustomerById(int customerId) {
        logMsg("deleting a specific customer");
        try {
            deleteByIdPstmt.setInt(1, customerId);
            int numRows = deleteByIdPstmt.executeUpdate();
            if (numRows < 1) {
                logMsg("something went wrong deleting from database (numRows < 1)");
            }
        }
        catch (SQLException e) {
            logMsg("something went wrong deleting from database (" + e.getSQLState() + ", " + e.getLocalizedMessage() + ")");
        }
    }

}