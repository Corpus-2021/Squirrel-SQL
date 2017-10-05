package org.squirrelsql.drivers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.squirrelsql.PreDefinedDrivers;
import org.squirrelsql.services.CollectionUtil;
import org.squirrelsql.services.Dao;

import java.util.Collections;
import java.util.List;

public class DriversManager
{
   private List<SQLDriver> _allDrivers;

   public DriversManager()
   {
      _allDrivers = Dao.loadSquirrelDrivers();

      List<SQLDriver> preDefinedDrivers = PreDefinedDrivers.get();


      for (SQLDriver preDefinedDriver : preDefinedDrivers)
      {
         if(false == _allDrivers.contains(preDefinedDriver))
         {
            _allDrivers.add(preDefinedDriver);
         }
      }

      for (SQLDriver sqlDriver : _allDrivers)
      {
         sqlDriver.setLoaded(DriversUtil.checkDriverLoading(sqlDriver));
      }

      Collections.sort(_allDrivers);
   }

   public ObservableList<SQLDriver> getDrivers(boolean filtered)
   {
      if(filtered)
      {
         List<SQLDriver> filteredList = CollectionUtil.filter(_allDrivers, SQLDriver::isLoaded);
         return FXCollections.observableList(filteredList);
      }

      return FXCollections.observableList(_allDrivers);
   }

   public List<SQLDriver> getFilteredOutDrivers()
   {
      return CollectionUtil.filter(_allDrivers, d -> false == d.isLoaded());
   }

   public void remove(SQLDriver selectedItem)
   {
      _allDrivers.remove(selectedItem);
      Dao.writeDrivers(_allDrivers);
   }

   public void add(SQLDriver newDriver)
   {
      _allDrivers.add(newDriver);
      Dao.writeDrivers(_allDrivers);
   }

   public void editedDriver(SQLDriver selectedDriver)
   {
      Dao.writeDrivers(_allDrivers);
   }

   public void saveDrivers()
   {
      Dao.writeDrivers(_allDrivers);
   }
}
