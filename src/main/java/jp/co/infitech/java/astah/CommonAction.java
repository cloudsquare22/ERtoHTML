package jp.co.infitech.java.astah;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IERDomain;
import com.change_vision.jude.api.inf.model.IEREntity;
import com.change_vision.jude.api.inf.model.IERModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate.UnExpectedException;
import com.change_vision.jude.api.inf.ui.IWindow;

public class CommonAction {

    public void action(IWindow window, boolean domain) throws UnExpectedException {
        try {
            AstahAPI astahAPI = AstahAPI.getAstahAPI();
            ProjectAccessor projectAccessor = astahAPI.getProjectAccessor();
            INamedElement[] erentityList= projectAccessor.findElements(IEREntity.class);
            INamedElement[] ermodel= projectAccessor.findElements(IERModel.class);
            INamedElement[] domainList = null;
            if(domain == true) {
                domainList = projectAccessor.findElements(IERDomain.class);
            }
            if(erentityList.length != 0) {
                String sourceTopFolder = null;
                JFileChooser jFilechooser = new JFileChooser(sourceTopFolder);
                jFilechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFilechooser.setDialogTitle(HtmlWord.getWord("dialog_title"));
                jFilechooser.setApproveButtonText(HtmlWord.getWord("select_button"));
                int selected = jFilechooser.showOpenDialog(null);
                if(selected == JFileChooser.APPROVE_OPTION) {
                    File outputDirectory = jFilechooser.getSelectedFile();
                    TableToHTML.makeHtml(erentityList, (IERModel)ermodel[0], outputDirectory, domain);
                    if(domain == true) {
                        DomainToHTML.makeHtml(domainList, outputDirectory);
                    }
                    for(INamedElement ine : erentityList) {
                        IEREntity entity= (IEREntity)ine;
                        EntityToHTML.makeHtml(entity, outputDirectory, domain);
                    }
                    JOptionPane.showMessageDialog(window.getParent(), HtmlWord.getWord("message_01"), "Message", JOptionPane.PLAIN_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(window.getParent(), HtmlWord.getWord("message_02"), "Alert", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(ProjectNotFoundException e) {
            JOptionPane.showMessageDialog(window.getParent(), HtmlWord.getWord("message_03"), "Alert", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(window.getParent(), e.toString(), "Alert", JOptionPane.ERROR_MESSAGE);
            throw new UnExpectedException();
        }
    }
}
