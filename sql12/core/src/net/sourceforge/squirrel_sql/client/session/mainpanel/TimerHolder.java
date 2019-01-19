package net.sourceforge.squirrel_sql.client.session.mainpanel;

import net.sourceforge.squirrel_sql.fw.datasetviewer.ResultSetDataSet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This timer treatment seems to give better Session GCing behaviour.
 */
public class TimerHolder
{
   private Timer _timer;
   private JTextField _txtExecTimeCounter;

   private final long _beginMillis;
   private ResultSetDataSet _rsds;
   private JTextField _txtNumberOfRowsRead;


   public TimerHolder(JTextField txtExecTimeCounter, JTextField txtNumberOfRowsRead)
   {
      _beginMillis = System.currentTimeMillis();

      _txtExecTimeCounter = txtExecTimeCounter;
      _txtNumberOfRowsRead = txtNumberOfRowsRead;

      _timer = new Timer(300, new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            onUpdateExecutionTime();
         }
      });
      _timer.setRepeats(true);
      _timer.start();

   }

   private void onUpdateExecutionTime()
   {
      if (null != _txtExecTimeCounter)
      {
         _txtExecTimeCounter.setText("" + (System.currentTimeMillis() - _beginMillis));
      }

      if(null != _rsds)
      {
         _txtNumberOfRowsRead.setText("" + _rsds.getAllDataForReadOnly().size());
      }
   }

   public void stop()
   {
      _timer.stop();
      _txtExecTimeCounter = null;
   }

   public void setResultSetDataSetInProgress(ResultSetDataSet rsds)
   {
      _rsds = rsds;
   }
}
