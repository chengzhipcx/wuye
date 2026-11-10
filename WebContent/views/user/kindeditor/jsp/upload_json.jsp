<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * KindEditor 图片上传接口(简化实现)
 * 演示环境不开放文件上传,统一返回提示信息,避免上传功能报错
 */
response.setHeader("Content-Type", "application/json;charset=UTF-8");
out.print("{\"error\":1,\"message\":\"演示环境未开放文件上传功能\"}");
out.flush();
%>
