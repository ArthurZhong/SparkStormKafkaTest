package com.walmart.labs.pcs.normalize.CassandraDao.DaoImplementation;

import com.clearspring.analytics.util.Lists;
import com.datastax.driver.core.*;
import com.walmart.labs.pcs.normalize.CassandraDao.Cassandra;
import com.walmart.labs.pcs.normalize.CassandraDao.DaoInterface.LeasesDaoInt;
import com.walmart.labs.pcs.normalize.CassandraModule.Lease;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
 * Created by pzhong1 on 12/5/14.
 */
public class LeasesDaoImp implements LeasesDaoInt {

    private static final String hosts = "real_host";
    private static final String keySpaceName = "real_space_name";

    static{
        Cassandra.DB.connect(9042, keySpaceName, hosts);
    }

    private static final PreparedStatement insertStatement = Cassandra.DB.getSession().prepare("INSERT INTO leases (name, owner, value) VALUES (?, ?, ?) IF NOT EXISTS;");
    private static final PreparedStatement updateStatement = Cassandra.DB.getSession().prepare("UPDATE leases SET owner = ?, value = ? WHERE name = ? IF owner = ?;");
    private static final PreparedStatement deleteStatement = Cassandra.DB.getSession().prepare("DELETE FROM leases where name = ? IF owner = ? and value = ?;");
    private static final PreparedStatement selectStatement = Cassandra.DB.getSession().prepare("SELECT writetime(owner), name, owner, value from leases where name = ?;");

    @Override
    public boolean addLease(String name, String owner, String value) {
        boolean insertingStatus = false;
        try {
            BoundStatement boundStatement = new BoundStatement(insertStatement);
            ResultSetFuture status = Cassandra.DB.getSession().executeAsync(boundStatement.bind(name, owner, value));
            for (Row row : status.getUninterruptibly()) {
                insertingStatus = row.getBool("[applied]");
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertingStatus;
    }

    @Override
    public boolean updateLease(String name, String oldOwner, String newOwner, String value) {
        try {
            BoundStatement boundStatement = new BoundStatement(updateStatement);
            ResultSetFuture status = Cassandra.DB.getSession().executeAsync(boundStatement.bind(newOwner, value, name, oldOwner));
            for (Row row : status.getUninterruptibly()) {
                return row.getBool("[applied]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteLease(String name, String owner, String value) {
        try {
            BoundStatement boundStatement = new BoundStatement(deleteStatement);
            ResultSetFuture status = Cassandra.DB.getSession().executeAsync(boundStatement.bind(name, owner, value));
            for (Row row : status.getUninterruptibly()) {
                return row.getBool("[applied]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Lease> getLeasesByName(String name) {
        List<Lease> leaseList = Lists.newArrayList();
        try {
            BoundStatement boundStatement = new BoundStatement(selectStatement);
            ResultSetFuture results = Cassandra.DB.getSession().executeAsync(boundStatement.bind(name));
            for (Row row : results.get().all()) {
                leaseList.add(new Lease(row.getString("name"), row.getString("owner"), row.getString("value"), row.getLong("writetime(owner)")));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return leaseList;
    }

}
