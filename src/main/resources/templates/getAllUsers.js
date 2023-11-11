function showList(){
    fetch("http://localhost:8080/rest/users")
        .then(response => response.json())
        .then(data => createTable(data));
}
function createTable(data) {
    var table = "<table>";
    // add a row for name and marks
    table += `<tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                </tr>`;
    // now loop through students
    // show their name and marks
    var tr = "";
    for(let i = 0; i < data.maconUsers.length; i++) {
        tr += "<tr>";
        tr += `<td>${data.maconUsers[i].name}</td>`;
        tr += `<td>${data.maconUsers[i].email}</td>`;
        tr += `<td>${data.maconUsers[i].role}</td>`;
        tr += "</tr>"
    }
    table += tr + "</table>";

    // append table to body
    document.body.innerHTML += table;
}