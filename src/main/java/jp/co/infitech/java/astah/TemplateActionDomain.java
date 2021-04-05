package jp.co.infitech.java.astah;


import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class TemplateActionDomain implements IPluginActionDelegate {

    public Object run(IWindow window) throws UnExpectedException {
        try {
            CommonAction commonAction = new CommonAction();
            commonAction.action(window, true);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(window.getParent(), e.toString(), "Alert", JOptionPane.ERROR_MESSAGE);
            throw new UnExpectedException();
        }
        return null;
    }

}
