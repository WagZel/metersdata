<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>
<@common.page>
    <div style="margin: 10px; text-align: right;">
        <a href="registration">
            <input type="submit" class="login-btn" value="Registration" />
        </a>
    </div>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <@login.login "/login">
        <button class="login-btn" type="submit" style="margin-top: 10px;">Sign in</button>
    </@login.login>
</@common.page>