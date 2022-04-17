package com.akong.base.interceptor;

import com.akong.base.util.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 分页插件
 *
 * @author Akong
 *
 */
@Slf4j
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }))
public class MyPageInterceptor implements Interceptor {
    private Invocation iv;
    private StatementHandler sh;

    @Override
    public Object intercept(Invocation iv) throws Throwable {
        this.iv = iv;
        sh = (StatementHandler) iv.getTarget();

        // 分页对象
        PageBean pb;

        // 获取元数据对象
        MetaObject mo = SystemMetaObject.forObject(sh);

        // 获取参数对象
        Object obj = sh.getBoundSql().getParameterObject();

        // 判断参数类型是否为Map集合
        if (obj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) obj;

            // 判断map集合中是否存在PageBean
            if (!map.containsKey("pageBean"))
                return iv.proceed();

            // 获取PageBean对象
            pb = (PageBean) map.get("pageBean");
        }else {
            return iv.proceed();
        }

        // 获取SQL语句
        String sql = sh.getBoundSql().getSql();

        // 设置总条数
        queryTotal(sql, pb);

        // 对PageBean进行判定
        if (pb != null && pb.isPagination()) {
            sql += " limit " + pb.calcStartIndex() + "," + pb.getRow();
        }

        // 设置全新的SQL语句
        mo.setValue("delegate.boundSql.sql", sql);

        // 放行
        return iv.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private void queryTotal(String sql, PageBean pb) throws SQLException {
        // 传入指定SQL语句
        PreparedStatement ps = ((Connection) iv.getArgs()[0])
                .prepareStatement("select count(0) from (" + sql + ") as a");

        // 渲染参数
        ParameterHandler parameterHandler = sh.getParameterHandler();
        parameterHandler.setParameters(ps);

        // 获取结果集
        ResultSet rs = ps.executeQuery();

        // 指针下移获取数据
        if (rs.next()) {
            // 设置总条数
            pb.setTotal(rs.getInt(1));
        }
    }

}
