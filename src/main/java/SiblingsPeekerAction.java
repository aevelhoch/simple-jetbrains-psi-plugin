import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class SiblingsPeekerAction extends AnAction {
    public SiblingsPeekerAction() {
        super("Peek at siblings");
    }

    public String siblingsPeek(AnActionEvent e){
        // Get the offset in the current editor
        DataContext dataContext = e.getDataContext();
        final Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        final Document document = editor.getDocument();
        final int offset = editor.getCaretModel().getOffset();
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        PsiElement tempElement = psiFile.findElementAt(offset);

        String ourElement = "Element selected = " + tempElement.toString() + "\n";
        String olderSibling = "";
        String oldererSibling = "";
        String youngerSibling = "";
        String youngererSibling = "";
        PsiElement sib;

        try {
            sib = tempElement.getPrevSibling();
            olderSibling = "Prev Sibling = " + sib.toString() + "\n";
            try {
                sib = sib.getPrevSibling();
                oldererSibling = "Prev x2 sibling = " + sib.toString() + "\n";
            }
            catch(Exception ex) {
                oldererSibling = "Couldn't get prev x2 sibling.\n";
            }
        }
        catch(Exception ex){
            olderSibling = "Couldn't get prev sibling.\n";
        }


        try {
            sib = tempElement.getNextSibling();
            youngerSibling = "Next Sibling = " + sib.toString() + "\n";
            try {
                sib = sib.getNextSibling();
                youngererSibling = "Next x2 sibling = " + sib.toString() + "\n";
            }
            catch(Exception ex) {
                youngererSibling = "Couldn't get next x2 sibling.\n";
            }
        }
        catch(Exception ex){
            youngerSibling = "Couldn't get next sibling.\n";
        }

        return youngererSibling + youngerSibling + ourElement + olderSibling + oldererSibling;
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Messages.showMessageDialog(project, siblingsPeek(event), "Peeking at siblings", Messages.getInformationIcon());
    }
}