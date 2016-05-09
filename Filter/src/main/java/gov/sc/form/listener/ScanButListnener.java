package gov.sc.form.listener;

import gov.sc.form.Form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ScanButListnener implements ActionListener {

	private Form form;

	/**
	 * @param form
	 */
	public ScanButListnener(Form form) {
		this.form = form;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileFilter() {

			public String getDescription() {
				return "Excel文件(*.xls/xlsx)";
			}

			@Override
			public boolean accept(java.io.File f) {
				// TODO Auto-generated method stub
				if (f.getName().endsWith(".xls")) {
					return true;
				}
				if (f.getName().endsWith(".xlsx")) {
					return true;
				}
				return false;
			}
		});
		if (chooser.showDialog(null, "选择") == JFileChooser.CANCEL_OPTION) {
			return;
		}

		form.srcPthTxtFiled.setText(chooser.getSelectedFile().toString());
	}

}
