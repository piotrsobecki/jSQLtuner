package pl.piotrsukiennik.tuner.test.model;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:54
 */
@Entity( name = "MOCK_DATA" )
public class MockDataModel implements MockData {


    public MockDataModel() {
    }

    public MockDataModel( MockData data ) {
        this.id = data.getId();
        this.firstName = data.getFirstName();
        this.lastName = data.getLastName();
        this.email = data.getEmail();
        this.country = data.getCountry();
        this.ipAddress = data.getIpAddress();
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer id;

    @Column( name = "first_name" )
    private String firstName;

    @Column( name = "last_name" )
    private String lastName;

    @Column( name = "email" )
    private String email;

    @Column( name = "country" )
    private String country;

    @Column( name = "ip_address" )
    private String ipAddress;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public void setCountry( String country ) {
        this.country = country;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress( String ipAddress ) {
        this.ipAddress = ipAddress;
    }
}
