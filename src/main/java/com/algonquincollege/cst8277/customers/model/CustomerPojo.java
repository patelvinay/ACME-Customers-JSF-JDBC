/*****************************************************************c******************o*******v******id********
 * File: CustomerPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * @author Bhavinkumar Patel
 *
 */
package com.algonquincollege.cst8277.customers.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.faces.view.ViewScoped;



/**
*
* Description: model for the Customer object
*/
@ViewScoped
public class CustomerPojo implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
   
    // these Model fields not used in Assignment 1 (later)
    protected LocalDateTime created;
    protected LocalDateTime updated;
    protected int version;
    protected boolean editable;

    public boolean isEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getId() {
        return id;
    }
    /**
     * @param id new value for id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the value for firstName
     */

    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the value for lastName
     */

    public String getLastName() {
        return lastName;
    }
    /**
     * @param firstName new value for firstName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * @return the value for email
     */

    public String getEmail() {
        return email;
    }
    /**
     * @param firstName new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the value for phoneNumber
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @param firstName new value for phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    // these methods not used in Assignment 1 (later)

   
    public LocalDateTime getCreatedDate() {
        return created;
    }
    public void setCreatedDate(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdatedDate() {
        return updated;
    }
    public void setUpdatedDate(LocalDateTime updated) {
        this.updated = updated;
    }
    
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomerPojo)) {
            return false;
        }
        CustomerPojo other = (CustomerPojo) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("Customer [id=")
            .append(id)
            .append(", ");
        if (firstName != null) {
            builder
                .append("firstName=")
                .append(firstName)
                .append(", ");
        }
        if (lastName != null) {
            builder
                .append("lastName=")
                .append(lastName)
                .append(", ");
        }
        if (email != null) {
            builder
                .append("email=")
                .append(email)
                .append(", ");
        }
        if (phoneNumber != null) {
            builder
                .append("phoneNumber=")
                .append(phoneNumber)
                .append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

}