package cn.czarea.jcodes.plugin.action;

import cn.czarea.jcodes.plugin.ui.ProducerDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

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
        dialog.setSize(800, 400);
    }
}
