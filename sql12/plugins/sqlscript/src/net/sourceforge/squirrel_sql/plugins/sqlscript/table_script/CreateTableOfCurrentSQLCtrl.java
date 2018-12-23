package net.sourceforge.squirrel_sql.plugins.sqlscript.table_script;

import net.sourceforge.squirrel_sql.fw.gui.GUIUtils;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.sourceforge.squirrel_sql.fw.props.Props;


public class CreateTableOfCurrentSQLCtrl
{
   private CreateTableOfCurrentSQLDialog _dlg;
   private boolean _isOk;
   private static final String PREFS_KEY_LAST_TABLE_NAME = "squirrel_sqlscript_tempSqlResultTable";
   private static final String PREFS_KEY_SCRIPT_ONLY = "squirrel_sqlscript_script_only";
   private static final String PREFS_KEY_DROP_TABLE = "squirrel_sqlscript_drop_table";


   public CreateTableOfCurrentSQLCtrl(Frame parent)
   {
      _dlg = new CreateTableOfCurrentSQLDialog(parent);

      _dlg.btnOK.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            onOK();
         }
      });

      _dlg.btnCancel.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            onCancel();
         }
      });


      String tempSqlResultTable = Props.getString(PREFS_KEY_LAST_TABLE_NAME, "tempSqlResultTable");
      boolean dropTable = Props.getBoolean(PREFS_KEY_DROP_TABLE, false);
      boolean scriptOnly = Props.getBoolean(PREFS_KEY_SCRIPT_ONLY, true);


      _dlg.txtTableName.setText(tempSqlResultTable);
      _dlg.chkDropTable.setSelected(dropTable);
      _dlg.chkScriptOnly.setSelected(scriptOnly);


      _dlg.setSize(360,160);
      GUIUtils.centerWithinParent(_dlg);

      _dlg.setVisible(true);

   }

   private void onCancel()
   {
      //System.out.println(_dlg.getSize());
      close();
   }

   private void onOK()
   {
      _isOk = true;
      Props.putString(PREFS_KEY_LAST_TABLE_NAME, _dlg.txtTableName.getText());
      Props.putBoolean(PREFS_KEY_DROP_TABLE, _dlg.chkDropTable.isSelected());
      Props.putBoolean(PREFS_KEY_SCRIPT_ONLY, _dlg.chkScriptOnly.isSelected());
      close();
   }

   private void close()
   {
      _dlg.setVisible(false);
      _dlg.dispose();
   }


   public boolean isOK()
   {
      return _isOk;
   }

   public String getTableName()
   {
      return _dlg.txtTableName.getText();
   }

   public boolean isScriptOnly()
   {
      return _dlg.chkScriptOnly.isSelected();
   }

   public boolean isDropTable()
   {
      return _dlg.chkDropTable.isSelected();
   }
}
