package com.tor.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 15:19
 * http://www.tutorialspoint.com/ejb/ejb_embeddable_objects.htm
 */
@Embeddable
public class Publisher implements Serializable {
    private String name;
    private String address;

    public Publisher() {
    }

    public Publisher(String publisherName, String publisherAddress) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Publisher{");
        sb.append("name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
