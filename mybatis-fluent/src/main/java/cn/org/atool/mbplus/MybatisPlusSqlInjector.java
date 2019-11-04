package cn.org.atool.mbplus;

import cn.org.atool.mbplus.method.Insert;
import cn.org.atool.mbplus.method.InsertBatch;
import cn.org.atool.mbplus.method.InsertWithPk;
import cn.org.atool.mbplus.method.UpdateByQuery;
import cn.org.atool.mbplus.method.partition.DeleteInPartition;
import cn.org.atool.mbplus.method.partition.SelectListInPartition;
import cn.org.atool.mbplus.method.partition.UpdateInPartition;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MybatisPlusSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList() {
        return Stream.of(
                // 替换掉Insert默认实现
                new Insert(),
                new UpdateByQuery(),
                //
                new Delete(),
                new DeleteByMap(),
                new DeleteById(),
                new DeleteBatchByIds(),
                // new Update(),
                new UpdateById(),
                new SelectById(),
                new SelectBatchByIds(),
                new SelectByMap(),
                new SelectOne(),
                new SelectCount(),
                new SelectMaps(),
                new SelectMapsPage(),
                new SelectObjs(),
                new SelectList(),
                new SelectPage(),
                //
                new InsertBatch(),
                new InsertWithPk(),
                //
                new SelectListInPartition(),
                new UpdateInPartition(),
                new DeleteInPartition()
        ).collect(Collectors.toList());
    }
}
