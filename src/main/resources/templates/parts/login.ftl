<#import "auth.ftl" as sec>
<#macro login path>
    <form id="login-form" action="${path}" method="POST">
        <div>
            <label>
                <input type="text" class="login-input" name="username" placeholder="User name" />
            </label>
        </div>
        <div>
            <label>
                <input type="text" class="login-input" name="password" placeholder="Password" style="margin-top: 10px;" />
            </label>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div>
            <#nested>
        </div>
    </form>
</#macro>

<#macro logout>
    <table width="100%">
        <tr>
            <td width="90%"></td>
            <td>
                <div style="display: flex; margin: 10px;">
                    <#nested/>
                    <form id="logout" action="/logout" method="post" style="margin-left: 3px;">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button class="login-btn" type="submit">Sign Out</button>
                    </form>
                </div>
            </td>
        </tr>
    </table>
</#macro>