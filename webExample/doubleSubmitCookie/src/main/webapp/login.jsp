<%--
  Created by IntelliJ IDEA.
  User: tor
  Date: 029, 29.08.2021
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
        }

        form {
            border: 3px solid #f1f1f1;
            width: 20rem;
        }

        input[type=text], input[type=password] {
            width: 100%;
            padding: 16px 1rem;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #04AA6D;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            opacity: 0.8;
        }

        .container {
            padding: 16px;
        }
        .hidden {
            border: 0;
            clip: rect(0 0 0 0);
            height: 1px;
            margin: -1px;
            overflow: hidden;
            padding: 0;
            position: absolute;
            width: 1px;
        }
        .icon {
            display: inline-block;
            fill: #606468;
            font-size: 16px;
            font-size: 1rem;
            height: 1em;
            vertical-align: middle;
            width: 1em;
        }
    </style>
</head>
<body>
<form method="post" action="LoginCheck">
    <div class="container">
        <div class="input-field">
            <label for="username">
                <svg class="icon">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#user"></use>
                </svg>
                <span class="hidden">Username</span></label>
            <input type="text" placeholder="Enter Username" name="username" id="username" required
                   autocomplete="nickname">
        </div>
        <div class="input-field">
            <label for="password">
                <svg class="icon">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#lock"></use>
                </svg>
                <span class="hidden">Password</span></label>
            <input type="password" placeholder="Enter Password" name="password" id="password" required
                   autocomplete="current-password">
        </div>
        <button type="submit" value="login">Login</button>

    </div>

</form>
<svg xmlns="http://www.w3.org/2000/svg" class="icons"><symbol id="arrow-right" viewBox="0 0 1792 1792"><path d="M1600 960q0 54-37 91l-651 651q-39 37-91 37-51 0-90-37l-75-75q-38-38-38-91t38-91l293-293H245q-52 0-84.5-37.5T128 1024V896q0-53 32.5-90.5T245 768h704L656 474q-38-36-38-90t38-90l75-75q38-38 90-38 53 0 91 38l651 651q37 35 37 90z"/></symbol><symbol id="lock" viewBox="0 0 1792 1792"><path d="M640 768h512V576q0-106-75-181t-181-75-181 75-75 181v192zm832 96v576q0 40-28 68t-68 28H416q-40 0-68-28t-28-68V864q0-40 28-68t68-28h32V576q0-184 132-316t316-132 316 132 132 316v192h32q40 0 68 28t28 68z"/></symbol><symbol id="user" viewBox="0 0 1792 1792"><path d="M1600 1405q0 120-73 189.5t-194 69.5H459q-121 0-194-69.5T192 1405q0-53 3.5-103.5t14-109T236 1084t43-97.5 62-81 85.5-53.5T538 832q9 0 42 21.5t74.5 48 108 48T896 971t133.5-21.5 108-48 74.5-48 42-21.5q61 0 111.5 20t85.5 53.5 62 81 43 97.5 26.5 108.5 14 109 3.5 103.5zm-320-893q0 159-112.5 271.5T896 896 624.5 783.5 512 512t112.5-271.5T896 128t271.5 112.5T1280 512z"/></symbol></svg>
</body>
</html>
