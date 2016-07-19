package com.wf2311.commons.persist.mybatis.plugins;

import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @ProjectName: sns
 *  @Description: mybatis 代码自动生成插件
 */
public class MybatisPlugin extends PluginAdapter {

    public static void generate(String configFile) {

        String config = MybatisPlugin.class.getClassLoader().getResource(
                configFile).getFile();
        String[] arg = {"-configfile", config, "-overwrite"};
        ShellRunner.main(arg);

    }

    public static void main(String[] args) {
        generate("generatorConfig.xml");
    }


    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();

        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            XmlElement xmlElement = new XmlElement("result");
            Attribute attributeColumn = new Attribute("column", "max_" + introspectedColumn.getActualColumnName());
            Attribute attributeProPerty = new Attribute("property", "max" + StringUtils.capitalize(introspectedColumn.getJavaProperty()));
            Attribute attributeJdbcType = new Attribute("jdbcType", "BIGINT");
            xmlElement.addAttribute(attributeColumn);
            xmlElement.addAttribute(attributeProPerty);
            xmlElement.addAttribute(attributeJdbcType);

            XmlElement xmlElementMinId = new XmlElement("result");
            Attribute attributeColumnMinId = new Attribute("column", "min_" + introspectedColumn.getActualColumnName());
            Attribute attributeProPertyMinId = new Attribute("property", "min" + StringUtils.capitalize(introspectedColumn.getJavaProperty()));
            Attribute attributeJdbcTypeMinId = new Attribute("jdbcType", "BIGINT");
            xmlElementMinId.addAttribute(attributeColumnMinId);
            xmlElementMinId.addAttribute(attributeProPertyMinId);
            xmlElementMinId.addAttribute(attributeJdbcTypeMinId);

            XmlElement xmlElementAvgId = new XmlElement("result");
            Attribute attributeColumnAvgId = new Attribute("column", "avg_" + introspectedColumn.getActualColumnName());
            Attribute attributeProPertyAvgId = new Attribute("property", "avg" + StringUtils.capitalize(introspectedColumn.getJavaProperty()));
            Attribute attributeJdbcTypeAvgId = new Attribute("jdbcType", "BIGINT");
            xmlElementAvgId.addAttribute(attributeColumnAvgId);
            xmlElementAvgId.addAttribute(attributeProPertyAvgId);
            xmlElementAvgId.addAttribute(attributeJdbcTypeAvgId);

            XmlElement xmlElementCountId = new XmlElement("result");
            Attribute attributeColumnCountId = new Attribute("column", "count_" + introspectedColumn.getActualColumnName());
            Attribute attributeProPertyCountId = new Attribute("property", "count" + StringUtils.capitalize(introspectedColumn.getJavaProperty()));
            Attribute attributeJdbcTypeCountId = new Attribute("jdbcType", "BIGINT");
            xmlElementCountId.addAttribute(attributeColumnCountId);
            xmlElementCountId.addAttribute(attributeProPertyCountId);
            xmlElementCountId.addAttribute(attributeJdbcTypeCountId);

            XmlElement xmlElementCountDistinctId = new XmlElement("result");
            Attribute attributeColumnCountDistinctId = new Attribute("column", "count_distinct_" + introspectedColumn.getActualColumnName());
            Attribute attributeProPertyCountDistintId = new Attribute("property", "countDistinct" + StringUtils.capitalize(introspectedColumn.getJavaProperty()));
            Attribute attributeJdbcTypeCountDistinctId = new Attribute("jdbcType", "BIGINT");
            xmlElementCountDistinctId.addAttribute(attributeColumnCountDistinctId);
            xmlElementCountDistinctId.addAttribute(attributeProPertyCountDistintId);
            xmlElementCountDistinctId.addAttribute(attributeJdbcTypeCountDistinctId);

            XmlElement xmlElementSumId = new XmlElement("result");
            Attribute attributeColumnSumId = new Attribute("column", "sum_" + introspectedColumn.getActualColumnName());
            Attribute attributeProPertySumId = new Attribute("property", "sum" + StringUtils.capitalize(introspectedColumn.getJavaProperty()));
            Attribute attributeJdbcTypeSumId = new Attribute("jdbcType", introspectedColumn.getJdbcTypeName());
            xmlElementSumId.addAttribute(attributeColumnSumId);
            xmlElementSumId.addAttribute(attributeProPertySumId);
            xmlElementSumId.addAttribute(attributeJdbcTypeSumId);


            element.getElements().add(1, xmlElement);
            element.getElements().add(2, xmlElementMinId);
            element.getElements().add(3, xmlElementAvgId);
            element.getElements().add(4, xmlElementCountId);
            element.getElements().add(5, xmlElementSumId);
            element.getElements().add(6, xmlElementCountDistinctId);
        }

        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        /**
         * mybatis自动生成mapper的时候是追加操作,但是我们平常都是删除重新生成,
         * 所以需要扩展删除原来的mapper让mybatis再生成一个
         **/
        List<GeneratedXmlFile> generatedXmlFiles = introspectedTable.getGeneratedXmlFiles();
        for (GeneratedFile generatedFile : generatedXmlFiles) {
            generatedFile.getFormattedContent();

            File project = new File(generatedFile.getTargetProject() + generatedFile.getTargetPackage());
            File file = new File(project, generatedFile.getFileName());
            if (file.exists()) {
                file.delete();
            }
        }
        addDomainExampleClassFields(topLevelClass, introspectedTable);

        return super.modelExampleClassGenerated(topLevelClass,
                introspectedTable);
    }

    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        Attribute attribute1 = new Attribute("useGeneratedKeys", "true");
        Attribute attribute2 = new Attribute("keyProperty", "id");
        element.addAttribute(attribute1);
        element.addAttribute(attribute2);
        return true;
    }

    public boolean sqlMapInsertElementGenerated(XmlElement element,
                                                IntrospectedTable introspectedTable) {
        Attribute attribute1 = new Attribute("useGeneratedKeys", "true");
        Attribute attribute2 = new Attribute("keyProperty", "id");
        element.addAttribute(attribute1);
        element.addAttribute(attribute2);
        return true;
    }

    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        return true;
    }

    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        method.getParameters().get(0).addAnnotation("@Param(\"record\")");
        return super.clientUpdateByPrimaryKeySelectiveMethodGenerated(method,
                interfaze, introspectedTable);
    }

    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        generateSqlMapDelete(element, introspectedTable);
        return super.sqlMapDeleteByExampleElementGenerated(element, introspectedTable);
    }

    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        generateSqlMapDelete(element, introspectedTable);
        return super.sqlMapDeleteByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    private void generateSqlMapDelete(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().remove(0);
//        element.getElements().add(0, new TextElement("update " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
//        element.getElements().add(1, new TextElement("set IS_DELETED = '1'"));
        element.getElements().add(0, new TextElement("delete from " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));

    }

    /**
     * 在ExampleClass中追加属性
     *
     * @param topLevelClass
     */
    private void addDomainExampleClassFields(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        /**
         * 增加forUpdate属性
         **/
        Field fieldNew = new Field();
        fieldNew.setName("forUpdate");
        fieldNew.setVisibility(JavaVisibility.PROTECTED);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType("boolean");
        fieldNew.setType(type);
        fieldNew.addJavaDocLine("/** 在查询中增加for update ");
        fieldNew.addJavaDocLine(" * 目前用来支持对查询记录加锁");
        fieldNew.addJavaDocLine(" **/");
        topLevelClass.addField(fieldNew);

        /**
         * 增加setForUpdate方法
         **/
        Method setMethod = new Method();
        setMethod.setName("setForUpdate");
        setMethod.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("void");
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("boolean");
        Parameter parameter = new Parameter(parameterType, "forUpdate");
        setMethod.addParameter(parameter);
        setMethod.addBodyLine("this.forUpdate = forUpdate;");
        setMethod.setReturnType(returnType);


        /**
         * 增加isForUpdate方法
         **/
        Method isMethod = new Method();
        isMethod.setName("isForUpdate");
        isMethod.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType isMethodReturnType = new FullyQualifiedJavaType("boolean");
        isMethod.setReturnType(isMethodReturnType);
        isMethod.addBodyLine("return forUpdate;");


        Field definedQueryColumns = new Field();
        definedQueryColumns.setName("definedQueryColumns");
        definedQueryColumns.setVisibility(JavaVisibility.PROTECTED);
        FullyQualifiedJavaType definedQueryColumnsType = new FullyQualifiedJavaType("boolean");
        definedQueryColumns.setType(definedQueryColumnsType);
        definedQueryColumns.addJavaDocLine("/** 是否自定义设置查询字段 */");
        topLevelClass.addField(definedQueryColumns);

        Method setDefinedQueryColumnsMethod = new Method();
        setDefinedQueryColumnsMethod.setName("setDefinedQueryColumns");
        setDefinedQueryColumnsMethod.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType setDefinedQueryColumnsReturnType = new FullyQualifiedJavaType("void");
        FullyQualifiedJavaType setDefinedQueryColumnsParameterType = new FullyQualifiedJavaType("boolean");
        Parameter setDefinedQueryColumnsParameter = new Parameter(setDefinedQueryColumnsParameterType, "definedQueryColumns");
        setDefinedQueryColumnsMethod.addParameter(setDefinedQueryColumnsParameter);
        setDefinedQueryColumnsMethod.addBodyLine("this.definedQueryColumns = definedQueryColumns;");
        setDefinedQueryColumnsMethod.setReturnType(setDefinedQueryColumnsReturnType);

        Method isDefinedQueryColumnsMethod = new Method();
        isDefinedQueryColumnsMethod.setName("isDefinedQueryColumns");
        isDefinedQueryColumnsMethod.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType isDefinedQueryColumnsMethodReturnType = new FullyQualifiedJavaType("boolean");
        isDefinedQueryColumnsMethod.setReturnType(isDefinedQueryColumnsMethodReturnType);
        isDefinedQueryColumnsMethod.addBodyLine("return definedQueryColumns;");

        Field queryColumns = new Field();
        queryColumns.setName("queryColumns");
        queryColumns.setVisibility(JavaVisibility.PROTECTED);
        FullyQualifiedJavaType queryColumnsType = new FullyQualifiedJavaType("List<String>");
        queryColumns.setType(queryColumnsType);
        queryColumns.addJavaDocLine("/** 设置查询字段 */");
        topLevelClass.addField(queryColumns);
        topLevelClass.addMethod(setMethod);
        topLevelClass.addMethod(isMethod);

        topLevelClass.addMethod(setDefinedQueryColumnsMethod);
        topLevelClass.addMethod(isDefinedQueryColumnsMethod);

        List<IntrospectedColumn> baseColumns = introspectedTable.getAllColumns();

        if (!CollectionUtils.isEmpty(baseColumns)) {
            for (IntrospectedColumn baseColumn : baseColumns) {

                Method addQueryColumnsMethod = new Method();
                addQueryColumnsMethod.setName("add" + StringUtils.capitalize(baseColumn.getJavaProperty()) + "QueryColumn");
                addQueryColumnsMethod.setVisibility(JavaVisibility.PUBLIC);
                FullyQualifiedJavaType setQueryColumnsReturnType = new FullyQualifiedJavaType("void");
                //addQueryColumnsMethod.addBodyLine("if(this.queryColumns == null || this.queryColumns.isEmpty()){");
                //addQueryColumnsMethod.addBodyLine("throw new RuntimeException(\"Value for queryColumns cannot be null\");");
                //addQueryColumnsMethod.addBodyLine("}");
                addQueryColumnsMethod.addBodyLine("this.queryColumns.add(\"" + baseColumn.getActualColumnName() + "\");");
                addQueryColumnsMethod.setReturnType(setQueryColumnsReturnType);
                topLevelClass.addMethod(addQueryColumnsMethod);

                Method addQueryColumnsMaxMethod = new Method();
                addQueryColumnsMaxMethod.setName("addMax" + StringUtils.capitalize(baseColumn.getJavaProperty()) + "QueryColumn");
                addQueryColumnsMaxMethod.setVisibility(JavaVisibility.PUBLIC);
                FullyQualifiedJavaType setMaxQueryColumnsReturnType = new FullyQualifiedJavaType("void");
                //addQueryColumnsMaxMethod.addBodyLine("if(this.queryColumns == null || this.queryColumns.isEmpty()){");
                // addQueryColumnsMaxMethod.addBodyLine("throw new RuntimeException(\"Value for queryColumns cannot be null\");");
                //addQueryColumnsMaxMethod.addBodyLine("}");
                addQueryColumnsMaxMethod.addBodyLine("this.queryColumns.add(\"max(" + baseColumn.getActualColumnName() + ") max_"+baseColumn.getActualColumnName()+"\");");
                addQueryColumnsMaxMethod.setReturnType(setMaxQueryColumnsReturnType);
                topLevelClass.addMethod(addQueryColumnsMaxMethod);

                Method addQueryColumnsMinMethod = new Method();
                addQueryColumnsMinMethod.setName("addMin" + StringUtils.capitalize(baseColumn.getJavaProperty()) + "QueryColumn");
                addQueryColumnsMinMethod.setVisibility(JavaVisibility.PUBLIC);
                FullyQualifiedJavaType setMinQueryColumnsReturnType = new FullyQualifiedJavaType("void");
                //addQueryColumnsMinMethod.addBodyLine("if(this.queryColumns == null || this.queryColumns.isEmpty()){");
                //addQueryColumnsMinMethod.addBodyLine("throw new RuntimeException(\"Value for queryColumns cannot be null\");");
                //addQueryColumnsMinMethod.addBodyLine("}");
                addQueryColumnsMinMethod.addBodyLine("this.queryColumns.add(\"min(" + baseColumn.getActualColumnName() + ") min_"+baseColumn.getActualColumnName()+"\");");
                addQueryColumnsMinMethod.setReturnType(setMinQueryColumnsReturnType);
                topLevelClass.addMethod(addQueryColumnsMinMethod);

                Method addQueryColumnsAvgMethod = new Method();
                addQueryColumnsAvgMethod.setName("addAvg" + StringUtils.capitalize(baseColumn.getJavaProperty()) + "QueryColumn");
                addQueryColumnsAvgMethod.setVisibility(JavaVisibility.PUBLIC);
                FullyQualifiedJavaType setAvgQueryColumnsReturnType = new FullyQualifiedJavaType("void");
                //addQueryColumnsAvgMethod.addBodyLine("if(this.queryColumns == null || this.queryColumns.isEmpty()){");
                //addQueryColumnsAvgMethod.addBodyLine("throw new RuntimeException(\"Value for queryColumns cannot be null\");");
                //addQueryColumnsAvgMethod.addBodyLine("}");
                addQueryColumnsAvgMethod.addBodyLine("this.queryColumns.add(\" avg(" + baseColumn.getActualColumnName() + ") avg_"+baseColumn.getActualColumnName()+"\");");
                addQueryColumnsAvgMethod.setReturnType(setAvgQueryColumnsReturnType);
                topLevelClass.addMethod(addQueryColumnsAvgMethod);

                Method addQueryColumnsCountMethod = new Method();
                addQueryColumnsCountMethod.setName("addCount" + StringUtils.capitalize(baseColumn.getJavaProperty()) + "QueryColumn");
                addQueryColumnsCountMethod.setVisibility(JavaVisibility.PUBLIC);
                FullyQualifiedJavaType setCountQueryColumnsReturnType = new FullyQualifiedJavaType("void");
                //addQueryColumnsAvgMethod.addBodyLine("if(this.queryColumns == null || this.queryColumns.isEmpty()){");
                //addQueryColumnsAvgMethod.addBodyLine("throw new RuntimeException(\"Value for queryColumns cannot be null\");");
                //addQueryColumnsAvgMethod.addBodyLine("}");
                addQueryColumnsCountMethod.addBodyLine("this.queryColumns.add(\" count(" + baseColumn.getActualColumnName() + ") count_"+baseColumn.getActualColumnName()+"\");");
                addQueryColumnsCountMethod.setReturnType(setCountQueryColumnsReturnType);
                topLevelClass.addMethod(addQueryColumnsCountMethod);

                Method addQueryColumnsCountDistinctMethod = new Method();
                addQueryColumnsCountDistinctMethod.setName("addCountDistinct" + StringUtils.capitalize(baseColumn.getJavaProperty()) + "QueryColumn");
                addQueryColumnsCountDistinctMethod.setVisibility(JavaVisibility.PUBLIC);
                FullyQualifiedJavaType setCountQueryColumnsDistinctReturnType = new FullyQualifiedJavaType("void");
                addQueryColumnsCountDistinctMethod.addBodyLine("this.queryColumns.add(\" count( DISTINCT " + baseColumn.getActualColumnName() + ") count_distinct_"+baseColumn.getActualColumnName()+"\");");
                addQueryColumnsCountDistinctMethod.setReturnType(setCountQueryColumnsDistinctReturnType);
                topLevelClass.addMethod(addQueryColumnsCountDistinctMethod);


                Method addQueryColumnsSumMethod = new Method();
                addQueryColumnsSumMethod.setName("addSum" + StringUtils.capitalize(baseColumn.getJavaProperty()) + "QueryColumn");
                addQueryColumnsSumMethod.setVisibility(JavaVisibility.PUBLIC);
                FullyQualifiedJavaType setSumQueryColumnsReturnType = new FullyQualifiedJavaType("void");
                addQueryColumnsSumMethod.addBodyLine("this.queryColumns.add(\" sum(" + baseColumn.getActualColumnName() + ") sum_"+baseColumn.getActualColumnName()+"\");");
                addQueryColumnsSumMethod.setReturnType(setSumQueryColumnsReturnType);

                topLevelClass.addMethod(addQueryColumnsSumMethod);
            }

        }

        List<Method> methods = topLevelClass.getMethods();
        if (CollectionUtils.isEmpty(methods)) {
            return;
        }

        for (Method method : methods) {
            if ("clear".equals(method.getName())) {
                method.addBodyLine("forUpdate = false;");
                method.addBodyLine("definedQueryColumns = false;");
                method.addBodyLine("queryColumns.clear();");
            }

            if ((introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Example").equals(method.getName())) {
                method.addBodyLine("queryColumns = new ArrayList<String>();");
            }
        }
    }

    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        if (element != null) {
            List<Element> elements = element.getElements();
            if (!CollectionUtils.isEmpty(elements)) {
                XmlElement xmlElement = ((XmlElement) ((XmlElement) ((XmlElement) elements.get(0)).getElements().get(0)).getElements().get(0));
                List<Element> forEchElements = ((XmlElement) xmlElement.getElements().get(0)).getElements();
                xmlElement.getElements().remove(0);
                List<Element> childElements = xmlElement.getElements();
                for (Element elementTmp : forEchElements) {
                    childElements.add(elementTmp);
                }
//                TextElement textElement = new TextElement("IS_DELETED = '0'");
                TextElement textElement = new TextElement("");
                XmlElement whereChildElement = new XmlElement("trim");
                Attribute trimAttribute1 = new Attribute("prefix", "(");
                Attribute trimAttribute2 = new Attribute("suffix", ")");
                Attribute trimAttribute3 = new Attribute("prefixOverrides", "and");
                whereChildElement.addAttribute(trimAttribute1);
                whereChildElement.addAttribute(trimAttribute2);
                whereChildElement.addAttribute(trimAttribute3);
                List<Element> xmlElementList = ((XmlElement) elements.get(0)).getElements();
                for (Element elementTmp : xmlElementList) {
                    whereChildElement.addElement(elementTmp);
                }
                whereChildElement.addElement(0, textElement);
                ((XmlElement) elements.get(0)).getElements().remove(0);
                ((XmlElement) elements.get(0)).getElements().add(whereChildElement);
            }
        }


        return super.sqlMapExampleWhereClauseElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType(
                "org.springframework.stereotype.Repository"));
        interfaze.addAnnotation("@Repository");
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        generateSetExpression(element, introspectedTable);
        TextElement textElement = (TextElement) element.getElements().get(element.getElements().size() - 1);
        element.getElements().remove(element.getElements().size() - 1);

        element.getElements().add(new TextElement(textElement.getContent().replace("#{", "#{record.")));
        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    }

    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        //generateForUpdateSqlMapSelectByExample(element);
        return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement xmlElement = (XmlElement) element.getElements().get(2);
        element.getElements().remove(2);
        XmlElement ifNotDefinedQueryColumnsXmlElement = new XmlElement("if");
        Attribute attribute = new Attribute("test", "!definedQueryColumns");
        ifNotDefinedQueryColumnsXmlElement.addAttribute(attribute);
        ifNotDefinedQueryColumnsXmlElement.addElement(xmlElement);

        XmlElement ifDefinedQueryColumns = new XmlElement("if");
        Attribute attributeDefinedQueryColumns = new Attribute("test", "definedQueryColumns");
        ifDefinedQueryColumns.addAttribute(attributeDefinedQueryColumns);

        XmlElement forEachQueryColumns = new XmlElement("foreach");
        Attribute collection = new Attribute("collection", "queryColumns");
        Attribute item = new Attribute("item", "queryColumn");
        Attribute separator = new Attribute("separator", ",");
        forEachQueryColumns.addAttribute(collection);
        forEachQueryColumns.addAttribute(item);
        forEachQueryColumns.addAttribute(separator);
        forEachQueryColumns.addElement(new TextElement("${queryColumn}"));
        ifDefinedQueryColumns.addElement(forEachQueryColumns);

        element.getElements().add(2, ifNotDefinedQueryColumnsXmlElement);
        element.getElements().add(3, ifDefinedQueryColumns);

        generateForUpdateSqlMapSelectByExample(element);

        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {

        generateForUpdateSqlMapSelectByExample(element);

        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {

        generateSetExpression(element, introspectedTable);

        return super.sqlMapUpdateByExampleSelectiveElementGenerated(element, introspectedTable);
    }

    /**
     * mapper.xml 增加Set表达式场景,支持如update set 字段=表达式(如:'字段+1')这种场景
     *
     * @param element
     * @param introspectedTable
     */
    private void generateSetExpression(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().remove(1);
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();

        if (CollectionUtils.isEmpty(baseColumns)) {
            return;
        }

        XmlElement xmlElement = new XmlElement("set");

        XmlElement setChildElement = new XmlElement("trim");
        Attribute trimAttribute = new Attribute("suffixOverrides", ",");
        setChildElement.addAttribute(trimAttribute);
        for (IntrospectedColumn baseColumn : baseColumns) {
            XmlElement baseXmlElement = new XmlElement("if");
            Attribute attribute = new Attribute("test", "record." + baseColumn.getJavaProperty()
                    + "!=null and record." + baseColumn.getJavaProperty() + "Expression==null");
            baseXmlElement.addAttribute(attribute);
            baseXmlElement.addElement(new TextElement(baseColumn.getActualColumnName() + " = #{record."
                    + baseColumn.getJavaProperty() + ",jdbcType=" + baseColumn.getJdbcTypeName() + "},"));

            XmlElement expressionXmlElement = new XmlElement("if");
            Attribute expressionAttribute = new Attribute("test", "record." + baseColumn.getJavaProperty()
                    + "Expression!=null");
            expressionXmlElement.addAttribute(expressionAttribute);
            expressionXmlElement.addElement(new TextElement(baseColumn.getActualColumnName() + " = ${record."
                    + baseColumn.getJavaProperty() + "Expression},"));

            setChildElement.addElement(baseXmlElement);
            setChildElement.addElement(expressionXmlElement);
        }
        xmlElement.addElement(setChildElement);
        element.addElement(1, xmlElement);
    }


    private void generateForUpdateSqlMapSelectByExample(XmlElement element) {
        XmlElement baseXmlElement = new XmlElement("if");
        Attribute attribute = new Attribute("test", "forUpdate");
        baseXmlElement.addAttribute(attribute);
        baseXmlElement.addElement(new TextElement("for update"));
        element.getElements().add(baseXmlElement);

    }


    private void addDomainClassFields(TopLevelClass topLevelClass) {

        topLevelClass.getMethods().clear();
        List<Field> fields = topLevelClass.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        List<Field> fieldList = new ArrayList<Field>();

        for (Field field : fields) {
            Field fieldNew = new Field();
            fieldNew.setName(field.getName() + "Expression");
            fieldNew.setVisibility(field.getVisibility());
            FullyQualifiedJavaType type = new FullyQualifiedJavaType("java.lang.String");
            fieldNew.setType(type);
            fieldNew.addJavaDocLine("/** " + field.getName() + "属性对应的表达式属性，");
            fieldNew.addJavaDocLine(" * 目前用来支持update set 字段=表达式(如:'字段+1')这种场景");
            fieldNew.addJavaDocLine(" **/");
            fieldList.add(fieldNew);


            Field fieldMaxId = new Field();
            fieldMaxId.setName("max" + StringUtils.capitalize(field.getName()));
            fieldMaxId.setVisibility(field.getVisibility());
            FullyQualifiedJavaType maxIdType = new FullyQualifiedJavaType("java.lang.Long");
            fieldMaxId.setType(maxIdType);
            fieldMaxId.addJavaDocLine("/** 与 max("+field.getName()+") 映射**/");
            fieldList.add(fieldMaxId);

            Field fieldMinId = new Field();
            fieldMinId.setName("min" + StringUtils.capitalize(field.getName()));
            fieldMinId.setVisibility(field.getVisibility());
            FullyQualifiedJavaType minIdType = new FullyQualifiedJavaType("java.lang.Long");
            fieldMinId.setType(minIdType);
            fieldMinId.addJavaDocLine("/** 与 min("+field.getName()+") 映射**/");
            fieldList.add(fieldMinId);

            Field fieldAvgId = new Field();
            fieldAvgId.setName("avg" + StringUtils.capitalize(field.getName()));
            fieldAvgId.setVisibility(field.getVisibility());
            FullyQualifiedJavaType avgIdType = new FullyQualifiedJavaType("java.lang.Long");
            fieldAvgId.setType(avgIdType);
            fieldAvgId.addJavaDocLine("/** 与 avg("+field.getName()+") 映射**/");
            fieldList.add(fieldAvgId);

            Field fieldCountId = new Field();
            fieldCountId.setName("count" + StringUtils.capitalize(field.getName()));
            fieldCountId.setVisibility(field.getVisibility());
            FullyQualifiedJavaType countIdType = new FullyQualifiedJavaType("java.lang.Long");
            fieldCountId.setType(countIdType);
            fieldCountId.addJavaDocLine("/** 与 count("+field.getName()+") 映射**/");
            fieldList.add(fieldCountId);

            Field fieldCountDistinctId = new Field();
            fieldCountDistinctId.setName("countDistinct" + StringUtils.capitalize(field.getName()));
            fieldCountDistinctId.setVisibility(field.getVisibility());
            FullyQualifiedJavaType countDistinctIdType = new FullyQualifiedJavaType("java.lang.Long");
            fieldCountDistinctId.setType(countDistinctIdType);
            fieldCountDistinctId.addJavaDocLine("/** 与 count(distinct"+field.getName()+") 映射**/");
            fieldList.add(fieldCountDistinctId);


            Field fieldSumId = new Field();
            fieldSumId.setName("sum" + StringUtils.capitalize(field.getName()));
            fieldSumId.setVisibility(field.getVisibility());
            fieldSumId.setType(field.getType());
            fieldSumId.addJavaDocLine("/** 与 sum("+field.getName()+") 映射**/");
            fieldList.add(fieldSumId);

        }

        for (Field field : fieldList) {
          //给某些属性添加 @JsonIgnore
          String fieldName = field.getName();
          if(StringUtils.isNotBlank(fieldName)){
            if(fieldName.indexOf("Expression")>=0 || fieldName.startsWith("max") || fieldName.startsWith("min") || 
                fieldName.startsWith("avg") || fieldName.startsWith("count") || fieldName.startsWith("sum") || 
                fieldName.startsWith("sum")){
                field.addAnnotation("@JsonIgnore");
                topLevelClass.addImportedType(new FullyQualifiedJavaType("com.fasterxml.jackson.annotation.JsonIgnore"));
            }
          }
            topLevelClass.addField(field);
        }

    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document,
                                           IntrospectedTable introspectedTable) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        addDomainClassFields(topLevelClass);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        addDomainClassFields(topLevelClass);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        addDomainClassFields(topLevelClass);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    private void generateToString(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {

        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.wf2311.commons.persist.annotation.Domain"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.ToString"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
//        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
//        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
        topLevelClass.addAnnotation("@Domain");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@ToString");
        List<Field> fields = topLevelClass.getFields();
        Map<String, Field> map = new HashMap<String, Field>();
        for (Field field : fields) {
            map.put(field.getName(), field);
        }
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        for (IntrospectedColumn column : columns) {
            Field f = map.get(column.getJavaProperty());
            if (f != null) {
                f.getJavaDocLines().clear();
                if (column.getRemarks() != null) {
                    f.addJavaDocLine("/** " + column.getRemarks() + " */");
                    if (column.getRemarks().indexOf("@Primary") != -1 || column.getRemarks().indexOf("Primary") != -1
                            || column.getRemarks().indexOf("Primary") != -1 || column.getRemarks().indexOf("Primary") != -1) {
                        f.addAnnotation("@Primary");
                        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.wf2311.commons.persist.annotation.Primary"));
                    }

                }
                //解决tinyint 对应成 Boolean的问题
                if(column.getJdbcTypeName().equals("BIT")){
                  f.setType(FullyQualifiedJavaType.getIntInstance());
                }
            }
        }
    }

//	public boolean validate(List<String> arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}

//    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
    
}
