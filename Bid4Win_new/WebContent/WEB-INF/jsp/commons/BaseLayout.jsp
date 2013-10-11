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

<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/src/base.css"/>" />
<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/src/basefancy.css"/>" />
<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/src/main.css"/>" />
<!--<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/handheld.css"/>" media="handheld" />-->
<!--<link rel="stylesheet" type="text/css" href="<s:url value="/WEB-SUP/css/print.css"/>" media="print" />-->
<!--[if lte IE 6]><link type="text/css" rel="stylesheet" href="<s:url value="/WEB-SUP/css/ie6.css"/>" media="all" /><![endif]-->
<!--[if IE 7]><link type="text/css" rel="stylesheet" href="<s:url value="/WEB-SUP/css/ie7.css"/>" media="all" /><![endif]-->
<!--  MediaQuery pour iPhone, Google, WebKit -->
<!--[if !IE]>-->
<!--<link media="only screen and (max-device-width: 480px)" href="<s:url value="/WEB-SUP/css/handheld.css"/>" type="text/css" rel="stylesheet" />-->
<!--<![endif]-->
<script type="text/javascript">
accountIdJSP = '<%=request.getParameter("accountId")%>';
reinitKeyJSP = '<%=request.getParameter("reinitKey")%>';
</script>
</head>

<body onload="">
<tiles:importAttribute name="board" />
<tiles:importAttribute name="page" />
<tiles:importAttribute name="popup" />
<tiles:importAttribute name="snippet" />
<tiles:importAttribute name="fancyTable" />
<tiles:insertAttribute name="header">
  <tiles:putAttribute name="snippet" value="${snippet}" />
</tiles:insertAttribute>
<tiles:insertAttribute name="body">
  <tiles:putAttribute name="board" value="${board}" />
  <tiles:putAttribute name="page" value="${page}" />
  <tiles:putAttribute name="popup" value="${popup}" />
  <tiles:putAttribute name="fancyTable" value="${fancyTable}" />
</tiles:insertAttribute>
<tiles:insertAttribute name="footer" />
<div id="json"></div>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/lib/jquery-1.7.1.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/lib/jquery.history.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/lib/jquery.cookie.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/lib/ajaxfileupload.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Util.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Object.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Function.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Array.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Date.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/String.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Number.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Collection.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/List.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Map.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/core/Bean.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/communication/Logger.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/communication/Message.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/communication/Result.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/shortcut/Shortcut.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/property/Property.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/image/Image.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/label/Label.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/product/Product.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/auction/Auction.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/account/Account.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/store/Directory.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/store/Tree.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/ajax/AjaxManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/ajax/AjaxObjectManager.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/connection/ConnectionManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/connection/SubscriptionManager.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/language/LanguageManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/currency/CurrencyManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/shortcut/ShortcutManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/property/PropertyMap.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/property/PropertyManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/property/i18n/I18nManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/image/ImageManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/label/LabelManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/product/ProductManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/auction/AuctionManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/account/AccountManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/model/store/DirectoryManager.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/Content.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/navigation/NavigationManager.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/status/PasswordReinitFormContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/status/ConnectionFormContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/status/SubscriptionFormContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/status/StatusSnippetContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/CurrentAuctionsContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/ClosedAuctionsContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/content/HowtoContent.js"/>"></script>

<!-- Admin imports -->
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/PropertiesManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/I18nManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/ProductsManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/ProductManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/ImagesManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/LabelsManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/AuctionsManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/AuctionManagerContent.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/content/AccountsManagerContent.js"/>"></script>

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/admin/AdminContentManager.js"/>"></script>
<!-- End admin imports -->

<script type="text/javascript" src="<s:url value="/WEB-SUP/js/main.js"/>"></script>
<script type="text/javascript" src="<s:url value="/WEB-SUP/js/______________________TEST______________________.js"/>"></script>
</body>
</html>