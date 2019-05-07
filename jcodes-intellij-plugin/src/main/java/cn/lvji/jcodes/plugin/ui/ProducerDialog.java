package cn.lvji.jcodes.plugin.ui;

import cn.lvji.jcodes.Producer;
import cn.lvji.jcodes.util.StringUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * 启动窗口
 *
 * @author zhouzx
 */
public class ProducerDialog extends JDialog {

	private static final Logger log = Logger.getInstance(ProducerDialog.class);

	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JRadioButton projectRadio;
	private JRadioButton codesRadio;
	private JTextField configFileTextField;
	private JButton chooseFileButton;

	public ProducerDialog(Project project) {
		setTitle("jcodes");
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		ButtonGroup bg = new ButtonGroup();
		bg.add(projectRadio);
		bg.add(codesRadio);
		buttonOK.addActionListener(e -> onOK(project));
		buttonCancel.addActionListener(e -> onCancel());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		contentPane.registerKeyboardAction(e -> onCancel(),
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		chooseFileButton.addActionListener(e -> addChooseListener(project));
		this.setLocationRelativeTo(null);
	}

	private void onOK(Project project) {
		String config = configFileTextField.getText();
		if (StringUtils.isEmpty(config)) {
			Messages.showErrorDialog("请选择配置文件!", "Error!");
			return;
		}
		Producer producer = new Producer();
		if (projectRadio.isSelected()) {
			try {
				producer.produceProject(config);
				Messages.showInfoMessage(project, "成功创建工程!", "Success!");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				long start = System.currentTimeMillis();
				producer.produceCodes(config);
				Messages.showInfoMessage(
						"成功生成代码! 耗时：" + (System.currentTimeMillis() - start)
								+ "ms", "Success!");
				log.info("成功生成代码! 耗时：" + (System.currentTimeMillis() - start)
						+ "ms");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void onCancel() {
		dispose();
	}

	public void setConfig(String config) {
		configFileTextField.setText(config);
	}

	public void addChooseListener(Project project) {
		FileChooserDescriptor descriptor = new FileChooserDescriptor(true,
				false, false, false, false, false);
		VirtualFile[] selectedFiles = FileChooser
				.chooseFiles(descriptor, project, null);
		if (selectedFiles.length == 0) {
			return;
		}
		String config = selectedFiles[0].getPath();
		setConfig(config);
	}

}
