package @{package}.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @{table.remarks}
 *
 * @author @{author}
 */
@Data
@EqualsAndHashCode(callSuper = false)
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

}
