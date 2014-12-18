package com.walmart.labs.pcs.normalize.CassandraDao.DaoInterface;

import com.walmart.labs.pcs.normalize.CassandraModule.Lease;

import java.util.List;

/*
 * Created by pzhong1 on 12/5/14.
 */
public interface LeasesDaoInt {
    public boolean addLease(String name, String owner, String value);
    public boolean updateLease(String name, String oldOwner, String newOwner, String value);
    public boolean deleteLease(String name, String owner, String value);
    public List<Lease> getLeasesByName(String name);
}
