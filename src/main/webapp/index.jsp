<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>“to do” list</title>
</head>
<body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<script src="<c:url value="/script.js"/>"></script>
<script> loadAllItems() </script>

<div class="container">
    <div class="form-group">
        <label for="textarea1"> </label>
        <textarea placeholder="Опишите детали нового задания" class="form-control" id="textarea1" rows="3"></textarea>
    </div>
    <button type="submit" class="btn btn-primary mb-2" onclick=saveItem()>добавить</button>
    <div class="form-check">
        <input class="form-check-input" type="checkbox" id="allItems" onclick=selectAllItems()>
        <label class="form-check-label" for="allItems">
            Все задания
        </label>
    </div>
    <div class="row pt-3">
        <table class="table table-striped" id="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">описание</th>
                <th scope="col">выполнено</th>
            </tr>
            </thead>
            <tbody>
            <tr></tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>