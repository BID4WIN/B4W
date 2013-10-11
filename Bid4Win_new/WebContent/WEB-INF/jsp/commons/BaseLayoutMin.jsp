<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>

<head>
<title><s:text name="welcome.title" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta name="keywords" content="<s:text name="meta.keywords"/>" />
<meta name="description" content="<s:text name="meta.description"/>" />
<meta http-equiv="imagetoolbar" content="no" />
<meta name="geo.placename" content="France" />

<link rel="shortcut icon" href="<s:url value="/WEB-SUP/css/img/logo_graphic01.png"/>" />
<link rel="apple-touch-icon" href="<s:url value="/WEB-SUP/img/favicon/favicon01.gif"/>" />

<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/min/min.css"/>" />
<!--<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/handheld.css"/>" media="handheld" />-->
<!--<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/print.css"/>" media="print" />-->
<!--[if lte IE 6]><link type="text/css" rel="stylesheet" href="<s:url value="/WEB-SUP/css/ie6.css"/>" media="all" /><![endif]-->
<!--[if IE 7]><link type="text/css" rel="stylesheet" href="<s:url value="/WEB-SUP/css/ie7.css"/>" media="all" /><![endif]-->
<!--  MediaQuery pour iPhone, Google, WebKit -->
<!--[if !IE]>-->
<!--<link media="only screen and (max-device-width: 480px)" href="<s:url value="/WEB-SUP/css/handheld.css"/>" type="text/css" rel="stylesheet" />-->
<!--<![endif]-->
</head>

<body>
<tiles:insertAttribute name="header" />
<tiles:importAttribute name="board" />
<tiles:importAttribute name="page" />
<tiles:importAttribute name="popup" />
<tiles:importAttribute name="fancyTable" />
<tiles:insertAttribute name="body">
  <tiles:putAttribute name="board" value="${board}" />
  <tiles:putAttribute name="page" value="${page}" />
  <tiles:putAttribute name="popup" value="${popup}" />
  <tiles:putAttribute name="fancyTable" value="${fancyTable}" />
</tiles:insertAttribute>
<tiles:insertAttribute name="footer" />
<div id="json"></div>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/min/min.js"/>"></script>
</body>
</html>