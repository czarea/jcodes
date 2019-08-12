package @{package}.service;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import cn.lvji.order.garden.BaseTest;
import org.junit.Test;


/**
 *  @author @{author}
 */
public class @{table.className}ServiceTest extends BaseTest  {
	@Test
	public void @{utils.toLowerCaseFirst(table.className)}List() throws Exception {
		mockMvc.perform(get("/pt/@{utils.toLowerCaseFirst(table.className)}/action/search")
		.param("offset", "0")
		.param("limit", "20"))
		.andExpect(status().is2xxSuccessful())
		.andDo(this.documentationHandler.document(
		responseFields(
			fieldWithPath("code").description("返回码"),
			fieldWithPath("msg").description("返回消息"),
			fieldWithPath("data.pages").description("总页数"),
			fieldWithPath("data.total").description("总条数"),
		# for(column in table.columns){ #
		    # if(columnLP.last){ #
			fieldWithPath("data.list[].@{utils.toLowerCaseFirst(column.columnJavaName)}").description("@{column.remarks}")
			# } else { #
			fieldWithPath("data.list[].@{utils.toLowerCaseFirst(column.columnJavaName)}").description("@{column.remarks}"),
			# } #
		# } #
			))
		);
	}
}
