<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.contract.form.label.code" path="code"/>	
	<acme:input-moment code="any.contract.form.label.instantiation-moment" path="instantiationMoment"/>
	<acme:input-textarea code="any.contract.form.label.provider-name" path="providerName"/>
	<acme:input-textarea code="any.contract.form.label.customer-name" path="customerName"/>
	<acme:input-textarea code="any.contract.form.label.goals" path="goals"/>
	<acme:input-money code="any.contract.form.label.budget" path="budget"/>
	<acme:button code="any.contract.form.button.project" action="/any/project/show?id=${projectId}"/>	
	<acme:button code="any.contract.form.button.progress-logs.list" action="/any/progress-log/list?masterId=${id}"/>			
</acme:form>