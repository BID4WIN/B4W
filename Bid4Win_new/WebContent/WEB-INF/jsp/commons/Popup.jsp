<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="popup">
  <div class="fog click"></div>
  <div class="window">
    <tiles:insertAttribute name="fancyTable" />
  </div>
</div>
