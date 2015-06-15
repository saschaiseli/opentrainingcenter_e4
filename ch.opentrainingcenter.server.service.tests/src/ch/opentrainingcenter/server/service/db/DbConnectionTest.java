package ch.opentrainingcenter.server.service.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("nls")
public class DbConnectionTest {

    @org.junit.Test
    public void testDbNameGut() {
        final DbConnection connection = new DbConnection("driver", "dialect1", "jdbc:h2:file:~/.otc_dev/otc", null, null);
        assertEquals("otc", connection.getDatabaseName());
    }

    @Test
    public void testDbNameFalsch() {
        final DbConnection connection = new DbConnection("driver", "dialect1", "jdbc:h2:file:~.otc_devotc", null, null);
        assertEquals("jdbc:h2:file:~.otc_devotc", connection.getDatabaseName());
    }

    @Test(timeout = 100)
    public void testDbConnection() {
        final DbConnection connection = new DbConnection("driver", "dialect1");
        connection.setPassword("pw");
        connection.setUsername("user");
        connection.setUrl("url");

        assertEquals("user", connection.getUsername());
        assertEquals("pw", connection.getPassword());
        assertEquals("url", connection.getUrl());
    }
}
