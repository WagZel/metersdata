<#import "auth.ftl" as sec>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css" />
            <script type="text/javascript" src="/libs/jquery.min.js"></script>
            <script type="text/javascript" src="/libs/jquery-ui.min.js"></script>
            <style>
                body {
                    margin: auto;
                    padding: 0;
                    text-align: center;
                }

                #login-form {
                    margin-top: 200px;
                }

                .login-input {
                    font-size: 20px;
                    font-weight: bold;
                    height: 40px;
                }

                .login-btn {
                    cursor:pointer;
                    font-size: 16px;
                    width: 120px;
                    height: 30px;
                    text-align:center;
                    border-radius:5px;
                }

                .alert {
                    color: #ff1a1a;
                    font-size: 20px;
                    font-weight: bold;
                }

                table {
                    border-collapse: collapse;
                }

                .bordered td {
                    border: 1px solid black;
                }
            </style>
        </head>
        <body>
            <#nested>
        </body>
    </html>
</#macro>