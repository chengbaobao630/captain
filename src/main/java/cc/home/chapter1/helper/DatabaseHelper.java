package cc.home.chapter1.helper;

import cc.home.chapter1.helper.annotation.Table;
import cc.home.util.MapUtils;
import cc.home.util.PropsUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERY_RUNNER;

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    private static final BasicDataSource DATA_SOURCE;

    static {
        CONNECTION_HOLDER = new ThreadLocal<>();
        QUERY_RUNNER = new QueryRunner();

        Properties props = PropsUtil.loadProps("config.properties");
        String driver = props.getProperty("jdbc.driver");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);


    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection due error", e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }


    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list due error", e);
            throw new RuntimeException(e);
        }
        return entityList == null ? Collections.emptyList() : entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn=getConnection();
            entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity  due error", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    public static List<Map<String,Object>> executeQuery(String sql,Object... params){
        List<Map<String,Object>> result;
        try {
            Connection conn=getConnection();
            result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("query entity  due error", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static int executeUpdate(String sql,Object... params){
        int rows ;
        try {
            Connection conn=getConnection();
            rows = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            LOGGER.error("executeUpdate  due error", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    public static  <T extends BaseEntity> boolean insertEntity(T t){
        final Map<String, Object> map = MapUtils.toSqlMap(t);
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ")
                .append(getTableName(t))
                .append("(");
        for (String s : map.keySet()) {
            if (map.get(s) instanceof BaseEntity) continue;
            s = camelToUnderline(s);
            sb.append(s)
                    .append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(") ")
                .append(" values (");
        for (Object o : map.values()) {
            if (o instanceof BaseEntity) continue;
            if (isValidDate(o.toString())) {
                sb.append(((Date)o).getTime());
            } else {
                sb.append("'")
                        .append(o.toString())
                        .append("'");
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        return executeUpdate(sb.toString()) == 1;
    }

    public static  <T extends BaseEntity> boolean updateEntity(T t){
        final Map<String, Object> map = MapUtils.toSqlMap(t);
        StringBuilder sb = new StringBuilder();
        sb.append("update ")
                .append(getTableName(t))
                .append(" set ");
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String column = entry.getKey();
            if (column.trim().equalsIgnoreCase("id")) {
                continue;
            }
            Object value = entry.getValue();
            if (value instanceof BaseEntity) {
                continue;
            }
            column = camelToUnderline(column);
            sb.append(column)
                    .append(" = ");
            if (isValidDate(value.toString())){
                sb.append(((Date)value).getTime());
            }else {
                sb.append("'")
                        .append(value)
                        .append("'");
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" where ")
                .append(t.getKeyColumn())
                .append(" = '")
                .append(t.getId().toString())
                .append("'");
        return executeUpdate(sb.toString()) == 1;
    }

    public static  <T extends BaseEntity> boolean deleteEntity(Class<T> entityClass,long id) throws IllegalAccessException, InstantiationException {
        String sql = "DELETE FROM  " + getTableName(entityClass.newInstance()) + "where id = ?";
        return executeUpdate(sql,id) == 1;
    }


    public static final char UNDERLINE = '_';

    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }


    private static  <T extends BaseEntity> String  getTableName(T t) {
        String tableName;
        Table table=t.getClass().getAnnotation(Table.class);
        if (table != null){
            tableName=table.name();
        }else {
            tableName=t.getClass().getSimpleName().toLowerCase();
        }
        return tableName;
    }


}
