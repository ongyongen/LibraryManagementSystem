<%-- 
    Document   : getStaff
    Created on : 6 Feb, 2023, 4:00:41 PM
    Author     : ongyongen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%> <!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <title>West Coast Community Centre Library</title>
    </head>
    <body>
    <h1>Login Page</h1>
    <p>Please fill in your staff username and password</p>
    <form action="Controller/doLoginStaff" method="POST">
        <label for="username">Username </label><input type="text" id="username" name="username"/><br/>
        <label for="password">Password: </label><input type="password" id="password" name="password"/><br/>
        <input type="submit" value="Submit" /> 
    </form>
    <a href="index.html">Back to Landing Page</a>
</html>


