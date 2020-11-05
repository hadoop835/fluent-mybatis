package cn.org.atool.fluent.mybatis.generate.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.RefField;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.customize.IBaseEntity;
import cn.org.atool.fluent.mybatis.customize.MyCustomerInterface;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * StudentScoreEntity: 数据映射实体定义
 *
 * @author Powered By Fluent Mybatis
 */
@Getter
@Setter
@ToString(doNotUseGetters = true)
@Accessors(
    chain = true
)
@FluentMybatis(
    table = "student_score",
    mapperBeanPrefix = "my",
    defaults = MyCustomerInterface.class
)
public class StudentScoreEntity extends RichEntity implements IBaseEntity<StudentScoreEntity> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;

    /**
     * 记录创建时间
     */
    @TableField(
        value = "gmt_created",
        insert = "now()"
    )
    private Date gmtCreated;

    /**
     * 记录最后修改时间
     */
    @TableField(
        value = "gmt_modified",
        insert = "now()",
        update = "now()"
    )
    private Date gmtModified;

    /**
     * 逻辑删除标识
     */
    @TableField(
        value = "is_deleted",
        insert = "0"
    )
    private Boolean isDeleted;

    /**
     * 数据隔离环境
     */
    @TableField("env")
    private String env;

    /**
     * 性别, 0:女; 1:男
     */
    @TableField("gender_man")
    private Integer genderMan;

    /**
     * 学期
     */
    @TableField("school_term")
    private Integer schoolTerm;

    /**
     * 成绩
     */
    @TableField("score")
    private Integer score;

    /**
     * 学号
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 学科
     */
    @TableField("subject")
    private String subject;

    /**
     * 租户标识
     */
    @TableField("tenant")
    private Long tenant;

    @Getter(AccessLevel.NONE)
    @RefField("studentId:id")
    private StudentEntity student;

    @Override
    public Serializable findPk() {
        return this.id;
    }

    public StudentEntity getStudent() {
        super.lazyLoad("studentOfStudentScoreEntity", this::setStudent);
        return student;
    }
}