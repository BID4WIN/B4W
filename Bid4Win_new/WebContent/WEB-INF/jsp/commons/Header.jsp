<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="header">
<tiles:insertAttribute name="snippet" />
<div id="header-content">

<h1><a href='#CurrentAuctions' class='click' accesskey='1' title="<s:text name="logo.title"/>"><img id="logo" alt="Bid4win.com" src="<s:url value="/WEB-SUP/css/img/logo02.png" />" width="265" height="74" /></a></h1>


<ul id="menu">
  <li id="menu-home"><a href="#ProductsManager"  class='click'><img alt="Apprendre" src="<s:url value="/WEB-SUP/css/img/menu-apprendre.png" />" width="102" height="111" /></a></li>
  <li id="menu-auctions"><a href="#PropertiesManager"  class='click'><img alt="Forum" src="<s:url value="/WEB-SUP/css/img/menu-forum.png" />" width="105" height="114" /></a></li>
</ul>

<div id="sous-menu">
<ul>
  <li class="homeitem"><a href='#CurrentAuctions' class='click'><img src="<s:url value="/WEB-SUP/css/img/home_menu.png" />" alt="<s:text name="submenu.home" />" /></a></li>
  <li class="item"><span><a href='#CurrentAuctions' class='click'><s:text name="submenu.currentauctions" /></a></span></li>
  <li class="item"><span><a href='#ClosedAuctions' class='click'><s:text name="submenu.closedauctions" /></a></span></li>
  <li class="item"><span><a href='#AuctionsManager' class='click'><s:text name="submenu.howto" /></a></span></li>
</ul>
</div>
</div>
</div>