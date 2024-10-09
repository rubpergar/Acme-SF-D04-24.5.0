<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.contract.list.label.code" path="code"/>
	<acme:list-column code="any.contract.list.label.budget" path="budget"/>
	<acme:list-column code="any.contract.list.label.project" path="project"/>
</acme:list>

