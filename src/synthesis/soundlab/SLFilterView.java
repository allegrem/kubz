/**
 * 
 */
package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * @author allegrem
 *
 */
public class SLFilterView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public SLFilterView() {
		super();
		setLayout(new BorderLayout(0, 0));
		
		createToolbar();
	}

	private void createToolbar() {
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		add(toolBar_2, BorderLayout.NORTH);

		Component horizontalGlue = Box.createHorizontalGlue();
		toolBar_2.add(horizontalGlue);

		JLabel lblSignalView = new JLabel("Filter");
		toolBar_2.add(lblSignalView);
	}

}
