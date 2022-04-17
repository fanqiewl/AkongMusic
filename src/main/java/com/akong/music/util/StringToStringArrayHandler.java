package com.akong.music.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将数据库读取的字符串转为字符串数组
 *
 * @author Akong
 * @create 2021/12/22 0:28
 */
public class StringToStringArrayHandler extends BaseTypeHandler<String[]> {

    private static final String splitCharset = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] objects, JdbcType jdbcType) throws SQLException {
        String str = arrayToString(objects);
        ps.setString(i, str);
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return stringToArray(str);
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        return stringToArray(str);
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        return stringToArray(str);
    }

    // --- private methods ---

    /**
     * String数组转String
     * 注：使用提前设定好的分隔符分割数组的每一项
     */
    private static String arrayToString(String[] array) {
        StringBuilder res = new StringBuilder();
        if (array != null && array.length > 0) {
            for (Object o : array) {
                res.append(splitCharset).append(o.toString());
            }
            res.append(splitCharset);
        }
        return res.length() > 0 ? res.toString() : null;
    }

    /**
     * 从String转String数组
     * 注：String是用分隔符分割的，使用String.split方法可以分解为数组
     */
    private static String[] stringToArray(String str) {
        List<String> list = new ArrayList<>();
        if (str != null) {
            String[] array = str.split(splitCharset);
            if (array.length > 0) {
                for (String o : array) {
                    if (o != null && o.length() > 0) {
                        list.add(o);
                    }
                }
            }
        }
        return list.toArray(new String[0]);
    }
}


