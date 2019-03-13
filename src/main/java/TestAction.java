import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import java.lang.String;

import static com.intellij.psi.JavaTokenType.LPARENTH;

public class TestAction extends AnAction {
    public TestAction() {
        super("Test");
    }

    private String testReturnThing(AnActionEvent e){
        // Get the offset in the current editor
        DataContext dataContext = e.getDataContext();
        final Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        final Document document = editor.getDocument();
        final int offset = editor.getCaretModel().getOffset();
        // Then go into the file and get the element at that offset
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        PsiElement tempElement = psiFile.findElementAt(offset);

        String foo = tempElement.toString();
        String bar = "PSI element at current cursor location is " + foo + "\n";
        PsiElement elementParent = tempElement.getParent();
        foo = elementParent.toString();
        bar = bar + "Current elements parent is: " + foo + "\n";

        PsiElement prevLeaf = PsiTreeUtil.prevLeaf(tempElement);
        foo = prevLeaf.toString();
        bar = bar + "Current elements prev leaf is: " + foo + "\n";

        PsiElement nextLeaf = PsiTreeUtil.nextLeaf(tempElement);
        foo = nextLeaf.toString();
        bar = bar + "Current elements next leaf is: " + foo + "\n";

        return bar;
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Messages.showErrorDialog(project, testReturnThing(event), "Test");
    }
}