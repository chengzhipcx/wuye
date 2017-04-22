package com.onlymvp.interceptor;

import java.util.Map;

import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.StringTool;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AdminInterceptor extends AbstractInterceptor {


	private static final long serialVersionUID = -9131535619794630998L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		System.out.println("---进入拦截器---");
		
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		
		AdminInfoEntity entity = (AdminInfoEntity) session.get(Const.SESSION_ADMIN_BEAN);
		
		
		if(StringTool.isObjectNull(entity))
			return invocation.invoke();
		
		
		
		return "error";
	}

}
