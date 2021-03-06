package net.sourceforge.squirrel_sql.client.session.action;

import net.sourceforge.squirrel_sql.client.IApplication;
import net.sourceforge.squirrel_sql.client.Main;
import net.sourceforge.squirrel_sql.client.action.SquirrelAction;
import net.sourceforge.squirrel_sql.client.session.ISQLPanelAPI;

import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.datatransfer.StringSelection;


public class CutSqlAction extends SquirrelAction implements ISQLPanelAction
{
	private ISQLPanelAPI _panel;

	public CutSqlAction(IApplication app)
	{
		super(app);
	}

	public void actionPerformed(ActionEvent e)
	{
		int[] bounds = _panel.getSQLEntryPanel().getBoundsOfSQLToBeExecuted();

		if(bounds[0] == bounds[1])
		{
			return;
		}

		String sqlToBeExecuted = _panel.getSQLEntryPanel().getSQLToBeExecuted();

		StringSelection contents = new StringSelection(sqlToBeExecuted);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(contents, contents);

		Main.getApplication().getPasteHistory().addToPasteHistory(sqlToBeExecuted);


		_panel.getSQLEntryPanel().setSelectionStart(bounds[0]);
		_panel.getSQLEntryPanel().setSelectionEnd(bounds[1]);
		_panel.getSQLEntryPanel().replaceSelection("");
		
	}

	public void setSQLPanel(ISQLPanelAPI panel)
	{
		_panel = panel;
		setEnabled(null != _panel);
	}

}
