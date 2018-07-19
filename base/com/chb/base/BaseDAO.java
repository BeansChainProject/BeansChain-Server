package com.chb.base;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.chb.util.DateUtil;

public class BaseDAO {

	/**
	 * 日志对象
	 */
	public Log log = LogFactory.getLog(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * <p>
	 * Discription: 批量执行方法(BatchPreparedStatementSetter结构入参)
	 * </p>
	 * 
	 * @param sql
	 * @param setter
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter setter) {
		log.info(sql);
		return getJdbcTemplate().batchUpdate(sql, setter);
	}

	/**
	 * <p>
	 * Discription: 批量执行方法(数组入参)
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public int[] batchUpdate(String[] sql) {
		if (sql != null && sql.length != 0) {
			for (String subSql : sql) {
				log.info(subSql);
			}
		}
		return getJdbcTemplate().batchUpdate(sql);
	}

	public int[] batchUpdate(List<String> sqls) {
		if (sqls == null || sqls.size() == 0) {
			return new int[0];
		}
		String[] sql = new String[sqls.size()];
		for (int i = 0; i < sqls.size(); i++) {
			sql[i] = sqls.get(i);
		}
		return batchUpdate(sql);
	}

	/**
	 * <p>
	 * Discription: 清空某个表
	 * </p>
	 * 
	 * @param assistTableName
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void clearAssistTable(String assistTableName) {
		String sql = "TRUNCATE TABLE " + assistTableName;
		executeUpdate(sql);
	}

	/**
	 * <p>
	 * Discription: 删除临时表
	 * </p>
	 * 
	 * @param assistTableName
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void destroyTable(String assistTableName) {
		String sql = "DROP TABLE IF EXISTS " + assistTableName;
		executeUpdate(sql);
	}

	/**
	 * <p>
	 * Discription: 获得所有符合条件的t*表的表名称
	 * </p>
	 * 
	 * @param dataBase
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public List<String> getAllTableNames(String dataBase) {
		List<String> listTableNames = null;
		String sql = "SHOW TABLES FROM " + dataBase + " LIKE 't%'";
		try {
			log.info(sql);
			listTableNames = queryListString(sql, String.class);
		} catch (RuntimeException e) {
			return new ArrayList<String>();
		}
		return listTableNames;
	}

	/**
	 * <p>
	 * Discription: 供t*表获得表名称使用
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public List<String> queryListString(String sql, Class<String> clazz) {
		log.info(sql);
		return getJdbcTemplate().queryForList(sql, clazz);
	}

	/**
	 * <p>
	 * Discription: 不带参数,返回SqlRowSet.SqlRowSet是对JDBC中ResultSet的简单封装
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public SqlRowSet queryRowSet(String sql) {
		return getJdbcTemplate().queryForRowSet(sql);
	}

	/**
	 * <p>
	 * Discription: 带参数,返回SqlRowSet.SqlRowSet是对JDBC中ResultSet的简单封装
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public SqlRowSet queryRowSet(String sql, Object[] obj) {
		String logInfo = getLogInfo(sql, obj);
		log.info(logInfo);
		return getJdbcTemplate().queryForRowSet(sql, obj);
	}

	/**
	 * 
	 * <p>
	 * Discription:根据sql和参数组装log信息
	 * </p>
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	private String getLogInfo(String sql, Object[] obj) {
		String logInfo = sql + ";paramter:";
		if (obj != null && obj.length != 0) {
			String param = "";
			for (int i = 0, n = obj.length; i < n; i++) {
				if (obj[i] != null) {
					param = obj[i].toString();
				} else {
					param = "null";
				}
				logInfo += param + ",";
			}
			logInfo = logInfo.substring(0, logInfo.length() - 1);// 去掉最后一个,号
		}
		return logInfo;
	}

	/**
	 * <p>
	 * Discription: 不带参数的update方法
	 * </p>
	 * 
	 * @param sql
	 *            SQL语句
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public int executeUpdate(String sql) {
		log.info(sql);
		return getJdbcTemplate().update(sql);
	}

	/**
	 * <p>
	 * Discription: 带参数的update方法
	 * </p>
	 * 
	 * @param sql
	 *            SQL语句
	 * @param obj
	 *            参数
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public int executeUpdate(String sql, Object[] obj) {
		String logInfo = getLogInfo(sql, obj);
		log.info(logInfo);
		return getJdbcTemplate().update(sql, obj);
	}

	/**
	 * <p>
	 * Discription:返回String 数据,不带参数
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public String queryForString(String sql, String key) {
		log.info(sql);
		Object obj = null;
		try {
			obj = getJdbcTemplate().queryForMap(sql).get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj == null) {
			return "";
		} else {
			return (String) obj;
		}
	}

	/**
	 * <p>
	 * Discription:返回整型数据,带参数
	 * </p>
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public int queryForInt(String sql, Object[] obj) {
		String logInfo = getLogInfo(sql, obj);
		log.info(logInfo);
		int result = 0;
		result = getJdbcTemplate().queryForObject(sql, obj, Integer.class);
		return result;
	}

	/**
	 * <p>
	 * Discription:返回Long类型数据,带参数
	 * </p>
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public long queryForLong(String sql, Object[] obj) {
		String logInfo = getLogInfo(sql, obj);
		log.info(logInfo);
		return getJdbcTemplate().queryForObject(sql, obj, Long.class);
	}

	/**
	 * <p>
	 * Discription:带参数的查询,返回List,list中元素是参数中给定的类.
	 * 注:sql语句中获得的DataSet中字段要和给定的类中包含的变量名称相同
	 * </p>
	 * 
	 * @param sql
	 * @param objs
	 * @param clazz
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	// @SuppressWarnings("unchecked")
	public <E> List<E> queryForClassList(String sql, Object[] objs, Class<E> clazz) {
		if (clazz == null) {
			return new ArrayList<E>();
		}
		String logInfo = getLogInfo(sql, objs);
		log.info(logInfo);
		List<Map<String, Object>> tempList = getJdbcTemplate().queryForList(sql, objs);
		return toBeanList(tempList, clazz);
	}

	/**
	 * 将map转换成Bean，Bean的属性名与map的key值对应时不区分大小写，并对map中key做忽略OMIT_REG正则处理
	 * 
	 * @param <E>
	 * @param cla
	 * @param map
	 * @return
	 */
	public static <E> E transformMaptoBean(Class<E> cla, Map<String, Object> map) {

		// 创建对象
		E obj = null;
		try {
			obj = cla.newInstance();
			if (obj == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			// log.error("类型实例化对象失败,类型:" + cla);
			return null;
		}

		// 处理map的key
		Map<String, Object> newmap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> en : map.entrySet()) {
			newmap.put("set" + en.getKey().trim().toLowerCase(), en.getValue());
		}

		// 进行值装入
		Method[] ms = cla.getMethods();
		for (Method method : ms) {
			String mname = method.getName().toLowerCase();
			if (mname.startsWith("set")) {

				Class<?>[] clas = method.getParameterTypes();
				Object v = newmap.get(mname);

				if (v != null && clas.length == 1) {
					try {
						if (clas[0] == Integer.class || clas[0] == Integer.TYPE) {
							method.invoke(obj, Integer.parseInt(v.toString()));
						} else if (clas[0] == Long.class || clas[0] == Long.TYPE) {
							method.invoke(obj, new Double(Double.parseDouble(v.toString())).longValue());
						} else if (clas[0] == Double.class || clas[0] == Double.TYPE) {
							method.invoke(obj, Double.parseDouble(v.toString()));
						} else {
							method.invoke(obj, v);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}

	/**
	 * 不带参数的插入 0:失败，1:成功
	 * 
	 * @param sql
	 * @return
	 */
	public int insert(String sql) {
		int row = 0;
		try {
			row = jdbcTemplate.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 带参数的插入 0:失败，1:成功
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	public int insertParams(String sql, Object[] objs) {
		int row = 0;
		try {
			row = jdbcTemplate.update(sql, objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 不带参数的更新 0:失败，1:成功
	 * 
	 * @param sql
	 * @return
	 */
	public int update(String sql) {
		int row = 0;
		try {
			row = jdbcTemplate.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 带参数的更新 0:失败，1:成功
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	public int updateParams(String sql, Object[] objs) {
		int row = 0;
		try {
			row = jdbcTemplate.update(sql, objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 不带参数的删除 0:失败，1:成功
	 * 
	 * @param sql
	 * @return
	 */
	public int delete(String sql) {
		int row = 0;
		try {
			row = jdbcTemplate.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 带参数的删除 0:失败，1:成功
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	public int deleteParams(String sql, Object[] objs) {
		int row = 0;
		try {
			row = jdbcTemplate.update(sql, objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 不带参数的返回整数类型
	 * 
	 * @param sql
	 * @return
	 */
	public int queryForInt(String sql) {
		log.info(sql);
		return jdbcTemplate.queryForObject(sql, null, Integer.class);
	}

	/**
	 * 不带参数的返回Long类型
	 * 
	 * @param sql
	 * @return
	 */
	public long queryForLong(String sql) {
		return jdbcTemplate.queryForObject(sql, null, Long.class);
	}

	/**
	 * 带参数的返回整数类型
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	public int queryForIntParams(String sql, Object[] objs) {
		int result = 0;
		try {
			result = jdbcTemplate.queryForObject(sql, objs, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询返回结果是List，List中的元素是Map类型
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql) {
		log.info(sql);
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> queryForList(String sql, PageQuery pageQuery) {
		final String tempSql = mysqlLimit(sql, pageQuery);
		log.info(tempSql);
		jdbcTemplate.setFetchSize(100);
		List<Map<String, Object>> list = (List<Map<String, Object>>) jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(tempSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			}
		}, new ResultSetExtractor<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> extractData(ResultSet arg0) throws SQLException, DataAccessException {
				java.sql.ResultSetMetaData rsmd = arg0.getMetaData();
				int columnLength = rsmd.getColumnCount();
				List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
				while (arg0.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					for (int i = 0; i < columnLength; i++) {
						String fieldName = rsmd.getColumnLabel(i + 1).toUpperCase();
						if (StringUtils.equalsIgnoreCase("DATETIME", rsmd.getColumnTypeName(i + 1))
								&& arg0.getTimestamp(i + 1) != null) {
							map.put(fieldName, DateUtil.getDate(arg0.getTimestamp(i + 1)));
						} else {
							map.put(fieldName, arg0.getString(fieldName));
						}
					}
					tempList.add(map);
				}
				arg0.close();
				return tempList;
			}
		});
		return list;
	}

	public List<Map<String, Object>> queryForList(String sql, PageQuery pageQuery, Object[] obj) {
		final String tempSql = mysqlLimit(sql, pageQuery);
		log.info(tempSql);
		final Object[] params = obj;
		List<Map<String, Object>> list = new ArrayList<>();
		jdbcTemplate.setFetchSize(100);
		if (obj == null) {
			list = (List<Map<String, Object>>) jdbcTemplate.query(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(tempSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				}
			}, new ResultSetExtractor<List<Map<String, Object>>>() {
				@Override
				public List<Map<String, Object>> extractData(ResultSet arg0) throws SQLException, DataAccessException {
					java.sql.ResultSetMetaData rsmd = arg0.getMetaData();
					int columnLength = rsmd.getColumnCount();
					List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
					while (arg0.next()) {
						Map<String, Object> map = new HashMap<String, Object>();
						for (int i = 0; i < columnLength; i++) {
							String fieldName = rsmd.getColumnLabel(i + 1).toUpperCase();
							if (StringUtils.equalsIgnoreCase("DATETIME", rsmd.getColumnTypeName(i + 1))
									&& arg0.getTimestamp(i + 1) != null) {
								map.put(fieldName, DateUtil.getDate(arg0.getTimestamp(i + 1)));
							} else {
								map.put(fieldName, arg0.getString(fieldName));
							}
						}
						tempList.add(map);
					}
					arg0.close();
					return tempList;
				}
			});
		} else {
			list = (List<Map<String, Object>>) jdbcTemplate.query(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(tempSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				}
			}, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement arg0) throws SQLException {
					for (int i = 0; i < params.length; i++) {
						arg0.setString((i + 1), String.valueOf(params[i]));
					}
				}
			}, new ResultSetExtractor<List<Map<String, Object>>>() {
				@Override
				public List<Map<String, Object>> extractData(ResultSet arg0) throws SQLException, DataAccessException {
					java.sql.ResultSetMetaData rsmd = arg0.getMetaData();
					int columnLength = rsmd.getColumnCount();
					List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
					while (arg0.next()) {
						Map<String, Object> map = new HashMap<String, Object>();
						for (int i = 0; i < columnLength; i++) {
							String fieldName = rsmd.getColumnLabel(i + 1).toUpperCase();
							if (StringUtils.equalsIgnoreCase("DATETIME", rsmd.getColumnTypeName(i + 1))
									&& arg0.getTimestamp(i + 1) != null) {
								map.put(fieldName, DateUtil.getDate(arg0.getTimestamp(i + 1)));
							} else {
								map.put(fieldName, arg0.getString(fieldName));
							}
						}
						tempList.add(map);
					}
					arg0.close();
					return tempList;
				}
			});
		}
		return list;
	}

	public List<String[]> queryForListString(String sql, PageQuery pageQuery, Object[] obj) {
		final String tempSql = mysqlLimit(sql, pageQuery);
		log.info(tempSql);
		final Object[] params = obj;
		List<String[]> list = new ArrayList<>();
		jdbcTemplate.setFetchSize(100);
		if (obj == null) {
			list = (List<String[]>) jdbcTemplate.query(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(tempSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				}
			}, new ResultSetExtractor<List<String[]>>() {
				@Override
				public List<String[]> extractData(ResultSet arg0) throws SQLException, DataAccessException {
					java.sql.ResultSetMetaData rsmd = arg0.getMetaData();
					int columnLength = rsmd.getColumnCount();
					List<String[]> tempList = new ArrayList<String[]>();
					while (arg0.next()) {
						String[] temp = new String[columnLength];
						for (int i = 0; i < columnLength; i++) {
							temp[i] = arg0.getString(i + 1);
						}
						tempList.add(temp);
					}
					arg0.close();
					return tempList;
				}
			});
		} else {
			list = (List<String[]>) jdbcTemplate.query(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(tempSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				}
			}, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement arg0) throws SQLException {
					for (int i = 0; i < params.length; i++) {
						arg0.setString((i + 1), String.valueOf(params[i]));
					}
				}
			}, new ResultSetExtractor<List<String[]>>() {
				@Override
				public List<String[]> extractData(ResultSet arg0) throws SQLException, DataAccessException {
					java.sql.ResultSetMetaData rsmd = arg0.getMetaData();
					int columnLength = rsmd.getColumnCount();
					List<String[]> tempList = new ArrayList<String[]>();
					while (arg0.next()) {
						String[] temp = new String[columnLength];
						for (int i = 0; i < columnLength; i++) {
							String fieldName = rsmd.getColumnName(i + 1).toUpperCase();
							temp[i] = arg0.getString(fieldName);
						}
						tempList.add(temp);
					}
					arg0.close();
					return tempList;
				}
			});
		}
		return list;
	}

	/**
	 * 不带参数的查询,返回List,list中元素是参数中给定的类.<br/>
	 * 注:sql语句中获得的DataSet中字段要和给定的类中包含的变量名称相同
	 */
	public <E> List<E> queryForClassList(String sql, Class<E> clazz) {
		if (clazz == null) {
			return new ArrayList<E>();
		}
		log.info(sql);
		List<Map<String, Object>> tempList = jdbcTemplate.queryForList(sql);
		return toBeanList(tempList, clazz);
	}

	/**
	 * 将map集合转换成Bean集合<br/>
	 * Bean的属性名与map的key值对应时不区分大小写，并对map中key做忽略OMIT_REG正则处理
	 */
	private <E> List<E> toBeanList(List<Map<String, Object>> mapList, Class<E> cla) {
		List<E> list = new ArrayList<E>(mapList.size());
		for (Map<String, Object> map : mapList) {
			E obj = toBean(cla, map);
			list.add(obj);
		}
		return list;
	}

	/**
	 * 将map转换成Bean<br/>
	 * Bean的属性名与map的key值对应时不区分大小写，并对map中key做忽略OMIT_REG正则处理
	 */
	@SuppressWarnings("rawtypes")
	private <E> E toBean(Class<E> cla, Map<String, Object> map) {
		// 创建对象
		E obj = null;
		try {
			obj = cla.newInstance();
			if (obj == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			return null;
		}
		// 处理map的key
		Map<String, Object> newmap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> en : map.entrySet()) {
			newmap.put("set" + en.getKey().trim().toLowerCase(), en.getValue());
		}
		// 进行值装入
		Method[] ms = cla.getMethods();
		for (Method method : ms) {
			String mname = method.getName().toLowerCase();
			if (mname.startsWith("set")) {
				Class[] clas = method.getParameterTypes();
				Object v = newmap.get(mname);
				if (v != null && clas.length == 1) {
					try {
						if (clas[0] == Integer.class || clas[0] == Integer.TYPE) {
							method.invoke(obj, Integer.parseInt(v.toString()));
						} else if (clas[0] == Long.class || clas[0] == Long.TYPE) {
							method.invoke(obj, Long.parseLong(v.toString()));
						} else if (clas[0] == Double.class || clas[0] == Double.TYPE) {
							method.invoke(obj, Double.parseDouble(v.toString()));
						} else {
							method.invoke(obj, v.toString());
						}
					} catch (Exception e) {

					}
				}
			}
		}
		return obj;
	}

	/**
	 * <p>
	 * Discription: 批量执行方法(数组入参)
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @author:
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public int batchUpdateSql(String[] sql) {
		int code = 0;
		try {
			if (sql != null && sql.length != 0) {
				for (String subSql : sql) {
					log.info(subSql);
				}
			}
			getJdbcTemplate().batchUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
		}
		return code;
	}

	/**
	 * 不带参数的查询,返回List,list中元素是参数中给定的类.<br/>
	 * 注:sql语句中获得的DataSet中字段要和给定的类中包含的变量名称相同
	 */
	public <E> List<E> queryForClassList(String sql, Class<E> clazz, PageQuery pageQuery) {
		if (clazz == null) {
			return new ArrayList<E>();
		}
		log.info(sql);
		List<Map<String, Object>> tempList = queryForList(sql, pageQuery);
		return toBeanList(tempList, clazz);
	}

	public <E> List<E> queryForClassList(String sql, Object[] objs, PageQuery pageQuery, Class<E> clazz) {
		if (clazz == null) {
			return new ArrayList<E>();
		}
		log.info(sql);
		List<Map<String, Object>> tempList = queryForList(sql, pageQuery, objs);
		return toBeanList(tempList, clazz);
	}

	public String limitSql(String sql, PageQuery pageQuery) {
		// StringBuilder buf = new StringBuilder(256);
		// if (tool != null) {
		// if (StringUtils.isNotEmpty(sql)) {
		// buf.append("SELECT * FROM ( SELECT IBMS_A.*, rownum r FROM ( ");
		// buf.append(sql);
		// buf.append(" ) IBMS_A WHERE rownum <= ");
		// buf.append((1 + tool.getPageIndex()) * tool.getPageSize());
		// buf.append(" ) IBMS_B");
		// buf.append(" WHERE r > ");
		// buf.append(tool.getPageIndex() * tool.getPageSize());
		// sql = buf.toString();
		// }
		// }
		return sql + " limit " + pageQuery.getPageNumber() + "," + pageQuery.getPageSize();
	}

	public String countSql(String sql) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(1) from (");
		buffer.append(sql);
		buffer.append(") q");
		return buffer.toString();
	}

	/**
	 * 神通数据库序列
	 * 
	 * @param sequencesName
	 * @return
	 */
	public long getSTSequences(String sequencesName) {
		String sql = "SELECT nextval ('" + sequencesName + "')";
		return jdbcTemplate.queryForObject(sql, null, Long.class);
	}

	public PageQuery pageSql(String sql, PageQuery pageQuery) {
		List<Map<String, Object>> list = new ArrayList<>();
		int total = 0;
		String countSql = countSql(sql);
		total = queryForInt(countSql);
		if (total > 0) {
			String pgSql = limitSql(sql, pageQuery);
			list = jdbcTemplate.queryForList(pgSql);
		}
		pageQuery.setTotalRow(total);
		pageQuery.setList(list);
		return pageQuery;
	}

	public PageQuery pageSql(String sql, PageQuery pageQuery, Object[] para) {
		List<Map<String, Object>> list = new ArrayList<>();
		int total = 0;
		String countSql = countSql(sql);
		total = queryForInt(countSql, para);
		if (total > 0) {
			String pgSql = limitSql(sql, pageQuery);
			list = jdbcTemplate.queryForList(pgSql, para);
		}
		pageQuery.setTotalRow(total);
		pageQuery.setList(list);
		return pageQuery;
	}

	public HashMap<String, Object> bean2Map(final Object obj) throws Throwable {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		Class<?> _class = obj.getClass();
		final Method[] ms = _class.getMethods();
		for (final Method m : ms) {
			String _name = m.getName();
			String mn = _name.toLowerCase();
			boolean _startsWith = mn.startsWith("get");
			if (_startsWith) {
				String _substring = mn.substring(3);
				mn = _substring;
				final Class<?>[] clz = m.getParameterTypes();
				int _length = clz.length;
				boolean _equals = (_length == 0);
				if (_equals) {
					final Object v = m.invoke(obj);
					if (((v instanceof String) && (!((String) v).isEmpty()))) {
						String _string = v.toString();
						map.put(mn, _string);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 插入并返回当前操作自动生成的自增长ID [MySQL]
	 * 
	 * @param sql
	 * @return
	 */
	public int insertAndGetKey(final String sql) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				return ps;
			}
		}, keyHolder);

		int generatedId = keyHolder.getKey().intValue();
		return generatedId;
	}

	private String mysqlLimit(String sql, PageQuery pageQuery) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);
		if (pageQuery != null) {
			buffer.append(" limit ");
			buffer.append(pageQuery.getPageNumber());
			buffer.append(",");
			buffer.append(pageQuery.getPageSize());
		}
		return buffer.toString();
	}
}
