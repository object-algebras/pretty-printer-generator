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


    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment env) {
	String folder = "pp";
	for (Element element : env.getElementsAnnotatedWith(PP.class)) {
	    String name = element.getSimpleName().toString();
	    String res = createPPClass(folder, element);
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

    private String createPPClass(String folder, Element e) {
	String name = getName(e);
	String res = "package " + folder + ";\n\n";
	res += "import " + getPackage(e) + "." + name + ";\n\n";
	res += "public class " + nameGenPP(name) + " implements " + name
		+ "<String> {\n";
	// TODO: generate PP implementation methods.

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

