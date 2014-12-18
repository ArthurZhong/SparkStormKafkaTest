package com.walmart.labs.pcs.normalize;

import com.walmart.labs.pcs.normalize.CassandraDao.DaoImplementation.LeasesDaoImp;
import com.walmart.labs.pcs.normalize.CassandraDao.DaoInterface.LeasesDaoInt;
import com.walmart.labs.pcs.normalize.CassandraModule.Lease;

import java.util.List;

/*
 * Created by pzhong1 on 12/5/14.
 */
public class CassandraDaoTest {
    public static void main(String[] args) {
        LeasesDaoInt leasesDao = new LeasesDaoImp();
        System.out.println(leasesDao.addLease("foo","client_unique_id_2", "test1"));
        List<Lease> leaseList = leasesDao.getLeasesByName("foo");
        for(Lease lease : leaseList){
            System.out.println(lease.toString());
        }
        System.out.println(leasesDao.updateLease("foo", "client_unique_id_2", "client_unique_id_1", "test1"));

        leaseList = leasesDao.getLeasesByName("foo");
        for(Lease lease : leaseList){
            System.out.println(lease.toString());
        }

        System.out.println(leasesDao.deleteLease("foo","client_unique_id_1", "test1"));

        leaseList = leasesDao.getLeasesByName("foo");
        for(Lease lease : leaseList){
            System.out.println(lease.toString());
        }

    }
}
