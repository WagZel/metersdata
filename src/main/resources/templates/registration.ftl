<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>
<@common.page>
    <div style="margin: 10px; text-align: right;">
        <a href="login">
            <input type="submit" class="login-btn" value="Sign in" />
        </a>
    </div>
    <@login.login "/registration">
        <a href="user.ftl">
            <input type="submit" class="login-btn" value="Sign up" style="margin-top: 10px;" />
        </a>
    </@login.login>
    <a href="registration.ftl"></a>
</@common.page>