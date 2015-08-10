package anno;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes(value={"anno.PP"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class PPProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public void init(ProcessingEnvironment env){
        filer = env.getFiler();
    }

    private String[] toList(String message) {
	return message.split(",");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment env) {

	String folder = "ppgen";
	for (Element element : env.getElementsAnnotatedWith(PP.class)) {
	    // Initialization.
	    TypeMirror tm = element.asType();
	    String typeArgs = tm.accept(new DeclaredTypeVisitor(), element);
	    String[] lTypeArgs = toList(typeArgs);

	    String name = element.getSimpleName().toString();
	    String res = createPPClass(folder, (TypeElement) element,
		    lTypeArgs, typeArgs);

	    try {
		JavaFileObject jfo;
		jfo = filer.createSourceFile(folder + "/" + nameGenPP(name),
			element);
		jfo.openWriter().append(res).close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

        return true;
    }

    private String nameGenPP(String name) {
	return "PP" + name;
    }

    private String getName(Element e) {
	return e.getSimpleName().toString();
    }

    private String createPPClass(String folder, TypeElement te,
	    String[] lTypeArgs, String typeArgs) {
	String name = getName(te);
	String res = "package " + folder + ";\n\n";
	res += "import " + getPackage(te) + "." + name + ";\n\n";
	res += "public class " + nameGenPP(name) + " implements " + name
		+ "<String> {\n";

	List<? extends Element> le = te.getEnclosedElements();
	for (Element e : le) {
	    String methodName = e.getSimpleName().toString();
	    String[] args = { methodName, typeArgs, name };
	    res += e.asType().accept(new PrintMethodVisitor(), args);
	}

	res += "}";
	return res;
    }

    @Override
    public SourceVersion getSupportedSourceVersion(){
        return SourceVersion.latestSupported();
    }

    private String getPackage(Element element) {
        return ((PackageElement)element.getEnclosingElement()).getQualifiedName().toString();
    }

}

