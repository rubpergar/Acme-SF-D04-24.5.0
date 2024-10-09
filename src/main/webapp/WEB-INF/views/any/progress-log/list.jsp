<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="client.progress-log.list.label.record-id" path="recordId" width="10%"/>
	<acme:list-column code="client.progress-log.list.label.completeness" path="completeness" width="10%"/>
	<acme:list-column code="client.progress-log.list.label.registration-moment" path="registrationMoment" width="10%"/>	
</acme:list>