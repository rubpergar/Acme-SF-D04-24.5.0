<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.progress-log.form.label.record-id" path="recordId"/>
	<acme:input-double code="client.progress-log.form.label.completeness" path="completeness"/>	
	<acme:input-textarea code="client.progress-log.form.label.comment" path="comment"/>	
	<acme:input-moment code="client.progress-log.form.label.registration-moment" path="registrationMoment" readonly="true"/>	
	<acme:input-textarea code="client.progress-log.form.label.responsible-person" path="responsiblePerson"/>	
</acme:form>