package net.sourceforge.squirrel_sql.client.session;
/*
 * Copyright (C) 2001 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sourceforge.squirrel_sql.fw.id.IIdentifier;
import net.sourceforge.squirrel_sql.fw.sql.ISQLAlias;
import net.sourceforge.squirrel_sql.fw.sql.ISQLDriver;
import net.sourceforge.squirrel_sql.fw.sql.SQLConnection;
import net.sourceforge.squirrel_sql.fw.util.IMessageHandler;
import net.sourceforge.squirrel_sql.fw.util.NullMessageHandler;

import net.sourceforge.squirrel_sql.client.IApplication;
import net.sourceforge.squirrel_sql.client.plugin.IPlugin;
import net.sourceforge.squirrel_sql.client.preferences.SquirrelPreferences;
import net.sourceforge.squirrel_sql.client.session.properties.SessionSheetProperties;
import net.sourceforge.squirrel_sql.client.util.IdentifierFactory;

class Session implements ISession {
    private SessionSheet _sessionSheet;
    /** The <TT>IIdentifier</TT> that uniquely identifies this object. */
    private IIdentifier _id = IdentifierFactory.getInstance().createIdentifier();

    private IApplication _app;

    /** Connection to database. */
    private SQLConnection _conn;

    /** Driver used to connect to database. */
    private ISQLDriver _driver;

    /** Alias describing how to connect to database. */
    private ISQLAlias _alias;

    /** Properties for this session. */
    private SessionSheetProperties _props = new SessionSheetProperties();

    /**
     * Objects stored in session. Each entry is a <TT>Map</TT>
     * keyed by <TT>IPlugin.getInternalName()</TT>. Each <TT>Map</TT>
     * contains the objects saved for the plugin.
     */
    private Map _pluginObjects = new HashMap();
    private IMessageHandler _msgHandler = NullMessageHandler.getInstance();
    /**
     * Create a new session.
     *
     * @param   app     Application API.
     * @param   driver  JDBC driver for session.
     * @param   alias   Defines URL to database.
     * @param   conn    Connection to database.
     *
     * @throws IllegalArgumentException if any parameter is null.
     */
    public Session(IApplication app, ISQLDriver driver, ISQLAlias alias, SQLConnection conn) {
        super();
        if (app == null) {
            throw new IllegalArgumentException("null IApplication passed");
        }
        if (driver == null) {
            throw new IllegalArgumentException("null ISQLDriver passed");
        }
        if (alias == null) {
            throw new IllegalArgumentException("null ISQLAlias passed");
        }
        if (conn == null) {
            throw new IllegalArgumentException("null SQLConnection passed");
        }

        _app = app;
        _driver = driver;
        _alias = alias;
        _conn = conn;
        _props.assignFrom(_app.getSquirrelPreferences().getSessionSheetProperties());
    }

    public IIdentifier getIdentifier() {
        return _id;
    }

    public IApplication getApplication() {
        return _app;
    }

    /**
     * @return <TT>SQLConnection</TT> for this session.
     */
    public SQLConnection getSQLConnection() {
        return _conn;
    }
    /**
     * @return <TT>ISQLDriver</TT> for this session.
     */
    public ISQLDriver getDriver() {
        return _driver;
    }
    /**
     * @return <TT>ISQLAlias</TT> for this session.
     */
    public ISQLAlias getAlias() {
        return _alias;
    }

    public SessionSheetProperties getProperties() {
        return _props;
    }

    public synchronized Object getPluginObject(IPlugin plugin, String key) {
        if (plugin == null) {
            throw new IllegalArgumentException("Null IPlugin passed");
        }
        if (key == null) {
            throw new IllegalArgumentException("Null key passed");
        }
        Map map = (Map)_pluginObjects.get(plugin.getInternalName());
        if (map == null) {
            map = new HashMap();
            _pluginObjects.put(plugin.getInternalName(), map);
        }
        return map.get(key);
    }

    public synchronized Object putPluginObject(IPlugin plugin, String key, Object value) {
        if (plugin == null) {
            throw new IllegalArgumentException("Null IPlugin passed");
        }
        if (key == null) {
            throw new IllegalArgumentException("Null key passed");
        }
        Map map = (Map)_pluginObjects.get(plugin.getInternalName());
        if (map == null) {
            map = new HashMap();
            _pluginObjects.put(plugin.getInternalName(), map);
        }
        return map.put(key, value);
    }

    public String getSQLScript() {
        return _sessionSheet.getSQLScript();
    }

    public void setSQLScript(String sqlScript) {
        _sessionSheet.setSQLScript(sqlScript);
    }

    public void closeSQLConnection() throws SQLException {
        if (_conn != null) {
            try {
                _conn.close();
            } finally {
                _conn = null;
            }
        }
    }

    public IMessageHandler getMessageHandler() {
        return _msgHandler;
    }
    public void setMessageHandler(IMessageHandler handler) {
        _msgHandler = handler != null ? handler : NullMessageHandler.getInstance();
    }
    public void showMessage(Exception ex) {
        _msgHandler.showMessage(ex);
    }

    public void showMessage(String msg) {
        _msgHandler.showMessage(msg);
    }
    public void setSessionSheet(SessionSheet child) {
        _sessionSheet = child;
    }
}

