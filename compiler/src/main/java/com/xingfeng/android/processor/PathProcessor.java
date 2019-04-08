package com.xingfeng.android.processor;

import com.squareup.javapoet.*;
import com.xingfeng.android.annotation.Path;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 王立
 * @Date: 2019/3/25 19:44
 * @Desc:
 */
public class PathProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.size() == 0) {
            return false;
        }

        MethodSpec.Builder builder = MethodSpec.methodBuilder("getUrlRouterMap")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
                        ParameterizedTypeName.get(ClassName.get(Class.class),
                                WildcardTypeName.subtypeOf(ClassName.get("android.app", "Activity")))))
                .addStatement("Map<String,Class<? extends Activity>> urlMaps=new $T<>()", HashMap.class);
        //遍历每个@Path注解，将内容添加到Map中
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Path.class)) {
            //收集信息
            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            Path path = typeElement.getAnnotation(Path.class);
            String url = path.value();
            builder.addStatement("urlMaps.put($S,$T.class)", url, typeElement.asType());

        }

        builder.addStatement("return urlMaps");
        TypeSpec typeSpec = TypeSpec.classBuilder("UrlCollectorImpl")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ClassName.get("com.xingfeng.android.api", "UrlCollector"))
                .addMethod(builder.build())
                .build();

        JavaFile javaFile = JavaFile.builder("com.xingfeng.android.api", typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Path.class.getCanonicalName());
    }
}
