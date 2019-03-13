import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

public class ParentRecursionAction extends AnAction {
    public ParentRecursionAction() {
        super("'Rents");
    }

    public String parentRecur(AnActionEvent e){
        // Get the offset in the current editor
        DataContext dataContext = e.getDataContext();
        final Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        final Document document = editor.getDocument();
        final int offset = editor.getCaretModel().getOffset();
        // Get the psi element at that offset
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        PsiElement tempElement = psiFile.findElementAt(offset);

        String blah = "Looking at element: " + tempElement.toString() + "\n";

        PsiElement Recur1 = tempElement;
        PsiElement Recur2 = tempElement.getParent();
        int saveme = 1;

        // Make a list of the parents and work your way up parents, then return it. Hit 20 parents at most.
        while((!Recur1.isEquivalentTo(Recur2)) || saveme < 21){
            try {
                blah += "Parent number " + saveme + ": " + Recur2.toString() + "\n";
            }
            catch(Exception ex){
                break;
            }

            Recur1 = Recur2;
            Recur2 = Recur2.getParent();

            saveme++;
        }

        return blah;
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Messages.showMessageDialog(project, parentRecur(event), "Parents up the tree:", Messages.getWarningIcon());
    }
}