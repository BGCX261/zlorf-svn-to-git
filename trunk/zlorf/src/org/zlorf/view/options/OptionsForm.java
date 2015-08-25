package org.zlorf.view.options;

import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zlorf.controller.InvalidOptionException;
import org.zlorf.controller.OptionLevel;
import org.zlorf.controller.Options_OLD;

/**
 *
 * @author Mat Jaggard
 */
public class OptionsForm extends JFrame
{

	private static final Logger LOG = LoggerFactory.getLogger(OptionsForm.class);

	/** Creates new form OptionsForm */
	public OptionsForm()
	{
		initComponents();
		screenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		screenTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		screenTable.setFillsViewportHeight(true);
		LOG.debug(screenTable.getColumnName(3));
		try
		{
			numberOfPreviewScreensSlider.setValue(Options_OLD.getBestOptionInt(Options_OLD.SCREEN_OPTIONS, Options_OLD.NUMBER_OF_PREVIEW_SCREENS));
		}
		catch (InvalidOptionException ioe)
		{
			LOG.error("Unable to set Number of Preview Screens in options dialog", ioe);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        optionsTabbedFrame = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        systemTab = new javax.swing.JPanel();
        screenSelectionArea = new javax.swing.JPanel();
        screenSelectionLabel = new javax.swing.JLabel();
        screenOptions = new javax.swing.JPanel();
        numberOfPreviewScreensLabel = new javax.swing.JLabel();
        numberOfPreviewScreensSlider = new javax.swing.JSlider();
        numberOfPreviewScreensValue = new javax.swing.JLabel();
        screenScrollPane = new javax.swing.JScrollPane();
        screenTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(400, 300));

        optionsTabbedFrame.addTab("tab1", jPanel1);

        systemTab.setLayout(new java.awt.GridLayout(1, 1));

        screenSelectionArea.setLayout(new java.awt.BorderLayout());

        screenSelectionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        screenSelectionLabel.setText("Screen Selection"); // NOI18N
        screenSelectionArea.add(screenSelectionLabel, java.awt.BorderLayout.NORTH);

        numberOfPreviewScreensLabel.setText("Number Of Preview Screens");
        screenOptions.add(numberOfPreviewScreensLabel);

        numberOfPreviewScreensSlider.setMaximum(5);
        numberOfPreviewScreensSlider.setValue(0);
        screenOptions.add(numberOfPreviewScreensSlider);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, numberOfPreviewScreensSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), numberOfPreviewScreensValue, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        screenOptions.add(numberOfPreviewScreensValue);

        screenSelectionArea.add(screenOptions, java.awt.BorderLayout.SOUTH);

        screenScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        screenScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        screenScrollPane.setName(""); // NOI18N

        screenTable.setModel(new ScreenTableModel());
        screenScrollPane.setViewportView(screenTable);

        screenSelectionArea.add(screenScrollPane, java.awt.BorderLayout.CENTER);

        systemTab.add(screenSelectionArea);

        optionsTabbedFrame.addTab("System", systemTab);
        optionsTabbedFrame.addTab("tab3", jPanel3);

        getContentPane().add(optionsTabbedFrame, java.awt.BorderLayout.CENTER);

        okButton.setText("OK"); // NOI18N
        okButton.addActionListener(formListener);
        buttonPanel.add(okButton);

        cancelButton.setText("Cancel"); // NOI18N
        cancelButton.addActionListener(formListener);
        buttonPanel.add(cancelButton);

        applyButton.setText("Apply"); // NOI18N
        applyButton.addActionListener(formListener);
        buttonPanel.add(applyButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        bindingGroup.bind();

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == okButton) {
                OptionsForm.this.okButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                OptionsForm.this.cancelButtonActionPerformed(evt);
            }
            else if (evt.getSource() == applyButton) {
                OptionsForm.this.applyButtonActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

	private void cancelButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
	{//GEN-HEADEREND:event_cancelButtonActionPerformed
		dispose();
	}//GEN-LAST:event_cancelButtonActionPerformed

	private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
		// TODO Save all the options
		LOG.warn("Some options should have been saved. Some saving may not be implemented yet.");
		TableModel tm = screenTable.getModel();
		if (tm instanceof ScreenTableModel)
		{
			((ScreenTableModel) tm).saveOptions();
		}
		else
		{
			LOG.error("For some strange reason, the table model attached to the screen table on the options dialog is not a ScreenTableModel.");
		}
		int numPreviewScreens = numberOfPreviewScreensSlider.getValue();
		Options_OLD.setOption(Options_OLD.SCREEN_OPTIONS, Options_OLD.NUMBER_OF_PREVIEW_SCREENS, OptionLevel.SYSTEM, Integer.toString(numPreviewScreens));
	}//GEN-LAST:event_applyButtonActionPerformed

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		applyButtonActionPerformed(evt);
		dispose();
	}//GEN-LAST:event_okButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		java.awt.EventQueue.invokeLater(new Runnable()
		{

			public void run()
			{
				new OptionsForm().setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel numberOfPreviewScreensLabel;
    private javax.swing.JSlider numberOfPreviewScreensSlider;
    private javax.swing.JLabel numberOfPreviewScreensValue;
    private javax.swing.JButton okButton;
    private javax.swing.JTabbedPane optionsTabbedFrame;
    private javax.swing.JPanel screenOptions;
    private javax.swing.JScrollPane screenScrollPane;
    private javax.swing.JPanel screenSelectionArea;
    private javax.swing.JLabel screenSelectionLabel;
    private javax.swing.JTable screenTable;
    private javax.swing.JPanel systemTab;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
