package com.walmart.labs.pcs.normalize.CassandraDao;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/*
 * Created by pzhong1 on 11/28/14.
 */
public enum Cassandra {
    DB;

    private Session session;
    private Cluster cluster;
    private static final Logger LOGGER = LoggerFactory.getLogger(Cassandra.class);
    private static final String hosts = "real_host";
    private static final String keySpaceName = "real_space_name";

    /**
     * Connect to the cassandra database based on the connection configuration provided.
     * Multiple call to this method will have no effects if a connection is already established
     */
    public void connect(int port, String keySpace, String ... seeds) {
        if (cluster == null && session == null) {
            try {
                cluster = Cluster.builder().withPort(port).addContactPoints(seeds).build();
                session = cluster.connect(keySpace);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Metadata metadata = cluster.getMetadata();
        LOGGER.info("Connected to cluster: " + metadata.getClusterName() + " with partitioner: " + metadata.getPartitioner());
        for(Host host : metadata.getAllHosts()){
            LOGGER.info("Cassandra datacenter: " + host.getDatacenter() + " | address: " + host.getAddress() + " | rack: " + host.getRack());
        }
//        metadata.getAllHosts().stream().forEach((host) -> {
//
//        });
    }

    /**
     * Invalidate and close the session and connection to the cassandra database
     */
    public void shutdown() {
        LOGGER.info("Shutting down the whole cassandra cluster");
        if (null != session) {
            session.close();
        }
        if (null != cluster) {
            cluster.close();
        }
    }

    public Session getSession() {
        if (session == null) {
            throw new IllegalStateException("No connection initialized");
        }
        return session;
    }

    public static void main(String[] args) {
        try {
            Cassandra.DB.connect(9042, keySpaceName, hosts);

            //insert lease
            PreparedStatement statement = Cassandra.DB.getSession().prepare("INSERT INTO leases (name, owner, value) VALUES (?, ?, ?) IF NOT EXISTS;");
            BoundStatement boundStatement = new BoundStatement(statement);
            ResultSetFuture status = Cassandra.DB.getSession().executeAsync(boundStatement.bind("foo","client_unique_id_2", "test1"));
            for (Row row : status.get().all()) {
                System.out.println(String.format("%-30s\t%-20s\t%-20s\t%-20s", row.getBool("[applied]"), row.getString("name"),
                        row.getString("owner"), row.getString("value")));
            }

            //update lease
            statement = Cassandra.DB.getSession().prepare("UPDATE leases SET owner = ? WHERE name = ? IF owner = ?;");
            boundStatement = new BoundStatement(statement);
            boundStatement.setString(0, "'client_unique_id_1'");
            boundStatement.setString(1, "'foo'");
            boundStatement.setString(2, "'client_unique_id_2'");
            status = Cassandra.DB.getSession().executeAsync(boundStatement);
            for (Row row : status.get().all()) {
                System.out.println(String.format("%-30s", row.getBool("[applied]")));
            }


            PreparedStatement ps = Cassandra.DB.getSession().prepare("SELECT writetime(owner), name, owner, value from leases where name = ?;");
            boundStatement = new BoundStatement(ps);
            ResultSetFuture results = Cassandra.DB.getSession().executeAsync(boundStatement.bind("foo"));
            for (Row row : results.get().all()) {
                System.out.println(String.format("%-30s\t%-20s\t%-20s\t%-20s", row.getLong("writetime(owner)"), row.getString("name"),
                        row.getString("owner"), row.getString("value")));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            Cassandra.DB.shutdown();
        }
    }

}
