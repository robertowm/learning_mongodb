//package org.mongodb.morphia.demo;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ConcurrentModificationException;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import org.mongodb.morphia.query.MorphiaIterator;
//import org.mongodb.morphia.query.Query;
//
///**
// *
// * @author robertowm
// */
//public class Demos extends BaseTest {
//
//    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
//    private GithubUser user;
//    private Date date;
//
//    public Demos() throws ParseException {
//        this.date = sdf.parse("6-15-1987");
//    }
//    
//    @Test
//    public void basicUser() {
//        user = new GithubUser("evanchooly");
//        user.fullName = "Justin Lee";
//        user.memberSince = date;
//        user.following = 1000;
//        
//        ds.save(user);
//    }
//    
//    @Test(dependsOnMethods={"basicUser"})
//    public void repositories() {
//        Organization org = new Organization("mongodb");
//        ds.save(org);
//        
//        Repository morphia1 = new Repository(org, "morphia");
//        Repository morphia2 = new Repository(user, "morphia");
//        
//        ds.save(morphia1);
//        ds.save(morphia2);
//        
//        user.repositories.add(morphia1);
//        user.repositories.add(morphia2);
//        
//        ds.save(user);
//    }
//    
//    @Test(dependsOnMethods={"repositories"})
//    public void query() {
//        Query<Repository> query = ds.creatQuery(Repository.class);
//        Repository repository = query.get();
//        
//        List<Repository> repositories = query.asList();
//        
//        Iterable<Repository> fetch = query.fetch();
//        ((MorphiaIterator) fetch).close();
//        
//        Iterator<Repository> iterator = fetch.iterator();
//        while(iterator.hasNext()) {
//            iterator.next();
//        }
//        
//        iterator = fetch.iterator();
//        while(iterator.hasNext()) {
//            iterator.next();
//        }
//        
//        query.field("owner").equal(user).get();
//        
//        GithubUser memberSince = ds.createQuery(GithubUser.class)
//                .field("memberSince").equal(date).get();
//        System.out.println("memberSince = " + memberSince);
//        GithubUser since = ds.createQuery(GithubUser.class)
//                .field("since").equal(date).get();
//        System.out.println("since = " + since);
//    }
//    
//    @Test(dependsOnMethods={"repositories"})
//    public void updates() {
//        user.followers = 12;
//        user.following = 678;
//        ds.save(user);
//    }
//    
//    @Test(dependsOnMethods={"repositories"})
//    public void massUpdates() {
//        UpdateOperations<GithubUser> update =
//                ds.createUpdateOperations(GithubUser.class)
//                .inc("followers")
//                .set("following", 42);
//        Query<GithubUser> query = ds.createQuery(GithubUser.class)
//                .field("followers").equal(0);
//        ds.update(query, update);
//    }
//    
//    @Test(dependsOnMethods={"repositories"},
//            expectedExceptions = {ConcurrentModificationException.class})
//    public void versioned() {
//        Organization organization = ds.createQuery(Organization.class).get();
//        Organization organization2 = ds.createQuery(Organization.class).get();
//        
//        Assert.assertEquals(organization.version, 1l);
//        ds.save(organization);
//        
//        Assert.assertEquals(organization.version, 2l);
//        ds.save(organization);
//        
//        Assert.assertEquals(organization.version, 3l);
//        ds.save(organization2);
//    }
//}
