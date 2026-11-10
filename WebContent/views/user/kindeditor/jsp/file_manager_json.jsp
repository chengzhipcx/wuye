<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * KindEditor 文件浏览器接口(简化实现)
 * 演示环境无服务器文件空间,返回空列表
 */
response.setHeader("Content-Type", "application/json;charset=UTF-8");
String path = request.getParameter("path");
if (path == null || "".equals(path)) {
    out.print("{\"current_url\":\"\",\"current_dir_path\":\"\",\"moveup_dir_path\":\"\",\"file_list\":[],\"total_count\":0}");
} else {
    out.print("{\"file_list\":[],\"total_count\":0}");
}
out.flush();
%>
