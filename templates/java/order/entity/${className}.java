package @{package}.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

/**
 * @{table.name}:@{table.remarks}
 *
 * @author @{author}
 */
@TableName(value = "@{table.tableName}")
public class @{table.className} extends Model<@{table.className}> {

	private static final long serialVersionUID = 1L;

	//<editor-fold desc="properties">
	# for(column in table.columns){ #

		# if(column.pk) { #
	@TableId(value = "@{utils.toLowerCase(column.columnName)}", type=IdType.AUTO)
		# } else { #
	/**
	 * @{column.remarks}
	 */
			# if(utils.toLowerCase(column.columnName)!=utils.toLowerCaseFirst(column.columnJavaName) ) {#
	@TableField(value = "@{utils.toLowerCase(column.columnName)}")
			# } #
		# } #
	private @{column.javaType} @{utils.toLowerCaseFirst(column.columnJavaName)};
    # } #
	//</editor-fold>

	# for(column in table.columns){ #
	# if(column.pk) { #
	@Override
	protected Serializable pkVal() {
		return @{utils.toLowerCase(column.columnName)};
	}
	# } #
	# } #

    # for(column in table.columns){ #
	public @{column.javaType} get@{utils.toUpperCaseFirst(column.columnJavaName)}() {
		return @{utils.toLowerCaseFirst(column.columnJavaName)};
	}

    public void set@{utils.toUpperCaseFirst(column.columnJavaName)}(@{column.javaType} @{utils.toLowerCaseFirst(column.columnJavaName)}) {
    	this.@{utils.toLowerCaseFirst(column.columnJavaName)} = @{utils.toLowerCaseFirst(column.columnJavaName)};
    }
	# } #

	@Override
	public String toString() {
		String log = "";
	# for(column in table.columns){ #
		log += "[@{utils.toLowerCaseFirst(column.columnJavaName)}:" + get@{utils.toUpperCaseFirst(column.columnJavaName)}() + "]";
	# } #
		return log;
	}
}
