let userRoleId;
let userName;

function loadAllItems() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/items',
        dataType: 'json'
    }).done(function (items) {
        for (let item of items) {
            addNewTableLine(item);
        }
        if (!showAllTableLines()) {
            hideMarkedTableLines();
        }
    }).fail(function (err) {
            console.log(err);
        }
    );

}

function getUserRoleIdAndName() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/userInfo',
        dataType: 'json'
    }).done(function (objects) {
        userRoleId = objects[0]
        userName = objects[1]
    }).fail(function (err) {
            console.log(err);
        }
    );
}

function selectAllItems() {
    if (!showAllTableLines()) {
        hideMarkedTableLines()
    } else {
        deleteBodyTable()
        loadAllItems();
    }
}

function addNewTableLine(item) {
    const numberRow = document.getElementById("table").rows.length - 1;
    document.getElementById("table").insertRow(-1).innerHTML = '<th scope="row">'
        + numberRow + '</th><td>' + item.description + '</td><td>' + item.user.name + '</td>'
        + '<td>'
        + '<div class="form-check"><input class="form-check-input" name="line" onclick=updateItem(' + item.id + ') type="checkbox"'
        + ' id=' + item.id + '><label class="form-check-label" for="flexCheckDefault"></label></div>'
        + '</td>';
    const checkbox = document.getElementById(String(item.id));
    if (item.done === true) {
        checkbox.setAttribute('checked', 'true');
    }
    if (item.user.role.id !== userRoleId || item.user.name !== userName) {
        checkbox.setAttribute("disabled", "disabled")
        console.log(item.user.role.id + " = " + userRoleId)
        console.log(item.user.name + " = " + userName)
    }
}

function deleteBodyTable() {
    const trs = document.getElementById("table").rows;
    while (trs.length > 2) trs[2].parentNode.removeChild(trs[2]);
}

function hideMarkedTableLines() {
    const tables = Array.from(document.getElementsByTagName('table'));
    for (let table of tables) {
        const lines = table.getElementsByTagName("tr");
        for (let line of lines) {
            const li = line.getElementsByTagName("input");
            for (let l of li) {
                if (l.checked) {
                    line.style.display = "none";
                }
            }
        }
    }
}

function showAllTableLines() {
    return document.getElementById("allItems").checked;
}

function saveItem() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/items',
        data: JSON.stringify({
            description: $("#textarea1").val()
        }),
        dataType: 'json'
    }).done(function () {
        $("#textarea1").val("");
        deleteBodyTable();
        loadAllItems();
    }).fail(function (err) {
            console.log(err);
        }
    );
}

function updateItem(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/itemUp',
        data: JSON.stringify({
            id: id,
            done: document.getElementById(id).checked
        }),
        dataType: 'json'
    }).done(function () {
        deleteBodyTable();
        loadAllItems();
    }).fail(function (err) {
            console.log(err);
        }
    );
}