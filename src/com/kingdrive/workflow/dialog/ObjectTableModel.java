package com.kingdrive.workflow.dialog;

import java.lang.reflect.Field;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ObjectTableModel extends AbstractTableModel
{
  private static final long serialVersionUID = 1443961875368326621L;
  private String[] columnNames;
  private String[] propNames;
  private List rowList;

  public ObjectTableModel(String[] columnNames, String[] propNames, List rowList)
  {
    this.columnNames = columnNames;
    this.propNames = propNames;
    this.rowList = rowList;
  }

  public int getColumnCount()
  {
    return this.columnNames.length;
  }

  public int getRowCount()
  {
    return this.rowList.size();
  }

  public String getColumnName(int column) {
    return this.columnNames[column];
  }

  public Object getValueAt(int rowIndex, int columnIndex)
  {
    Object model = this.rowList.get(rowIndex);
    String propName = this.propNames[columnIndex];
    try {
      Field field = model.getClass().getDeclaredField(propName);
      field.setAccessible(true);
      return field.get(model);
    } catch (Exception ex) {
      ex.printStackTrace();
    }return "Illegal property name";
  }

  public boolean isCellEditable(int row, int col)
  {
    return false;
  }
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.ObjectTableModel
 * JD-Core Version:    0.6.0
 */