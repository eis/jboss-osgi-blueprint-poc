package fi.eis.applications.jboss.poc.osgiservice.blueprint.jndi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.osgi.service.jndi.JNDIContextManager;

import fi.eis.applications.jboss.poc.osgiservice.api.MessageService;

public class SimpleJNDIServiceBean implements MessageService {
    private JNDIContextManager jndiContextManager;

    public void bindJNDIService(final JNDIContextManager jndiContextManager) {
        this.jndiContextManager = jndiContextManager;
        log.info(jndiContextManager + " is linked");
    }

    public void unbindJNDIService(final JNDIContextManager jndiContextManager) {
        this.jndiContextManager = null;
        log.info("jndiContextManager is unlinked");
    }

	private static Logger log = Logger.getLogger(SimpleJNDIServiceBean.class);

	private static final String JBOSS_DEFAULT_DATA_SOURCE_JNDI_NAME = "java:jboss/datasources/ExampleDS";

	private static final String JBOSS_DEFAULT_DATA_SOURCE_PASS = "sa";

	private static final String JBOSS_DEFAULT_DATA_SOURCE_USER = "sa";

	@Override
	public String getMessage() {

		DataSource ds = null;
		Connection conn = null;

		try {
			ds = getDataSource();
			conn = getConnection(ds);
			addData(conn);
			return "Got from database: " + getResultFrom(conn, "Hello%");
		} catch (final IllegalStateException ex) {
			log.error("error working with data source", ex);
			return "Error working with data source: " + ex.getMessage();
		} finally {
			close(conn);
		}
	}



	/**
	 * For the sake of testing, we do a roundtrip to database here.
	 *
	 * @param conn connection to the database containing data
	 * @param searchWord what word do we want to search with
	 * @return what we were able to find
	 */
	private String getResultFrom(final Connection conn, final String searchWord) {
		final String GET_NAME =
			"SELECT name FROM test WHERE name LIKE ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(GET_NAME);
			ps.setString(1, searchWord);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("name");
			}
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			close(rs);
			close(ps);
		}
		return "";
	}

	private void addData(final Connection conn) {
		final String DROP_PREV_TABLE = "DROP TABLE test IF EXISTS";
		final String CREATE_TABLE = "CREATE TABLE test(id INT PRIMARY KEY, name VARCHAR)";
		final String INSERT_DATA = "INSERT INTO test VALUES(1, 'Hello Worrld')";

		Statement st = null;
		try {
			st = conn.createStatement();
			st.execute(DROP_PREV_TABLE);
			st.execute(CREATE_TABLE);
			st.execute(INSERT_DATA);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			close(st);
		}

	}

	private static void close(final ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (final SQLException e) {
				throw new IllegalStateException("Couldn't close", e);
			}
		}
	}

	private static void close(final Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (final SQLException e) {
				throw new IllegalStateException("Couldn't close", e);
			}
		}
	}

	private static void close(final PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (final SQLException e) {
				throw new IllegalStateException("Couldn't close", e);
			}
		}
	}
	private static void close(final Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (final SQLException e) {
				throw new IllegalStateException("Couldn't close", e);
			}
		}
	}


	private static Connection getConnection(final DataSource ds) {
		try {
			return ds.getConnection(JBOSS_DEFAULT_DATA_SOURCE_USER, JBOSS_DEFAULT_DATA_SOURCE_PASS);
		} catch (final SQLException e) {
			throw new IllegalStateException("Getting a connection failed", e);
		}
	}


	private DataSource getDataSource() {

		log.debug("jndi context manager is " + jndiContextManager);

		// create a context with the default environment setup
		Context initialContext = null;
		try {
			initialContext = jndiContextManager.newInitialContext();
		} catch (final NamingException e) {
			throw new IllegalStateException("JNDI lookup failed", e);
		}

		log.debug("jndi initial context is " + initialContext);

		try {
			return (DataSource) initialContext
					.lookup(JBOSS_DEFAULT_DATA_SOURCE_JNDI_NAME);
		} catch (final NamingException e) {
			throw new IllegalStateException( "DS lookup failed", e);
		}

	}

}
