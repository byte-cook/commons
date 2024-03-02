package de.kobich.commons.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.log4j.Logger;

public class SQLUtils {
	private static final Logger logger = Logger.getLogger(SQLUtils.class);
	public static final char LIKE_ESCAPE_CHAR = '\\';
	public static final String LIKE_ESCAPE = Character.toString(LIKE_ESCAPE_CHAR);
	
	private enum SQLWildcard {
		MULTI('%', '*'), SINGLE('_', '?');
		private Character character;
		private Character userCharacter;

		private SQLWildcard(Character character, Character userCharacter) {
			this.character = character;
			this.userCharacter = userCharacter;
		}
	}

	/**
	 * Escapes SQL wildcards ("%", "_") by {@link #LIKE_ESCAPE_CHAR}. "c:\folder\ab%c_" -> "c:\\folder\\ab\%c\_" 
	 * @param param
	 * @return
	 */
	public static String escapeSQLWildcards(final String param) {
		String escaped = param;
		escaped = escaped.replace(LIKE_ESCAPE, LIKE_ESCAPE+LIKE_ESCAPE);
		escaped = escaped.replace("%", LIKE_ESCAPE+"%");
		escaped = escaped.replace("_", LIKE_ESCAPE+"_");
		// hsqldb only support "%" and "_"
//		escaped = escaped.replace("-", LIKE_ESCAPE+"-");
//		escaped = escaped.replace("[", LIKE_ESCAPE+"[");
		return escaped;
	}
	
	/**
	 * Escapes SQL wildcards ("%", "_") by {@link #LIKE_ESCAPE_CHAR} and converts the user wildcards ("*" and "?") to SQL wildcards ("%", "_")
	 * @param param
	 * @param wildcardBefore
	 * @param wildcardAfter
	 * @return
	 */
	public static String escapeLikeParam(@Nullable final String param, boolean wildcardBefore, boolean wildcardAfter) {
		if (param == null) {
			return null;
		}
		
//		String escaped = param;
		String escaped = escapeSQLWildcards(param);
		for (SQLWildcard wildcard : SQLWildcard.values()) {
			escaped = escaped.replace(wildcard.userCharacter, wildcard.character);
		}
		if (wildcardBefore) {
			escaped = SQLWildcard.MULTI.character + escaped;
		}
		if (wildcardAfter) {
			escaped += SQLWildcard.MULTI.character;
		}
		return escaped;
	}
	
	public static record DBTable(String catalog, String schema, String name, String remarks) {};

	public static List<DBTable> getTables(Connection connection, Set<String> tableNames) throws SQLException {
		List<DBTable> tables = new ArrayList<>();

		ResultSet rs = connection.getMetaData().getTables(null, null, "%", null);
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			String tableSchema = rs.getString("TABLE_SCHEM");
			String tableCatalog = rs.getString("TABLE_CAT");
			String tableRemarks = rs.getString("REMARKS");

			if (tableNames.contains(tableName)) {
				DBTable table = new DBTable(tableCatalog, tableSchema, tableName, tableRemarks);
				tables.add(table);
			}
		}
		return tables;
	}

	public enum DBNullableType {
		UNKNOWN, NOT_NULL, NULL
	}
	public static record DBTableColumn(String columnName, String columnLabel, String columnTypeName, int precision, boolean writable, boolean readOnly,
			DBNullableType nullable, boolean autoIncrement) {};
			
	public static List<DBTableColumn> getTableColumns(Connection connection, DBTable table) throws SQLException {
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE 1 = 2", table.name()));
			ResultSetMetaData tableMetaData = rs.getMetaData();

			List<DBTableColumn> columns = new ArrayList<DBTableColumn>();
			for (int i = 1; i <= tableMetaData.getColumnCount(); ++i) {
				// String catalogName = tableMetaData.getCatalogName(i);
				String columnName = tableMetaData.getColumnName(i);
				String columnLabel = tableMetaData.getColumnLabel(i);
				String columnTypeName = tableMetaData.getColumnTypeName(i);
				int precision = tableMetaData.getPrecision(i);
				boolean autoIncrement = tableMetaData.isAutoIncrement(i);
				boolean writable = tableMetaData.isDefinitelyWritable(i);
				boolean readOnly = tableMetaData.isReadOnly(i);
				int nullableConstant = tableMetaData.isNullable(i);
				DBNullableType nullable = DBNullableType.UNKNOWN;
				if (ResultSetMetaData.columnNoNulls == nullableConstant) {
					nullable = DBNullableType.NOT_NULL;
				}
				else if (ResultSetMetaData.columnNullable == nullableConstant) {
					nullable = DBNullableType.NULL;
				}

				logger.debug("Column: " + columnName + "\t" + columnLabel + "\t" + columnTypeName + "\t" + precision + "\t" + nullable);

				DBTableColumn column = new DBTableColumn(columnName, columnLabel, columnTypeName, precision, writable, readOnly, nullable,
						autoIncrement);
				columns.add(column);
			}
			return columns;
		}
	}
	
	public static record DBTablePK(String name, String columnName, DBTable table) {};
	
	public static List<DBTablePK> getPrimaryKeys(Connection connection, DBTable table) throws SQLException {
		List<DBTablePK> pks = new ArrayList<DBTablePK>();
		ResultSet rs = connection.getMetaData().getPrimaryKeys(table.catalog(), table.schema(), table.name());
		while (rs.next()) {
//			String tableCatalog = rs.getString("TABLE_CAT");
//			String tableSchema = rs.getString("TABLE_SCHEM");
//			String tableName = rs.getString("TABLE_NAME");
			String columnName = rs.getString("COLUMN_NAME");
			String pkName = rs.getString("PK_NAME");
			logger.debug("PK: " + columnName + "\t" + pkName);
			
			DBTablePK pk = new DBTablePK(pkName, columnName, table);
			pks.add(pk);
		}
		return pks;
	}

	public static record DBTableFK(String name, DBTable fkTable, String fkColumnName, DBTable pkTable, String pkColumnName) {};

	public static List<DBTableFK> getForeignKeys(Connection connection, DBTable table, Collection<DBTable> tables) throws SQLException {
		List<DBTableFK> fks = new ArrayList<>();
		ResultSet rs = connection.getMetaData().getImportedKeys(table.catalog(), table.schema(), table.name());
		while (rs.next()) {
			String pkTableCatalog = rs.getString("PKTABLE_CAT");
			String pkTableSchema = rs.getString("PKTABLE_SCHEM");
			String pkTableName = rs.getString("PKTABLE_NAME");
			String pkColumnName = rs.getString("PKCOLUMN_NAME");
			// String fkTableCatalog = rs.getString("FKTABLE_CAT");
			// String fkTableSchema = rs.getString("FKTABLE_SCHEM");
			// String fkTableName = rs.getString("FKTABLE_NAME");
			String fkColumnName = rs.getString("FKCOLUMN_NAME");
			String fkName = rs.getString("FK_NAME");

			logger.info("FK: " + fkColumnName + "\t" + fkName);
			// DBTable pkTable = audioTables.getByName(pkTableCatalog, pkTableSchema, pkTableName).orElse(null);

			DBTable pkTable = tables
					.stream()
					.filter(t -> t.catalog().equals(pkTableCatalog) && t.schema().equals(pkTableSchema) && t.name().equals(pkTableName))
					.findFirst()
					.orElse(null);
			if (pkTable != null) {
				fks.add(new DBTableFK(fkName, table, fkColumnName, pkTable, pkColumnName));
			}
		}
		return fks;
	}
	
	public static record DBTableUnique(String name, DBTable table) {};
	
	public static List<DBTableUnique> getTableUniques(Connection connection, DBTable table) throws SQLException {
		List<DBTableUnique> uniques = new ArrayList<DBTableUnique>();
		// DatabaseMetaData metaData = connection.getConnection().getMetaData();
		ResultSet rs = connection.getMetaData().getBestRowIdentifier(table.catalog(), table.schema(), table.name(), DatabaseMetaData.bestRowUnknown, true);
		while (rs.next()) {
			String columnName = rs.getString("COLUMN_NAME");

			logger.debug("Unique: " + columnName);
			DBTableUnique unique = new DBTableUnique(columnName, table);
			uniques.add(unique);
		}
		return uniques;
	}

	public static record DBTableIndex(String name, String columnName, DBTable table) {};

	public static List<DBTableIndex> getTableIndices(Connection connection, DBTable table) throws SQLException {
		List<DBTableIndex> indices = new ArrayList<DBTableIndex>();
		// DatabaseMetaData metaData = connection.getConnection().getMetaData();
		ResultSet rs = connection.getMetaData().getIndexInfo(table.catalog(), table.schema(), table.name(), false, true);
		while (rs.next()) {
//			String tableCatalog = rs.getString("TABLE_CAT");
//			String tableSchema = rs.getString("TABLE_SCHEM");
//			String tableName = rs.getString("TABLE_NAME");
			String indexName = rs.getString("INDEX_NAME");
			String columnName = rs.getString("COLUMN_NAME");

			logger.debug("Index: " + columnName);
			if (columnName != null) {
				DBTableIndex unique = new DBTableIndex(indexName, columnName, table);
				indices.add(unique);
			}
		}
		return indices;
	}

}
