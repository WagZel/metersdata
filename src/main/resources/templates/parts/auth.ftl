<#macro authorize ifAnyGranted selector>
    <#assign authorized = false>
    <#if Session["SPRING_SECURITY_CONTEXT"]??>
        <#list Session["SPRING_SECURITY_CONTEXT"].authentication.authorities as authority>
            <#if authority == ifAnyGranted>
                <#assign authorized = true>
            </#if>
        </#list>
        <#if authorized == selector>
            <#nested>
        </#if>
    </#if>
</#macro>