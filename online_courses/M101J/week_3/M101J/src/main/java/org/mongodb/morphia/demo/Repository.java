package org.mongodb.morphia.demo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author robertowm
 */
@Entity("repos")
public class Repository {

    @Id
    public String name;
    @Reference
    public Organization organization;
    @Reference
    public GithubUser owner;
//    public Settings settings = new Settings()

    public Repository() {
    }

    public Repository(final Organization organization, final String name) {
        this.organization = organization;
        this.name = organization.name + "/" + name;
    }
}
