package cn.org.atool.fluent.mybatis.entity.generator;

import cn.org.atool.fluent.mybatis.base.impl.BaseDaoImpl;
import cn.org.atool.fluent.mybatis.base.model.FieldMapping;
import cn.org.atool.fluent.mybatis.entity.FluentEntityInfo;
import cn.org.atool.fluent.mybatis.entity.base.AbstractGenerator;
import cn.org.atool.fluent.mybatis.entity.base.ClassNames;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static cn.org.atool.fluent.mybatis.entity.base.ClassNames.*;
import static cn.org.atool.fluent.mybatis.entity.generator.MapperGenerator.getMapperName;
import static cn.org.atool.fluent.mybatis.mapper.FluentConst.Pack_BaseDao;
import static cn.org.atool.fluent.mybatis.mapper.FluentConst.Suffix_BaseDao;

public class BaseDaoGenerator extends AbstractGenerator {
    public BaseDaoGenerator(TypeElement curElement, FluentEntityInfo fluentEntityInfo) {
        super(curElement, fluentEntityInfo);
        this.packageName = fluentEntityInfo.getPackageName(Pack_BaseDao);
        this.klassName = fluentEntityInfo.getNoSuffix() + Suffix_BaseDao;
    }

    @Override
    protected void build(TypeSpec.Builder builder) {
        builder.addModifiers(Modifier.ABSTRACT)
            .superclass(this.superBaseDaoImplKlass())
            .addSuperinterface(this.superMappingClass());
        for (String daoInterface : fluent.getDaoInterfaces()) {
            this.addInterface(builder, daoInterface);
        }
        builder.addField(this.f_mapper())
            .addMethod(this.m_mapper())
            .addMethod(this.m_query())
            .addMethod(this.m_updater())
            .addMethod(this.m_primaryField());
    }

    private void addInterface(TypeSpec.Builder builder, String daoInterface) {
        int dot = daoInterface.lastIndexOf('.');
        String packageName = "";
        String simpleClassName = daoInterface;
        if (dot > 0) {
            packageName = daoInterface.substring(0, dot);
            simpleClassName = daoInterface.substring(dot + 1);
        }
        builder.addSuperinterface(parameterizedType(
            ClassName.get(packageName, simpleClassName),
            fluent.className(),
            query(fluent),
            updater(fluent)
        ));
    }

    private TypeName superMappingClass() {
        return ClassName.get(MappingGenerator.getPackageName(fluent), MappingGenerator.getClassName(fluent));
    }

    private TypeName superBaseDaoImplKlass() {
        ClassName baseImpl = ClassName.get(BaseDaoImpl.class.getPackage().getName(), BaseDaoImpl.class.getSimpleName());
        ClassName entity = fluent.className();
        return ParameterizedTypeName.get(baseImpl, entity);
    }

    /**
     * protected EntityMapper mapper;
     *
     * @return
     */
    private FieldSpec f_mapper() {
        return FieldSpec.builder(ClassNames.mapper(fluent), "mapper")
            .addModifiers(Modifier.PROTECTED)
            .addAnnotation(ClassNames.CN_Autowired)
            .addAnnotation(AnnotationSpec.builder(ClassNames.CN_Qualifier)
                .addMember("value", "$S", getMapperName(fluent)).build()
            )
            .build();
    }

    /**
     * public AddressMapper mapper() {}
     *
     * @return
     */
    private MethodSpec m_mapper() {
        return super.publicMethod("mapper", true, mapper(fluent))
            .addStatement(super.codeBlock("return mapper"))
            .build();
    }

    /**
     * public EntityQuery query() {}
     *
     * @return
     */
    private MethodSpec m_query() {
        return super.publicMethod("query", true, query(fluent))
            .addStatement("return new $T()", query(fluent))
            .build();
    }

    /**
     * public AddressUpdate updater() {}
     *
     * @return
     */
    private MethodSpec m_updater() {
        return super.publicMethod("updater", true, updater(fluent))
            .addStatement("return new $T()", updater(fluent))
            .build();
    }

    /**
     * public String findPkColumn() {}
     *
     * @return
     */
    private MethodSpec m_primaryField() {
        MethodSpec.Builder builder = super.publicMethod("primaryField", false, FieldMapping.class)
            .addJavadoc("返回实体类主键值");
        if (fluent.getPrimary() == null) {
            super.throwPrimaryNoFound(builder);
        } else {
            builder.addStatement("return $T.$L", mapping(fluent), fluent.getPrimary().getProperty());
        }
        return builder.build();
    }

    @Override
    protected boolean isInterface() {
        return false;
    }
}