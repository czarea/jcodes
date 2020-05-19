package @{package}.entity;

import @{package}.entity.@{table.className};
import com.ssjm.kll.common.vo.PageVO;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author @{author}
 */
@Data
public class @{table.className}VO extends PageVO<@{table.className}> {

	//<editor-fold desc="properties">
	# for(column in table.columns){ #

		# if(column.pk) { #
		# } else { #
	/**
	 * @{column.remarks}
	 */
			# if(utils.toLowerCase(column.columnName)!=utils.toLowerCaseFirst(column.columnJavaName) ) {#
			# } #
		# } #
    private @{column.javaType} @{utils.toLowerCaseFirst(column.columnJavaName)};
    # } #
	//</editor-fold>

}
