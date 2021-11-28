function query() {
    $.ajax({
        url: "http://api.rlspfood.local:8081/paymenttype",
        type: "get",

        success: function(response) {
            populateTable(response);
        }
    });
}

function save() {
    var paymentTypeJson = JSON.stringify({
        "description": $("#description").val()
    });

    console.log(paymentTypeJson);

    $.ajax({
        url: "http://api.rlspfood.local:8081/paymenttype",
        type: "post",
        data: paymentTypeJson,
        contentType: "application/json",

        success: function(response) {
            alert("Payment Type added!");
            query();
        },

        error: function(error) {
            if (error.status >= 400 && error.status <= 499) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            } else {
                alert("Error saving payment Type!");
            }
        }
    });
}

function remove(paymentType) {
    var url = "http://api.rlspfood.local:8081/paymenttype/" + paymentType.id;

    $.ajax({
        url: url,
        type: "delete",

        success: function(response) {
            query();

            alert("Payment Type deleted!");
        },

        error: function(error) {
            // tratando todos os erros da categoria 4xx
            if (error.status >= 400 && error.status <= 499) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            } else {
                alert("Error removing payment Type");
            }
        }
    });
}

function populateTable(paymentTypes) {
    $("#tablet tbody tr").remove();

    $.each(paymentTypes, function(i, paymentType) {
        var row = $("<tr>");

        var action = $("<a href='#'>")
            .text("Delete")
            .click(function(event) {
                event.preventDefault();
                remove(paymentType);
            });

        row.append(
            $("<td>").text(paymentType.id),
            $("<td>").text(paymentType.name),
            $("<td>").append(action)
        );

        row.appendTo("#tablet");
    });
}


$("#btn-query").click(query);
$("#btn-save").click(save);