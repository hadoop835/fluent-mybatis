package cn.org.atool.mbplus.method.partition;

import cn.org.atool.mbplus.util.MybatisUtil;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class SelectListInPartition extends SelectList {
    private final static String MAPPER_METHOD_ID = "selectListInPartition";

    private final static String SQL_FORMAT = "<script>\n%s SELECT %s FROM %s %s\n</script>";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_LIST;
        String sql = String.format(SQL_FORMAT,
                MybatisUtil.getPartitionDatabase(),
                sqlSelectColumns(tableInfo, true),
                MybatisUtil.getPartitionTable(tableInfo.getTableName()),
                this.sqlWhereEntityWrapper(true, tableInfo)
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatement(mapperClass, MAPPER_METHOD_ID, sqlSource, modelClass, tableInfo);
    }
}
