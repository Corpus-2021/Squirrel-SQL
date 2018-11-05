package net.sourceforge.squirrel_sql.plugins.postgres.tab;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sourceforge.squirrel_sql.client.session.ISession;
import net.sourceforge.squirrel_sql.client.session.mainpanel.objecttree.tabs.BaseDataSetTab;
import net.sourceforge.squirrel_sql.fw.datasetviewer.DataSetException;
import net.sourceforge.squirrel_sql.fw.datasetviewer.IDataSet;
import net.sourceforge.squirrel_sql.fw.datasetviewer.ResultSetDataSet;
import net.sourceforge.squirrel_sql.fw.dialects.DialectType;
import net.sourceforge.squirrel_sql.fw.sql.ISQLConnection;
import net.sourceforge.squirrel_sql.fw.util.StringManager;
import net.sourceforge.squirrel_sql.fw.util.StringManagerFactory;

/**
 * Detail tab providing info about database active connections.
 * 
 */
public class ActiveConnections extends BaseDataSetTab {

	private static final StringManager s_stringMgr = StringManagerFactory.getStringManager(ActiveConnections.class);

	static interface i18n {
		// i18n[ActiveConnectionsTab.hint=Active connections]
		String HINT = s_stringMgr.getString("ActiveConnectionsTab.hint");
		// i18n[ActiveConnectionsTab.title=Connections]
		String TITLE = s_stringMgr.getString("ActiveConnectionsTab.title");
	}

	private static final String QUERY = "SELECT datname,procpid,query_start,current_query,client_addr::TEXT,client_hostname::TEXT,datid,client_port,backend_start,xact_start,usesysid,usename,application_name,waiting "
			+ "FROM pg_stat_activity "
			+ "ORDER BY query_start DESC NULLS LAST ";

    /**
     * pg_stat_activity.procpid renamed to pid in postgres 9.2
     * pg_stat_activity.current_query disappears and is replaced by two columns:
     * state: is the session running a query, waiting
     * query: what is the last run (or still running if stat is "active") query
     */
    private static final String QUERY_92 = "SELECT datid,datname,pid,query_start,state,query,client_addr::TEXT,client_hostname::TEXT,client_port,backend_start,xact_start,usesysid,usename,application_name"
        + " FROM pg_stat_activity"
       + " ORDER BY query_start DESC NULLS LAST";

	/**
	 * @see net.sourceforge.squirrel_sql.client.session.mainpanel.objecttree.tabs.BaseDataSetTab#createDataSet()
	 */
	@Override
	protected IDataSet createDataSet() throws DataSetException {
		final ISession session = getSession();
		try {
			ISQLConnection con = session.getSQLConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = isPostgres92(con) ? stmt.executeQuery(QUERY_92) : stmt.executeQuery(QUERY);
			ResultSetDataSet rsds = new ResultSetDataSet();
			rsds.setResultSet(rs, DialectType.POSTGRES);
			
			return rsds;
		} catch (SQLException ex) {
			throw new DataSetException(ex);
		}
	}

	private boolean isPostgres92(ISQLConnection con) throws SQLException {
	    int databaseMajorVersion = con.getSQLMetaData().getDatabaseMajorVersion();
	    int databaseMinorVersion = 0;

        String databaseProductVersion = con.getSQLMetaData().getDatabaseProductVersion();
        if (databaseProductVersion.contains(".")) {
            databaseMinorVersion = Integer.parseInt(databaseProductVersion.split("\\.")[1]);
        }

	    return (databaseMajorVersion == 9 && databaseMinorVersion >= 2) || (databaseMajorVersion >= 10);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.client.session.mainpanel.objecttree.tabs.IObjectTab#getHint()
	 */
	@Override
	public String getHint() {
		return i18n.HINT;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.client.session.mainpanel.objecttree.tabs.IObjectTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return i18n.TITLE;
	}
	
	@Override
	protected String getDestinationClassName()
	{
		return getSession().getProperties().getMetaDataOutputClassName();
	}

}
