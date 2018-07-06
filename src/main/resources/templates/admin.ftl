<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>

<@common.page>
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <script type="text/javascript">
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        function deleteUser(id) {
            var userId = id.split('_')[2];

            $.ajax({
                type: "DELETE",
                contentType: "application/json",
                url: "/admin/delete/" + userId,
                dataType: 'json',
                cache: false,
                timeout: 10000,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.status == "204") {
                        location.reload(true);
                    }
                }
            });
        }

        function disableUser(id) {
            var userId = id.split('_')[2];

            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "/admin/disabled/" + userId,
                dataType: 'json',
                cache: false,
                timeout: 10000,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.status == "204") {
                        location.reload(true);
                    }
                }
            });
        }

        function getList(username) {

            $("#usernameBtn").prop("disabled", true);

            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/admin/list",
                data: {"username": username},
                dataType: 'json',
                cache: false,
                timeout: 10000,
                success: function (data) {
                    var json;

                    data.forEach(function(element){
                        json = json +
                                "<tr>" +
                                    "<td>" + element.id + "</td>" +
                                    "<td>" + element.username + "</td>" +
                                    "<td>" + element.enabled + "</td>" +
                                    "<td>" + element.userRoles + "</td>" +
                                "</tr>" +
                                "<tr>" +
                                    "<td colspan='5'>" +
                                        "<div style='display: flex;'>" +
                                            "<div style='margin-left: 10px;'>" +
                                                "<a href='#' id='delete_${_csrf.token}_" + element.id + "' class='deleteUser' onclick='deleteUser(this.id)'>Delete</a>" +
                                            "</div>" +
                                            "<div style='margin-left: 10px;'>" +
                                                "<a href='#' id='disable_${_csrf.token}_" + element.id + "' class='disabledUser' onclick='disableUser(this.id)'>" +
                                                    (element.enabled ? "Disable" : "Enable") +
                                                "</a>" +
                                            "</div>" +
                                        "</div>" +
                                    "</td>" +
                                "</tr>";
                    });

                    $('#feedback').html(json);

                    console.log("SUCCESS : ", data);
                    $("#usernameBtn").prop("disabled", false);

                },
                error: function (e) {

                    var json = "<h4>Ajax Response</h4><pre>"
                            + e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);
                    $("usernameBtn").prop("disabled", false);

                }
            })
        }

        $(document).ready(function () {
            getList("");

            $("#usernameBtn").click(function () {
                getList($("#username").val());
            });
        });
    </script>
    <@login.logout>
        <div>
            <a href="/user">
                <button class="login-btn">User panel</button>
            </a>
        </div>
    </@login.logout>
    <input type="text" id="username" placeholder="username">
    <button id="usernameBtn">Search</button>
    <table class="bordered" width="90%" style="margin: 50px; margin-top: 20px;">
        <thead>
            <tr>
                <td>Id</td>
                <td>Username</td>
                <td>Enabled</td>
                <td>User Role</td>
            </tr>
        </thead>
        <tbody id="feedback"></tbody>
    </table>
</@common.page>