package cn.org.atool.fluent.mybatis.method.model;

import static cn.org.atool.fluent.mybatis.mapper.FluentConst.Param_EW;
import static java.lang.String.format;

/**
 * SqlBuilderConst: SqlBuilder中使用的表达式常量
 *
 * @author:darui.wu Created by darui.wu on 2020/6/23.
 */
public interface XmlConstant {
    /**
     * 变量在xml文件中的占位符全路径表达式
     * 例子: #{ew.wrapperData.parameters.variable_1}
     */
    String WRAPPER_PARAM_FORMAT = "#{%s.parameters.%s}";

    String Spec_Comment_Not_Null = "SPEC_COMMENT != null and SPEC_COMMENT != ''";

    String Wrapper_Data = format("%s.wrapperData", Param_EW);

    String Wrapper_Exists = format("%s != null", Wrapper_Data);

    String Wrapper_Page_Is_Null = format("%s.paged == null", Wrapper_Data);

    String Wrapper_Page_Not_Null = format("%s.paged != null", Wrapper_Data);

    String Wrapper_Distinct_True = format("%s.distinct", Wrapper_Data);

    String Wrapper_Select_Not_Null = format("%s.sqlSelect != null", Wrapper_Data);

    String Wrapper_Select_Var = format("${%s.sqlSelect}", Wrapper_Data);

    String Wrapper_UpdateStr_Not_Null = format("%s.updateStr != null", Wrapper_Data);

    String Wrapper_UpdateStr_Var = format("${%s.updateStr}", Wrapper_Data);

    String Wrapper_Update_Contain_Key = format("%s.updates.containsKey('@column') == false", Wrapper_Data);

    String Wrapper_Where_NotNull = format("%s.whereSql != null", Wrapper_Data);

    String Wrapper_Where_Var = format("${%s.whereSql}", Wrapper_Data);

    String Wrapper_GroupBy_NotNull = format("%s.groupBy != null", Wrapper_Data);

    String Wrapper_GroupBy_Var = format("${%s.groupBy}", Wrapper_Data);

    String Wrapper_OrderBy_NotNull = format("%s.orderBy != null", Wrapper_Data);

    String Wrapper_OrderBy_Var = format("${%s.orderBy}", Wrapper_Data);

    String Wrapper_LastSql_NotNull = format("%s.lastSql != null", Wrapper_Data);

    String Wrapper_LastSql_Var = format("${%s.lastSql}", Wrapper_Data);

    String Wrapper_Paged_Offset = format("#{%s.paged.offset}", Wrapper_Data);

    String Wrapper_Paged_Size = format("#{%s.paged.limit}", Wrapper_Data);

    String Wrapper_Paged_End_Offset = format("#{%s.paged.endOffset}", Wrapper_Data);
}