package org.squirrelsql.table.tableexport;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class ExportResultsView
{
	@FXML CheckBox checkExecuteCommand;
	@FXML TextField commandToExecute;
	@FXML Button browseCommand;
	@FXML Label lblExportTo;
	@FXML TextField exportTo;
	@FXML Button browseExportDir;
	@FXML Button export;
	@FXML Button exportCancel;
	@FXML Label lblFileName;
	@FXML TextField fileName;
	@FXML Label lblExportAs;
	@FXML RadioButton excelXLSX;
	@FXML RadioButton excelXLS;
	@FXML ProgressIndicator exportProgressIndicator;
}
