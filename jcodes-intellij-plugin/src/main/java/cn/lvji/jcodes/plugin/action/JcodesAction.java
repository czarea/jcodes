package cn.lvji.jcodes.plugin.action;

import cn.lvji.jcodes.plugin.ui.ProducerDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * idea action
 *
 * @author zhouzx
 */
public class JcodesAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        ProducerDialog dialog = new ProducerDialog(event.getProject());
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        dialog.setSize(800, 400);
    }
}
