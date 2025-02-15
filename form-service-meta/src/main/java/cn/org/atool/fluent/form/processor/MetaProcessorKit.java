package cn.org.atool.fluent.form.processor;

import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;

@SuppressWarnings({"unchecked", "unused", "rawtypes"})
class MetaProcessorKit {
    public static void generate(Element element, Filer filer) throws IOException {
        FormScanner scanner = new FormScanner();
        scanner.scan(element);
        JavaFile javaFile = new FormMetaFiler(scanner.getClassName(), scanner.getMetas()).javaFile();
        javaFile.writeTo(filer);
        // compile(javaFile.toJavaFileObject().toUri().getPath());
    }

    /**
     * 编译文件
     *
     * @param path 文件路径
     */
    private static void compile(String path) throws IOException {
        //拿到编译器
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager mgr = jc.getStandardFileManager(null, null, null)) {
            //获取文件
            Iterable units = mgr.getJavaFileObjects(path);
            //编译任务
            JavaCompiler.CompilationTask t = jc.getTask(null, mgr, null, null, null, units);
            //进行编译
            t.call();
        }
    }
}
