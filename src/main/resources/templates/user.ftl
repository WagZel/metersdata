<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>
<#import "parts/auth.ftl" as sec>
<@common.page>
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <script type="text/javascript">
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        function editData(id) {

            var selectText = $("td").filter(".type." + id).filter(".type").html();
            var obj = $("td").filter("." + id);

            $.each(obj, function (i, element) {

                if (i === 0) {
                    $(element).html(
                            "<select id='newSelect-" + id +"'>" +
                                "<option value='COLD_WATER'>Cold water</option>" +
                                "<option value='HEAT_WATER'>Heat water</option>" +
                                "<option value='HEATING'>Heating</option>" +
                                "<option value='ELECTRICITY_DAY'>Electricity day</option>" +
                                "<option value='ELECTRICITY_NIGHT'>Electricity night</option>" +
                            "</select>"
                    );
                } else if (i === 1) {
                    var input = document.createElement("INPUT");
                    input.setAttribute('type', 'text');
                    input.setAttribute('value', $(element).html());
                    input.setAttribute('id', "newData-" + id);
                    $(element).html(input);
                } else {
                    var input_ = document.createElement("INPUT");
                    input_.setAttribute('type', 'text');
                    input_.setAttribute('value', $(element).html());
                    input_.setAttribute('id', "description-" + id);
                    $(element).html(input_);
                }
            });

            $("#delete-div-" + id).html("<a href='#' onclick='location.reload(true);'>Cancel<a>")
            $("#edit-div-" + id).html("<a href='#' onclick='saveData(" + id + ");'>Save<a>")

            $("#newSelect-" + id).val(selectText);
        }

        function saveData(id) {

            addData($("#newSelect-" + id).val(),
                    $("#newData-" + id).val(),
                    $("#description-" + id).val(),
                    id
            );
        }

        function deleteData(id) {
            var userId = id.split('-')[1];

            $.ajax({
                type: "DELETE",
                contentType: "application/json",
                url: "/user/delete/" + userId,
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

        function getList(order, field, filter) {

            $("#filterBtn").prop("disabled", true);

            filter["order"] = order;
            filter["sortField"] = field;

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/user/list",
                data: JSON.stringify(filter),
                dataType: 'json',
                cache: false,
                timeout: 10000,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    var json;

                    if (data.length === 0) {
                        json = "<tr><td colspan='5'>No data</td></tr>"
                    } else {
                        data.forEach(function (element) {
                            var username = element[0];
                            var metersData = element[1];

                            json = json +
                                    "<tr>" +
                                        <@sec.authorize "ROLE_ADMIN" true>
                                            "<td>" + username + "</td>" +
                                        </@sec.authorize>
                                        "<td>" + metersData.dateCreated + "</td>" +
                                        "<td class='type " + metersData.id + "'>" + metersData.meterType + "</td>" +
                                        "<td class='" + metersData.id + "'>" + metersData.data + "</td>" +
                                        "<td class='" + metersData.id + "'>" + (metersData.description == null ? '' : metersData.description) + "</td>" +
                                    "</tr>" +
                                    "<tr>" +
                                        "<td colspan='5'>" +
                                            "<div style='display: flex;'>" +
                                                "<div id='delete-div-" + metersData.id + "' style='margin-left: 10px;'>" +
                                                    "<a href='#' id='delete-" + metersData.id + "' class='deleteData' onclick='deleteData(this.id)'>Delete</a>" +
                                                "</div>" +
                                                "<div id='edit-div-" + metersData.id + "' style='margin-left: 10px;'>" +
                                                    "<a href='#' id='" + metersData.id + "' class='editData' onclick='editData(this.id)'>Edit</a>" +
                                                "</div>" +
                                            "</div>" +
                                        "</td>" +
                                    "</tr>";
                        });
                    }

                    $('#feedback').html(json);

                    console.log("SUCCESS : ", data);
                    $("#filterBtn").prop("disabled", false);

                },
                error: function (e) {

                    var json = "<h4>Ajax Response</h4><pre>"
                            + e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);
                    $("#filterBtn").prop("disabled", false);

                }
            })
        }

        function addData(meterType, data, description, id) {

            var metersData = {};

            if (id != null) {
                metersData["id"] = id;
            }

            metersData["meterType"] = meterType;
            metersData["data"] = data;
            metersData["description"] = description;

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/user/add",
                data: JSON.stringify(metersData),
                dataType: 'json',
                cache: false,
                timeout: 10000,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {

                    console.log("SUCCESS : ", data);
                    location.reload(true);

                },
                error: function (e) {

                    var json = "<h4>Ajax Response</h4><pre>"
                            + e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);

                }
            })
        }

        $(document).ready(function () {
            var data = {};
            getList("desc", "dateCreated", data);

            $("#filterBtn").click(function () {

                var filter = new Map();

                if ($("#filterMetersData").val() !== '') {
                    filter.set("meterType", $("#filterMetersData").val());
                }

                if ($("#startDate").val() !== '') {
                    filter.set("startDate", $("#startDate").val());
                }

                if ($("#endDate").val() !== '') {
                    filter.set("endDate", $("#endDate").val());
                }

                if ($("#filterUsername").val() !== '') {
                    filter.set("username", $("#filterUsername").val());
                }

                getList(
                    $("#sortOrder").val(),
                    $("#sortField").val(),
                    filter
                );
            });

            $("#addBtn").click(function () {
                addData($("#addMeterType").val(), $("#addData").val(), $("#addDescription").val(), null)
            });
        });
    </script>
    <@login.logout>
        <@sec.authorize "ROLE_ADMIN" true>
            <div>
                <a href="/admin">
                    <button class="login-btn">Admin panel</button>
                </a>
            </div>
        </@sec.authorize>
    </@login.logout>
    <@sec.authorize "ROLE_ADMIN" false>
        <table class="metersData" style="margin: auto;">
            <tr><td colspan="3">Add new data</td></tr>
            <tr>
                <td>
                    <label>
                        <select id="addMeterType">
                            <option value="" selected>- Select meter type -</option>
                            <option value="COLD_WATER">Cold water</option>
                            <option value="HEAT_WATER">Heat water</option>
                            <option value="HEATING">Heating</option>
                            <option value="ELECTRICITY_DAY">Electricity day</option>
                            <option value="ELECTRICITY_NIGHT">Electricity night</option>
                        </select>
                    </label>
                </td>
                <td>
                    <label>
                        <input type="text" id="addData" placeholder="data" />
                    </label>
                </td>
                <td>
                    <label>
                        <input type="text" id="addDescription" style="width: 300px;" placeholder="description" />
                    </label>
                </td>
                <td>
                    <input type="submit" id="addBtn" value="Add Data" />
                </td>
            </tr>
        </table>
    </@sec.authorize>
    <table class="metersData" width="70%" style="margin: auto; margin-top: 50px;">
        <tr>
            <td width="50%">Sort by:</td>
            <td colspan="2">Filter:</td>
        </tr>
        <@sec.authorize "ROLE_ADMIN" true>
            <tr>
                <td></td>
                <td colspan="2">
                    <label>
                        <input type="text" id="filterUsername" placeholder="Username" style="margin-bottom: 5px;"/>
                    </label>
                </td>
            </tr>
        </@sec.authorize>
        <tr>
            <td>
                <label>
                    <select id="sortField">
                        <@sec.authorize "ROLE_ADMIN" true>
                            <option value="username">Username</option>
                        </@sec.authorize>
                        <option value="dateCreated" selected>Date created</option>
                        <option value="meterType">Meter type</option>
                        <option value="data">Data</option>
                    </select>
                </label>
            </td>
            <td colspan="2">
                <label>
                    <select id="filterMetersData">
                        <option value="" selected>- Select meter type -</option>
                        <option value="COLD_WATER">Cold water</option>
                        <option value="HEAT_WATER">Heat water</option>
                        <option value="HEATING">Heating</option>
                        <option value="ELECTRICITY_DAY">Electricity day</option>
                        <option value="ELECTRICITY_NIGHT">Electricity night</option>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <label>
                    <select id="sortOrder">
                        <option value="ASC" selected>asc</option>
                        <option value="DESC">desc</option>
                    </select>
                </label>
            </td>
            <td>
                <label>
                    Start date:</br>
                    <input id="startDate" type="datetime-local" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" />
                </label>
            </td>
            <td>
                <label>
                    End date:</br>
                    <input id="endDate" type="datetime-local" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" />
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <label>
                    <input type="submit" id="filterBtn" value="Submit" style="margin-top: 10px;" />
                </label>
            </td>
        </tr>
    </table>
    <table class="bordered" width="90%" style="margin: 50px; margin-top: 20px;">
        <thead>
            <tr>
                <@sec.authorize "ROLE_ADMIN" true>
                    <td>Username</td>
                </@sec.authorize>
                <td>Date</td>
                <td>Meter Type</td>
                <td>Data</td>
                <td>Description</td>
            </tr>
        </thead>
        <tbody id="feedback"></tbody>
    </table>
</@common.page>