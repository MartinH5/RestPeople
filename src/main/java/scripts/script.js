$(document).ready(function () {
    $("#failed").hide();
    loadEvents();

});

function dummyData() {
    $('#person-tbody').append(
            "<tr>" +
            "<td class='tabletd'>NaN</td>" +
            "<td class='tabletd'>Patrick</td>" +
            "<td class='tabletd'>Johansen</td>" +
            "<td class='tabletd'>12345678</td>" +
            "<td class='tabletd'><button class='del'>delete</button> / <button class='edit'>edit</button></td>" +
            "</tr>"
            );
    editable();
}

function loadEvents() {

    $.ajax({
        url: 'api/person/all',
        type: 'GET',
        contentType: 'application/json',
        success: function (res) {
            res.forEach(function (entry) {
                $('#person-tbody').append(
                        "<tr>" +
                        "<td class='tabletd'>" + entry.personid + "</td>" +
                        "<td class='tabletd'>" + entry.fname + "</td>" +
                        "<td class='tabletd'>" + entry.lname + "</td>" +
                        "<td class='tabletd'>" + entry.pnumber + "</td>" +
                        "<td><button class='del'>delete</button> / <button class='edit'>edit</button></td>" +
                        "</tr>"
                        );
            });
            editable();
        },
        error: function (error) {
            var json = JSON.parse(error.responseText);
            $("#failed").show().html(json["msg"]);
        }
    });

    $("#refresh").click(function () {
        location.reload();
    });

    $("#dummy").click(function () {
        dummyData();
    });

    $("#createperson").click(function () {
        var person = {
            fname: $('#fname').val(),
            lname: $('#lname').val(),
            pnumber: $('#pnumber').val()
        };
        $.ajax({
            url: 'api/person',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(person),
            success: function (res) {
                $('#person-tbody').append(
                        "<tr>" +
                        "<td class='tabletd'>" + res.personid + "</td>" +
                        "<td class='tabletd'>" + res.fname + "</td>" +
                        "<td class='tabletd'>" + res.lname + "</td>" +
                        "<td class='tabletd'>" + res.pnumber + "</td>" +
                        "<td><button class='del'>delete</button> / <button class='edit'>edit</button></td>" +
                        "</tr>"
                        );
                editable();
            },
            error: function (error) {
                var json = JSON.parse(error.responseText);
                $("#failed").show().html(json["msg"]);
            }
        });
    });
}

function editable() {

    $(".del").click(function () {
        var row = $(this).closest('tr');
        row.remove();
        $.ajax({
            url: 'api/person/' + $(row).find("td:first-child").text(),
            type: 'DELETE',
            success: function () {
                $('#person-tbody').remove($(row));
            },
            error: function (error) {
                var json = JSON.parse(error.responseText);
                $("#failed").show().html(json["msg"]);
            }
        });
    });

    $(".edit").click(function () {
        var row = $(this).closest('tr');
        var person = {
            personid: $(row).find("td:first-child").text(),
            fname: $("#fname").val(),
            lname: $("#lname").val(),
            pnumber: $("#pnumber").val()
        };
        $.ajax({
            url: 'api/person',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(person),
            success: function (res) {
                $(row).find("td:nth-child(2)").text(res.fname);
                $(row).find("td:nth-child(3)").text(res.lname);
                $(row).find("td:nth-child(4)").text(res.pnumber);
                location.reload();
            },
            error: function (error) {
                var json = JSON.parse(error.responseText);
                $("#failed").show().html(json["msg"]);
            }
        });
    });

}