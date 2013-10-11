<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="body">
<div id="header-shadow">&nbsp;</div>
<tiles:importAttribute name="fancyTable" />
<!--<tiles:insertAttribute name="board">
  <tiles:putAttribute name="fancyTable" value="${fancyTable}" />
</tiles:insertAttribute>-->
<tiles:insertAttribute name="page">
  <tiles:putAttribute name="fancyTable" value="${fancyTable}" />
</tiles:insertAttribute>
<tiles:insertAttribute name="popup">
  <tiles:putAttribute name="fancyTable" value="${fancyTable}" />
</tiles:insertAttribute>
</div>
