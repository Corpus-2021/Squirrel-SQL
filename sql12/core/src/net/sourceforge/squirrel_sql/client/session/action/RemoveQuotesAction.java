package net.sourceforge.squirrel_sql.client.session.action;
/*
 * Copyright (C) 2003 Gerd Wagner
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

import java.awt.event.ActionEvent;

import net.sourceforge.squirrel_sql.fw.util.log.ILogger;
import net.sourceforge.squirrel_sql.fw.util.log.LoggerController;
import net.sourceforge.squirrel_sql.fw.util.StringManagerFactory;
import net.sourceforge.squirrel_sql.fw.util.StringManager;

import net.sourceforge.squirrel_sql.client.IApplication;
import net.sourceforge.squirrel_sql.client.action.SquirrelAction;
import net.sourceforge.squirrel_sql.client.session.ISession;
import net.sourceforge.squirrel_sql.client.session.ISQLPanelAPI;

/**
 * This action will remove &quot;quote&quot; from an SQL string.
 *
 * @author Gerd Wagner
 */
public class RemoveQuotesAction extends SquirrelAction implements ISQLPanelAction
{
   private static final StringManager s_stringMgr = StringManagerFactory.getStringManager(RemoveQuotesAction.class);

   private static final ILogger s_log = LoggerController.createLogger(RemoveQuotesAction.class);

   private ISession _session;


   public RemoveQuotesAction(IApplication app)
   {
      super(app);
   }

   public void setSQLPanel(ISQLPanelAPI panel)
   {
      if (null != panel)
      {
         _session = panel.getSession();
      }
      else
      {
         _session = null;
      }
      setEnabled(null != _session);
   }


   public void actionPerformed(ActionEvent evt)
   {
      if (_session != null)
      {
         try
         {
            //new RemoveQuotesCommand(_session.getMainSQLPanelAPI(_plugin)).execute();
            new RemoveQuotesCommand(FrameWorkAcessor.getSQLPanelAPI(_session)).execute();
         }
         catch (Throwable ex)
         {
            // i18n[editextras.errorRemoveQuotes=Error processing Remove Quotes SQL command: {0}]
            final String msg = s_stringMgr.getString("editextras.errorRemoveQuotes", ex);

            _session.showErrorMessage(msg);
            s_log.error(msg, ex);
         }
      }
   }

}
