import com.intellij.ide.IdeBundle;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class ChangeSomethingAction extends AnAction {
    public ChangeSomethingAction() {
        super("Trying to change a thing");
    }

    public String getElementAtCaret(AnActionEvent e){
        try {
            return "Currently pointing to " + e.getData(LangDataKeys.PSI_ELEMENT).toString();
        }
        catch(Exception Ex){
            return "PSI element at caret is returning a null pointer?";
        }
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        DataContext dataContext = event.getDataContext();
        final Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        final int offset = editor.getCaretModel().getOffset();
        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
        CommandProcessor processor = CommandProcessor.getInstance();
        processor.executeCommand(CommonDataKeys.PROJECT.getData(dataContext), new Runnable() {
            public void run() {
                try {
                    psiFile.findElementAt(offset).delete();
                }
                catch(Exception Ex){

                }
            } // This gives me some sort of error and I don't know why.
        }, null, null);
        Messages.showMessageDialog(project, getElementAtCaret(event), "Tryin to make a change", Messages.getInformationIcon());
    }
}