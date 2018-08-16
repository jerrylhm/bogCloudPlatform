package com.ilongli.web.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * Druid监控过滤器
 * @author ilongli
 *
 */
@WebFilter(filterName="druidFilter", urlPatterns="/*", initParams={
	@WebInitParam(name="exclusions", value="*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")
})
public class Filter3_DruidStatFilter extends WebStatFilter {

}
